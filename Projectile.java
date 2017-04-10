import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends SmoothMover
{
    private int startingR;
    private Animal target;
    private int damage;
    public Projectile(Animal target,int rotation,int damage){
        this.target=target;
        this.damage=damage;
        startingR=rotation;
    }

    public void addedToWorld(World w){
        setRotation(startingR);
    }

    /**
     * Act - do whatever the Projectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(target.getWorld()==null){getImage().setTransparency(getImage().getTransparency()-3); move(5);}
        else{
            for(int i = 0; i < 5; i++){
                int temp = getRotation();
                turnTowards(target.getX(),target.getY());
                int temp2 = getRotation();
                int dif = Math.abs(temp2-temp);
                if(temp!=temp2){
                    if(dif<180){
                        if(temp2>temp){
                            setRotation(temp+1);
                        }
                        else if(temp2<temp){
                            setRotation(temp-1);
                        }
                    }else{
                        if(temp2<temp-180){
                            setRotation(temp+1);
                        }
                        else if(temp2>temp-180){
                            setRotation(temp-1);
                        }
                    }
                }
                move(1);
            }
        }
        Animal a = (Animal)getOneIntersectingObject(Animal.class);
        if (a != null) {
            a.damage(damage);
            getWorld().removeObject(this);
        }
        if(getImage().getTransparency()<5&&getWorld()!=null) getWorld().removeObject(this);
    }
}
