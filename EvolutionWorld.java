import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EvolutionWorld here.
 * 
 * @author Jerry Liu
 * @version Apr 2017
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
        super(960, 640, 1, false);
        setPaintOrder(HealthBar.class, EnergyBar.class, StabPlant.class, ShooterPlant.class, Projectile.class);
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
    
    /**
     * Spawns a given actor at a random spot within the world. 
     * 
     * @param a     The actor being added to the world.
     */
    
    public void spawnRandomSpot(Actor a){
        int x = getWidth();
        int y = getHeight();
        addObject(a,Greenfoot.getRandomNumber(x),Greenfoot.getRandomNumber(y));
    }
    
    /**
     * spawns 1-10 subclasses of Plant and 1-10 subclasses of Animal. Each plant/animal is randomly spawned in. 
     */
    
    public void spawnRandom(){
        int plantSpawn[] = new int [Greenfoot.getRandomNumber(9)+1];
        int animalSpawn[] = new int [Greenfoot.getRandomNumber(9)+1];
        for (int i = 0;i<plantSpawn.length;i++){
            int plantType = Greenfoot.getRandomNumber(4);
            if(plantType==0)spawnRandomSpot(new StabPlant());
            else if(plantType==1)spawnRandomSpot(new PoisonPlant());
            else if(plantType==2)spawnRandomSpot(new ThornPlant());
            else if(plantType==3)spawnRandomSpot(new ShooterPlant());
        }
        for (int i = 0;i<animalSpawn.length;i++){
            int animalType = Greenfoot.getRandomNumber(2);
            if(animalType==0)spawnRandomSpot(new Guy());
            else if(animalType==1)spawnRandomSpot(new Girl());
        }
    }
}
