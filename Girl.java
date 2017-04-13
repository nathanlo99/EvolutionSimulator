import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Girls target Guy's when they have enough health and energy,
 *  and try to eat plants when they don't. When there's nothing
 *  left to do, they spin in circles
 * 
 * @author Nathan Lo
 * @version 0.1
 */
public class Girl extends Animal
{
    private boolean eating = false; // Whether or not the Animal is currently eating
    private Animal child; // The animal's child
    
    // Minimum and maximum values for random generation
    public static final int MIN_HEALTH = 700, MAX_HEALTH = 1000;
    public static final int MIN_ENERGY = 400, MAX_ENERGY = 600;
    public static final int MIN_ARMOR = 0, MAX_ARMOR = 10;
    public static final int MIN_SPEED = 2, MAX_SPEED = 5;

    /**
     * Random constructor for Girls, call this in default
     */
    public Girl() {
        this(Greenfoot.getRandomNumber(MAX_HEALTH - MIN_HEALTH) + MIN_HEALTH,
            Greenfoot.getRandomNumber(MAX_ENERGY - MIN_ENERGY) + MIN_ENERGY,
            Greenfoot.getRandomNumber(MAX_ARMOR - MIN_ARMOR) + MIN_ARMOR,
            Greenfoot.getRandomNumber(MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }

    /**
     * The general constructor for Girls
     * @param maxH      Max health
     * @param maxE      Max energy
     * @param armor     Armor
     * @param speed     The Girl's moving speed
     */
    public Girl(double maxH, double maxE, double armor, double speed) {
        this.curHealth = this.maxHealth = maxH;
        this.curEnergy = this.maxEnergy = maxE;
        this.armor = armor;
        this.speed = speed;
        this.reproduceCooldown = 200;
        this.poisonCooldown = 0;

        this.healthBar = new HealthBar((int)maxHealth, this);
        this.energyBar = new EnergyBar((int)maxEnergy, this);
        child = null;
    }

    /**
     * Act - do whatever the Girl wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Get all things they can reproduce with
        List<Guy> guys = getWorld().getObjects(Guy.class);
        
        // If the Animal hasn't reproduced, has enough health and energy to reproduce
        if (child == null && guys.size() != 0 && curHealth >= REPRODUCE_THRESHOLD && curEnergy >= REPRODUCE_THRESHOLD) {
            // Targets the first Guy
            Guy target = guys.get(0);
            turnTowards(target.getX(), target.getY());
            
            // Reproduces if possible
            Guy guy = (Guy)getOneIntersectingObject(Guy.class);
            if (guy != null && guy.canReproduce()) {
                child=Animal.reproduce(this, guy);
                getWorld().addObject(child, getX(), getY());
                this.reproduce();
                guy.reproduce();
                ((EvolutionWorld)getWorld()).newAnimal();
            }
        } else {
            // Otherwise, just target plants
            List<Plant> plants = getWorld().getObjects(Plant.class);
            if (plants.size() != 0) {
                Plant target = plants.get(0);
                turnTowards(target.getX(), target.getY());
            }
            
            // Eat the plants every 50 ticks
            Plant p = (Plant)getOneIntersectingObject(Plant.class);
            if (p != null) {
                if (time % 50 == 0){
                    eat(p);
                    eating = true;
                }
            } else {
                eating = false;
            }
        }
        
        // Move forward if not eating
        if (!eating) move(speed);
        setRotation(getRotation() + 2);
        time++;
        super.act();
    }    
}
