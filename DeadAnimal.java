import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DeadAnimal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeadAnimal extends Actor
{
    public DeadAnimal(){
        Greenfoot.playSound("roar"+(Greenfoot.getRandomNumber(3))+".wav");
    }
    /**
     * Act - do whatever the DeadAnimal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        getImage().setTransparency(getImage().getTransparency() - 1);
        if (getImage().getTransparency() == 0) {
            getWorld().removeObject(this);
        }
    }  
}
