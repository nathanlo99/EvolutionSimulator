import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class StabPlant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StabPlant extends Plant
{
    public static GreenfootImage[] stabAnimation;
    public static GreenfootImage base;
    private Animal target;
    public static double bite = 0.15;
    public static void setUp(){
        stabAnimation=new GreenfootImage[41];
        for(int i = 0; i < 11; i++){
            stabAnimation[i]=new GreenfootImage("Stab"+i+".png");
        }
        for(int i = 11; i < 40; i+=3){
            stabAnimation[i]=stabAnimation[i+1]=stabAnimation[i+2]=new GreenfootImage("Stab"+(int)Math.ceil((-i+41)/3.0)+".png");
        }
        base = new GreenfootImage("StabBase.png");
    }
    private int stab;
    public StabPlant(){
        damage=1000;
        foodLeft=1;
        maxFood=75;
        type=4;
        time=0;
        duration=0;
        stab=0;
        target=null;
        maxEnergy=300;
        energyLeft=maxEnergy;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }

    public void addedToWorld(World w){
        w.addObject(hpBar,getX(),getY());
        w.addObject(nrgBar,getX(),getY());
    }

    /**
     * Act - do whatever the StabPlant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(time%3==0&&foodLeft<maxFood&&time<maxFood*3){
            foodLeft++;
        }
        if(time%6==0&&time>maxFood*4+200){
            foodLeft--;
        }
        if(stab==0){
            double closestDist;
            double curDist;
            ArrayList<Animal> animals = (ArrayList)getWorld().getObjects(Animal.class);
            target=null;
            if(animals.size()>0){
                target = animals.get(0);
                closestDist=EvolutionWorld.getDistance(this,target);
                for(Animal a: animals){
                    if(EvolutionWorld.getDistance(this,a)<closestDist){
                        target=a;
                        closestDist=EvolutionWorld.getDistance(this,a);
                    }
                }
            }
            if(energyLeft>=100&&target!=null&&EvolutionWorld.getDistance(this,target)<=110){
                int temp = getRotation();
                turnTowards(target.getX(),target.getY());
                int temp2 = getRotation();
                int dif=Math.abs(temp2-temp)%360;
                setRotation(temp);
                if(dif<=10){
                    stab++;
                    energyLeft-=100;
                }
            }
        }
        else{
            if(stab==10){
                target.damage(damage);
            }
            stab++;
        }
        if(stab>40){
            stab=0;
        }
        if(target!=null&&target.getWorld()!=null&&EvolutionWorld.getDistance(this,target)<=75000){
            for(int i = 0; i < 2; i++){
                int temp = getRotation();
                turnTowards(target.getX(),target.getY());
                int temp2 = getRotation();
                int dif = Math.abs(temp2-temp);
                if(temp!=temp2){
                    if(dif<180){
                        if(temp2>temp){
                            setRotation(temp+1);
                        }
                        else if(temp2<temp){
                            setRotation(temp-1);
                        }
                    }else{
                        if(temp2<temp-180){
                            setRotation(temp+1);
                        }
                        else if(temp2>temp-180){
                            setRotation(temp-1);
                        }
                    }
                }
            }
        }
        GreenfootImage pic = new GreenfootImage(base);
        pic.rotate(-getRotation());
        pic.drawImage(stabAnimation[stab],0,0);
        pic.rotate(getRotation());
        pic.scale(128,128);
        pic.rotate(-getRotation());
        setImage(pic);
        time++;
        hpBar.update(foodLeft);
        if(energyLeft<maxEnergy) energyLeft++;
        nrgBar.update(energyLeft);
        if(foodLeft<=0){
            dead();
        }
    }

    public void attacked(Animal animal){}

    public void attack(){}

    public int eaten(){
        if(maxFood*bite>foodLeft){
            foodLeft=0;
            return foodLeft;
        }
        foodLeft-=(int)(maxFood*bite);
        return (int)(maxFood*bite);
    }
}
