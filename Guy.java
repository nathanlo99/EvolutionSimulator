import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Guys target plants and eat. Each bite regains health but drains energy.
 * After 3 bites, it enters rageMode where speed and eatSpeed increases
 * until they die. 
 * 
 * @Eric
 * @version (0.1)
 */
public class Guy extends Animal
{
    // minimum and maximum health for random generation
    public static final int MIN_HEALTH = 700, MAX_HEALTH = 1000;
    public static final int MIN_ENERGY = 400, MAX_ENERGY = 600;
    public static final int MIN_ARMOR = 1, MAX_ARMOR = 10;
    public static final int MIN_SPEED = 1, MAX_SPEED = 5;
    
    
    private boolean rageMode = false;
    private boolean ifEat = false;
    private int bite = 0;
    private int timeInterval = 30;
    private GreenfootImage ragePic = new GreenfootImage("2.png");
    private double default_speed;
    
    /**
     * Default constructor for Guy and called by default. Randomly generates
     * stats for guy.
     */
    public Guy() {
        this(Greenfoot.getRandomNumber(MAX_HEALTH - MIN_HEALTH) + MIN_HEALTH,
            Greenfoot.getRandomNumber(MAX_ENERGY - MIN_ENERGY) + MIN_ENERGY,
            Greenfoot.getRandomNumber(MAX_ARMOR - MIN_ARMOR) + MIN_ARMOR,
            Greenfoot.getRandomNumber(MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }
    /**
     * More specific constructor for Guys that allows for custom health,
     * energy, armor and movespeed.
     * 
     * @param maxH      Max health
     * @param maxE      Max energy
     * @param armor     Armor
     * @param speed     Move speed of Guy
     */
    public Guy (double maxH, double maxE, double armor, double speed) {
        this.curHealth = this.maxHealth = maxH;
        this.curEnergy = this.maxEnergy = maxE;
        this.armor = armor;
        this.speed = speed;

        this.reproduceCooldown = 200;
        this.poisonCooldown = 0;

        this.healthBar = new HealthBar((int)maxHealth, this);
        this.energyBar = new EnergyBar((int)maxEnergy, this);
        time = 0;
        default_speed = speed;
    }

    /**
     * Act - do whatever the Guy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        List<Plant> plants = getWorld().getObjects(Plant.class);
        if (plants.size() != 0) {
            Plant target = plants.get(0);
            turnTowards(target.getX(), target.getY());
            if(ifEat == false)
            move(speed);
        }
        Plant p = (Plant)getOneIntersectingObject(Plant.class);
        if(bite==3&&!rageMode){ activateRage(); rageMode=true;}
        if (p != null) {
            if(time%timeInterval==0){
                eat(p);
                ifEat = true;
                bite++;
                speed = 0;
            }
        }else{
            if(rageMode ==true)
                speed = default_speed*2;
            else
                speed = default_speed;
            ifEat = false;
        }
        
        time++;
        super.act();
        
    }
    
    private void activateRage(){
        Greenfoot.playSound("laugh"+(Greenfoot.getRandomNumber(5))+".wav");
        speed = speed*2;
        timeInterval = 18;
        setImage(ragePic);
    }
}
