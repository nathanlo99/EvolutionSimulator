import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Vanishing skull, basically copied word for word from Mr. Cohen's bug simulation
 * 
 * @author Nathan Lo (but more Mr. Cohen)
 * @version 0.1
 */

public class DeadAnimal extends Actor
{
    // Roar when something dies
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
