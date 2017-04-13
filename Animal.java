import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An Animal who eats plants, and reproduces, passing on most of their traits 
 *  to their offspring. Includes a visual health bar and energy bar.
 *  
 * Nathan:
 *  - Basically did the whole thing, though Eric helped iron out some bugs
 *      with the animals constantly reproducing and eating too quickly.
 * Eric:
 *  - Added fix to where animals would move around the plant while eating.
 *      
 * @author Nathan Lo
 * @version 0.1
 */
public abstract class Animal extends SmoothMover {
    protected double curEnergy, maxEnergy, curHealth, maxHealth; // 
    protected double reproduceCooldown; // The cooldown for reproduction
    protected double poisonCooldown, poisonDamage; // Poison things
    protected double armor, speed; // Armor reduces damage
    protected HealthBar healthBar; // Displays health
    protected EnergyBar energyBar; // Displays energy
    protected int time; // Time alive

    public static final double NO_ENERGY = 1; 
    // Health lost per tick if animal is out of energy
    public static final int MUTATION_RATE = 10;
    // 1 in MUTATION_RATE chance of mutating stats
    public static final double REPRODUCE_THRESHOLD = 50.0;
    // Animals can only reproduce if they have more than REPRODUCE_THRESHOLD energy
    public static final double REPRODUCE_ENERGY = 30.0;
    // Energy taken when reproducing
    public static final double ENERGY_PER_TICK = 0.5;
    // Energy lost per tick naturally
    
    /**
     * This method is called when the Animal is added to the World
     * @param w     The world the Animal was added to.
     * 
     */
    public void addedToWorld(World w) {
        // Add stat bars
        w.addObject(this.healthBar, getX(), getY());
        w.addObject(this.energyBar, getX(), getY());
    }

    /**
     * Produces an Animal from the two parents
     * @param first    First animal
     * @param second   Second Animal
     * @return Animal  The Animal produced after reproduction
     */
    public static Animal reproduce (Animal first, Animal second) { // When reproducing
        // Whose stats to take? First parent's or second parent's?
        double maxH = first.getMaxHealth();
        double maxE = first.getMaxEnergy();
        double armor = first.getArmor();
        double speed = first.getSpeed();
        
        if (Greenfoot.getRandomNumber(2) == 1) maxH = second.getMaxHealth();
        if (Greenfoot.getRandomNumber(2) == 1) maxE = second.getMaxEnergy();
        if (Greenfoot.getRandomNumber(2) == 1) armor = second.getArmor();
        if (Greenfoot.getRandomNumber(2) == 1) speed = second.getSpeed();
        
        // Mutate
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed++;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxH--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) maxE--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) armor--;
        if (Greenfoot.getRandomNumber(MUTATION_RATE) == 1) speed--;
        
        // Girl or Guy
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
        if (poisonCooldown % 50 == 0 && poisonCooldown != 0) trueDamage(poisonDamage);
        if (poisonCooldown == 0) poisonDamage = 0;
        if (reproduceCooldown != 0) reproduceCooldown--;
        if (curEnergy < 0) curHealth -= NO_ENERGY;
        if (curHealth < 0) die();
    }

    public void eat(Plant p) {
        int food = p.eaten() * 10;
        if(curHealth>=maxHealth) curEnergy = Math.min(curEnergy+food,maxEnergy);
        else curHealth = Math.min(curHealth + food, maxHealth);
        p.attacked(this);
    }
    
    /**
     * Method called when the Animal dies
     */
    public void die() {
        getWorld().addObject(new DeadAnimal(), getX(), getY());
        ((EvolutionWorld)getWorld()).killAnimal(this);
    }
    
    /**
     * Takes in damage, adjusted for armor
     * @param d     Amount of damage done
     */
    public void damage(double d) {
        curHealth -= d / armor;
    }

    /**
     * Takes in true damage
     * @param d     Amount of damage done
     */
    public void trueDamage(double d) {
        curHealth -= d;
    }
    
    /**
     * Drain the animal during reproduction and reset cooldown
     */
    public void reproduce() {
        curEnergy -= REPRODUCE_ENERGY;
        reproduceCooldown = 100;
    }
    
    /**
     * Poison the animal
     * @param d     Amount of damage to take per tick
     * @param t     Amount of time to apply the damage for
     */
    public void poison(double d, double t) {
        this.poisonCooldown += t;
        this.poisonDamage = Math.max(d,poisonDamage);
    }
    
    /**
     * Returns the max health
     * @return double The max health
     */
    public double getMaxHealth() { return maxHealth; }
    
    /**
     * Returns the max energy
     * @return double The max energy
     */
    public double getMaxEnergy() { return maxEnergy; }
    
    /**
     * Returns the armor
     * @return double The armor
     */
    public double getArmor() { return armor; }
    
    /**
     * Returns the speed
     * @return double The speed
     */
    public double getSpeed() { return speed; }
    
    /**
     * Returns whether or not the animal can currently reproduce
     * @return double Whether or not the animal can currently reproduce
     */
    public boolean canReproduce() { return reproduceCooldown > 0; }
}
