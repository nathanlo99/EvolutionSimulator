import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An actor that gives a poisoned effect to any animal that attacks it. It will grow, then decay once spawned on the world. 
 * Its health will be decrease when attacked, and it will die faster. 
 * The poison effect is different for each instance of the actor, as it is randomly generated. However, the poison value is constant for every instance. 
 *
 * @author Jerry Liu
 * @version Apr 2017
 */
public class PoisonPlant extends Plant
{
    

    private static final double bite=0.25;

    /**
     * Constructs a new thorn plant with set health and energy, and random poison lethality and duration. 
     */
    public PoisonPlant(){
        maxFood = 40;
        maxEnergy = 300;
        type = 3;
        foodLeft = 1;
        energyLeft = maxEnergy;
        damage = Greenfoot.getRandomNumber(4)+1;
        duration = Greenfoot.getRandomNumber(9)+1;
        hpBar=new HealthBar(maxFood,1,this);
        nrgBar=new EnergyBar(maxEnergy,this);
    }

    /**
     * Checks if the plant is growing/decaying. Updates the HP and energy display. 
     */

    public void act(){
        if(time%3==0&&foodLeft<maxFood&&time<maxFood*3)foodLeft++;
        if(time%6==0&&time>maxFood*4+200)foodLeft--;
        if(foodLeft<=0)dead();
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
     * @return int[]  Amount of damage dealt. The first element is the amount of damage dealt per tick, and the second element is how many ticks the damage will persist over. 
     */

    public int[] attacked(){
        int [] store = {damage, duration};
        return store;
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
     * Does nothing because the poisoned plants do not attack. 
     */

    public void attack(){
        //does not attack
    }
}
