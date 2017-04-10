import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An actor that deals damage to any animals that attack it. It will grow, then decay once spawn on the world. 
 * Its health will decrease when attacked, and it will die quicker. 
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
        damage = 2;
        duration = 1;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }
    
    /**
     * Checks if the plant is growing/decaying. Removes the actor if it runs out of health. Updates the HP and energy display. 
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
     * @return int[]  Amount of damage dealt. The first element is the amount of damage dealt per tick, and the second element is how many ticks the damage will persist over. For thorned plants, damage should only last for 1 tick. 
     */
    
    public int[] attacked() {
        int [] array = {damage, duration};
        return array;
    }
    /**
     * Runs when the plant is being eaten. Damages the plant, making it lose health. 
     * 
     * @return int  The amount of damage dealt. 
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
