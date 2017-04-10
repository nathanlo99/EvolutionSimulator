import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class ShooterPlant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShooterPlant extends Plant
{
    private static final double bite=0.25;
    private static final int dmg=100;
    private Animal target;
    /**
     * Create a shooter plant
     */
    public ShooterPlant(){
        maxFood=50;
        maxEnergy=300;
        type=1;
        foodLeft=1;
        time=0;
        energyLeft=maxEnergy;
        target=null;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }

    /**
     * Act - do whatever the ShooterPlant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(time%10==0){
            attack();
        }
        if(time%2==0&&foodLeft<maxFood&&time<maxFood*2){
            foodLeft++;
        }
        if(time%4==0&&time>maxFood*4+200){
            foodLeft--;
        }
        if(foodLeft<=0){
            dead();
        }
        if(target!=null&&target.getWorld()!=null){
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
        time++;
        hpBar.update(foodLeft);
        if(energyLeft<maxEnergy) energyLeft++;
        nrgBar.update(energyLeft);
    }

    public void addedToWorld (World w)
    {
        w.addObject(hpBar, getX(), getY());
        w.addObject(nrgBar, getX(), getY());
    }

    public void attacked(Animal animal){}
    
    public int eaten(){
        if(maxFood*bite>foodLeft){
            foodLeft=0;
            return foodLeft;
        }
        foodLeft-=(int)(maxFood*bite);
        return (int)(maxFood*bite);
    }

    public void attack(){
        if(energyLeft>=30){
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
            if(target!=null){
                getWorld().addObject(new Projectile(target,getRotation(),dmg),getX(),getY());
                energyLeft-=30;
            }
        }
    }
}
