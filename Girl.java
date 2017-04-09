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
                eat(p);
            }
        }
        
        super.act();
    }    
}
