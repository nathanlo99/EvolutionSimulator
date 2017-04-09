import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Animal extends SmoothMover {
    private double curEnergy, maxEnergy, curHealth, maxHealth;
    private double armor, speed, turnSpeed;
    public static final double NO_ENERGY = 1; 
    public static final int MUTATION_RATE = 10;
    public static final double REPRODUCE_THRESHOLD = 50.0;
    public static final double REPRODUCE_ENERGY = 30.0;
    
    // Amount of health lost per tick if the Animal has no health.
    
    public Animal (double maxH, double maxE, double armor, double speed, double turnSpeed) {
        this.curEnergy = this.maxEnergy = maxH;
        this.curHealth = this.maxHealth = maxE;
        this.armor = armor;
        this.speed = speed;
        this.turnSpeed = turnSpeed;
    }
    
    public Animal(Animal first, Animal second) { // When reproducing
        double maxH = first.getMaxHealth();
        double maxE = first.getMaxEnergy();
        double armor = first.getArmor();
        double speed = first.getSpeed();
        double turnSpeed = first.getTurnSpeed();
        if (Greenfoot.getRandomNumber(2) == 1) maxH = second.getMaxHealth();
        if (Greenfoot.getRandomNumber(2) == 1) maxE = second.getMaxEnergy();
        if (Greenfoot.getRandomNumber(2) == 1) armor = second.getArmor();
        if (Greenfoot.getRandomNumber(2) == 1) speed = second.getSpeed();
        if (Greenfoot.getRandomNumber(2) == 1) turnSpeed = second.getTurnSpeed();
        
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) turnSpeed++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) turnSpeed--;
        
        this.curEnergy = this.maxEnergy = maxH;
        this.curHealth = this.maxHealth = maxE;
        this.armor = armor;
        this.speed = speed;
        this.turnSpeed = turnSpeed;
    }
    
    /**
     * Act - do whatever the Animal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (curEnergy < 0) curHealth -= NO_ENERGY;
        if (curHealth < 0) die();
    }
    
    public void die() {
        ((EvolutionWorld)getWorld()).killAnimal(this);
    }
    
    private void reproduce(Animal other) {
        
    }
    
    public double getMaxHealth() { return maxHealth; }
    public double getMaxEnergy() { return maxEnergy; }
    public double getArmor() { return armor; }
    public double getSpeed() { return speed; }
    public double getTurnSpeed() { return turnSpeed; }
}
