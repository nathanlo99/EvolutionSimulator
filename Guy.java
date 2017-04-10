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
    private int bite = 0;
    private int timeInterval = 30;
    private GreenfootImage ragePic = new GreenfootImage("2.png");
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
            move(speed);
        }
        Plant p = (Plant)getOneIntersectingObject(Plant.class);
        if(bite==3) activateRage();
        if (p != null) {
            if(time%30==0&&rageMode==false){
                eat(p);
                bite++;
                speed = 0;
            }
        }
        
        time++;
        super.act();
        
    }
    private void activateRage(){
        speed = speed*2;
        setImage(ragePic);
    }
}
