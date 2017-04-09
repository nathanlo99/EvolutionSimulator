import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Guy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guy extends Animal
{
    public Guy (double maxH, double maxE, double armor, double speed) {
        this.curHealth = this.maxHealth = maxH;
        this.curEnergy = this.maxEnergy = maxE;
        this.armor = armor;
        this.speed = speed;
        
        this.reproduceCooldown = 200;
        this.poisonCooldown = 0;
        
        this.healthBar = new HealthBar((int)maxHealth, this);
        this.energyBar = new EnergyBar((int)maxEnergy, this);
    }
    
    /**
     * Act - do whatever the Guy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
    }    
}
