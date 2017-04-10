import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Girl here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Girl extends Animal
{

    public static final int MIN_HEALTH = 700, MAX_HEALTH = 1000;
    public static final int MIN_ENERGY = 400, MAX_ENERGY = 600;
    public static final int MIN_ARMOR = 0, MAX_ARMOR = 10;
    public static final int MIN_SPEED = 1, MAX_SPEED = 5;

    public Girl() {
        this(Greenfoot.getRandomNumber(MAX_HEALTH - MIN_HEALTH) + MIN_HEALTH,
            Greenfoot.getRandomNumber(MAX_ENERGY - MIN_ENERGY) + MIN_ENERGY,
            Greenfoot.getRandomNumber(MAX_ARMOR - MIN_ARMOR) + MIN_ARMOR,
            Greenfoot.getRandomNumber(MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }

    public Girl(double maxH, double maxE, double armor, double speed) {
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
     * Act - do whatever the Girl wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (curEnergy >= REPRODUCE_THRESHOLD) {
            List<Guy> guys = getObjectsInRange(200, Guy.class);
            if (guys.size() != 0) {
                Guy target = guys.get(0);
                turnTowards(target.getX(), target.getY());
                move(speed);
            }
            Guy guy = (Guy)getOneIntersectingObject(Guy.class);
            if (guy != null && guy.canReproduce()) {
                getWorld().addObject(Animal.reproduce(this, guy), getX(), getY());
                this.reproduce();
                guy.reproduce();
            }
        } else {
            List<Plant> plants = getWorld().getObjects(Plant.class);
            if (plants.size() != 0) {
                Plant target = plants.get(0);
                turnTowards(target.getX(), target.getY());
                move(speed);
            }
            Plant p = (Plant)getOneIntersectingObject(Plant.class);
            if (p != null) {
                if(time%50==0){
                    eat(p);
                }
            }     
        }
        time++;
        super.act();
    }    
}
