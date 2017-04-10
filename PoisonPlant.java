import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ThornPlant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonPlant extends Plant
{
    /**
     * Act - do whatever the ThornPlant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private static final double bite=0.25;

    public PoisonPlant(){
        maxFood = 40;
        maxEnergy = 300;
        type = 3;
        foodLeft = 1;
        energyLeft = maxEnergy;
        damage = Greenfoot.getRandomNumber(4)+1; damage*=50;
        duration = Greenfoot.getRandomNumber(9)+1;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }

    public void act(){
        if(time%3==0&&foodLeft<maxFood&&time<maxFood*3){
            foodLeft++;
        }
        if(time%6==0&&time>maxFood*4+200){
            foodLeft--;
        }
        if(foodLeft<=0){
            dead();
        }
        time++;
        hpBar.update(foodLeft);
        if(energyLeft<maxEnergy) energyLeft++;
        nrgBar.update(energyLeft);
    }
    public void addedToWorld (World w){
        w.addObject(hpBar, getX(), getY());
        w.addObject(nrgBar, getX(), getY());
    }

    public void attacked(Animal animal){
        animal.poison(damage,duration);
    }
    
    public int eaten(){
        if(maxFood*bite>foodLeft){
            foodLeft=0;
            return foodLeft;
        }
        foodLeft-=(int)(maxFood*bite);
        return (int)(maxFood*bite);
    }

    public void attack(){
        //does not attack
    }
}