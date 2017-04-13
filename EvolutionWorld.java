import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EvolutionWorld here.
 * 
 * @author Jerry Liu
 * 
 * Jerry Liu:
 *  - Random spawn thing
 * Joey Ma:
 *  - Score bar (with his Widget)
 * Nathan Lo:
 *  - Changed the random spawning 
 * Eric Sun:
 *  - Graphics of peashooter, animal and background.
 * @version Apr 2017
 */
public class EvolutionWorld extends World
{
    private ScoreBar score;
    private int animals;
    private int plants;
    /**
     * Constructor for objects of class EvolutionWorld.
     * 
     */
    public EvolutionWorld()
    {
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1, false);
        setPaintOrder(ScoreBar.class,HealthBar.class, EnergyBar.class, StabPlant.class, ShooterPlant.class, Projectile.class);
        StabPlant.setUp();
        score=new ScoreBar("Animals Alive: %i:3%   Plants Alive: %i:3%");
        addObject(score,960/2,15);
        animals=0;
        plants=0;
        spawnRandom();
        score.updateText();
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
        animals=Math.max(0,animals-1);
        removeObject(a);
    }

    /**
     * Spawns a given actor at a random spot within the world. 
     * 
     * @param a     The actor being added to the world.
     */

    public void spawnRandomSpot(Actor a){
        int x = getWidth();
        int y = getHeight()-40;
        addObject(a,Greenfoot.getRandomNumber(x),Greenfoot.getRandomNumber(y)+40);
    }

    /**
     * spawns 1-10 subclasses of Plant and 1-10 subclasses of Animal. Each plant/animal is randomly spawned in. 
     */

    public void spawnRandom(){
        for (int i = 0;i<Greenfoot.getRandomNumber(10)+5;i++){
            int plantType = Greenfoot.getRandomNumber(4);
            if(plantType==0)spawnRandomSpot(new StabPlant());
            else if(plantType==1)spawnRandomSpot(new PoisonPlant());
            else if(plantType==2)spawnRandomSpot(new ThornPlant());
            else if(plantType==3)spawnRandomSpot(new ShooterPlant());
            plants++;
        }
        for (int i = 0;i<Greenfoot.getRandomNumber(5)+2;i++){
            int animalType = Greenfoot.getRandomNumber(2);
            if(animalType==0)spawnRandomSpot(new Guy());
            else if(animalType==1)spawnRandomSpot(new Girl());
            animals++;
        }
        score.update(0,animals);
        score.update(1,plants);
    }
    
    public void act(){
        score.update(0,animals);
        score.update(1,plants);
        score.updateText();
        if (animals == 0 || plants == 0) {
            spawnRandom();
        }
    }

    public void newAnimal(){
        animals++;
    }

    public void newPlant(){
        plants++;
    }

    public void killPlant(){
        plants--;
        if (plants < 0) System.err.println("Hm weird...");
    }
}
