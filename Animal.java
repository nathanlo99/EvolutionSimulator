import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Animal extends SmoothMover {
    protected double curEnergy, maxEnergy, curHealth, maxHealth;
    protected double reproduceCooldown;
    protected double poisonCooldown, poisonDamage;
    protected double armor, speed;
    protected HealthBar healthBar;
    protected EnergyBar energyBar;
    
    public static final double NO_ENERGY = 1;
    public static final int MUTATION_RATE = 10;
    public static final double REPRODUCE_THRESHOLD = 50.0;
    public static final double REPRODUCE_ENERGY = 30.0;
    
    public static final double ENERGY_PER_TICK = 0.5;
    
    public void addedToWorld(World w) {
        w.addObject(this.healthBar, getX(), getY());
        w.addObject(this.energyBar, getX(), getY());
    }
    
    static Animal reproduce (Animal first, Animal second) { // When reproducing
        double maxH = first.getMaxHealth();
        double maxE = first.getMaxEnergy();
        double armor = first.getArmor();
        double speed = first.getSpeed();
        
        if (Greenfoot.getRandomNumber(2) == 1) maxH = second.getMaxHealth();
        if (Greenfoot.getRandomNumber(2) == 1) maxE = second.getMaxEnergy();
        if (Greenfoot.getRandomNumber(2) == 1) armor = second.getArmor();
        if (Greenfoot.getRandomNumber(2) == 1) speed = second.getSpeed();
        
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed--;
        
        if (Greenfoot.getRandomNumber(2) == 1) {
            return new Girl(maxH, maxE, armor, speed);
        } else {
            return new Guy(maxH, maxE, armor, speed);
        }
    }
    
    /** 
     * Act - do whatever the Animal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        poisonCooldown--;
        curEnergy -= ENERGY_PER_TICK;
        this.healthBar.update((int)curHealth);
        this.energyBar.update((int)curEnergy);
        if (poisonCooldown % 50 == 0 && poisonCooldown != 0) damage(poisonDamage);
        if (poisonCooldown == 0) poisonDamage = 0;
        if (reproduceCooldown != 0) reproduceCooldown--;
        if (curEnergy < 0) curHealth -= NO_ENERGY;
        if (curHealth < 0) die();
    }
    
    public void eat(Plant p) {
        int food = p.eaten() * 10;
        if (curEnergy >= maxEnergy) curHealth = Math.min(curHealth + food, maxHealth);
        else curEnergy = Math.min(curEnergy + food, maxEnergy);
    }
    
    public void die() {
        ((EvolutionWorld)getWorld()).killAnimal(this);
    }
    
    public void damage(double d) {
        curHealth -= d / armor;
    }
    
    public void reproduce() {
        curEnergy -= REPRODUCE_ENERGY;
        reproduceCooldown = 1000;
    }
    
    public void poison(double d, double t) {
        this.poisonCooldown += t;
        this.poisonDamage += d;
    }
    
    public double getMaxHealth() { return maxHealth; }
    public double getMaxEnergy() { return maxEnergy; }
    public double getArmor() { return armor; }
    public double getSpeed() { return speed; }
    public boolean canReproduce() { return reproduceCooldown == 0; }
}
