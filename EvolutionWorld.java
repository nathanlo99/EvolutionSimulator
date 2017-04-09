import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class EvolutionWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EvolutionWorld extends World
{

    /**
     * Constructor for objects of class EvolutionWorld.
     * 
     */
    public EvolutionWorld()
    {
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1,false );
        setPaintOrder(HealthBar.class,EnergyBar.class,StabPlant.class,ShooterPlant.class,Projectile.class);
        StabPlant.setUp();
    }

    /**
     * Static method that gets the distance between the x,y coordinates of two Actors
     * using Pythagorean Theorum.
     * 
     * @param a     First Actor
     * @param b     Second Actor
     * @return float
     */
    public static float getDistance (Actor a, Actor b)
    {
        double distance;
        double xLength = a.getX() - b.getX();
        double yLength = a.getY() - b.getY();
        distance = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
        return (float)distance;
    }
    
    public void killAnimal(Animal a) {
        // TODO gather statistics?
        removeObject(a);
    }
}
