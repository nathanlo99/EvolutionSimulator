import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ThornPlant here.
 * 
 * @author Jerry Liu
 * @version Apr 2017
 */
public class ThornPlant extends Plant{
    
    private static final double bite=0.25;
    
    /**
     * Constructs a new thorn plant with set health and energy. 
     */
    public ThornPlant(){
        maxFood = 35;
        maxEnergy = 400;
        type = 2;
        foodLeft = 1;
        energyLeft = maxEnergy;
        damage = 500;
        duration = 1;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }
    
    /**
     * 
     */
    public void act(){
        
        if(time%2==0&&foodLeft<maxFood&&time<maxFood*2){
            foodLeft++;
        }
        if(time%4==0&&time>maxFood*4+200){
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
    /**
     * Adds this plant to a specified world. 
     * 
     * @param W    The world that the plant is being added to. 
     */
    public void addedToWorld (World W){
        W.addObject(hpBar, getX(), getY());
        W.addObject(nrgBar, getX(), getY());
    }
    /**
     * Returns the amount of damage the plant deals as recoil. Should be called by the attacking animal. 
     * 
     * @return int  Amount of damage dealt.
     */
    
    public void attacked(Animal animal) {
        animal.damage(damage);
    }
    /**
     * 
     */
    public int eaten(){
        if(maxFood*bite>foodLeft){
            foodLeft=0;
            return foodLeft;
        }
        foodLeft-=(int)(maxFood*bite);
        return (int)(maxFood*bite);
    }
    
    /**
     * Does nothing because the thorned plants do not attack. 
     */
    public void attack(){
        //does not attack
    }
}