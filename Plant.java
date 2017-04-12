import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Plant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Plant extends SmoothMover
{
    //essentially hp
    protected int foodLeft;
    
    //starting hp
    protected int maxFood;
    
    //type of flower in an int, ie shooter melee poison thornmail
    protected int type;
    
    //starting energy
    protected int maxEnergy;
    
    //takes energy to attack you know
    protected int energyLeft;
    
    //lifespan
    protected int time;
    
    //damage
    protected int damage;
    
    //duration
    protected int duration;
    
    //healthbar
    protected HealthBar hpBar;
    
    //energybar
    protected EnergyBar nrgBar;
    
    /**
     * Call when plant is eaten by an animal, returns amount of food the plant gives.
     * @return int amount of food the animal gets from eating this plant.
     */
    abstract int eaten();
    
    /**
     * Dont worry about this. Called when plant dies.
     */
    public void dead(){
        ((EvolutionWorld)getWorld()).killPlant();
        getWorld().removeObject(this);
    }
    
    /**
     * Plant uses energy to attack nearest animal
     */
    abstract void attack();
    
    /**
     * Called when animal eats plant. Returns two numbers, damage per second and duration in seconds.
     * Used by poison and thorns.
     */
    abstract void attacked(Animal animal);
}
