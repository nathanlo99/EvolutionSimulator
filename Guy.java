import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Guy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guy extends Animal
{
    
    private boolean rageMode = false;
    private boolean ifEat = false;
    private int bite = 0;
    private int timeInterval = 30;
    private GreenfootImage ragePic = new GreenfootImage("2.png");
    private double default_speed;
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
        if(bite==3){ activateRage(); rageMode=true;}
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
        speed = speed*2;
        timeInterval = 18;
        setImage(ragePic);
    }
}
