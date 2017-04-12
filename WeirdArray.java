import java.util.*;
/**
 * Weird array is a single array for all your array needs.<br />
 * Need a string and an int in one array? no problem.<br />
 * Bool and double? easy.<br />
 * With Universal array, all types can be saved in one spot.<br />
 * Whats the use for this I hear you say? I have 0 clue.
 * 
 * @author Joey Ma
 * @version March 2017
 */
public class WeirdArray  
{
    private ArrayList<Object> objs;
    private int el;
    public WeirdArray()
    {
        objs=new ArrayList<Object>();
        el=0;
    }
    public WeirdArray(int i){
        objs=new ArrayList<Object>();
        for(int j = 0; j < i; j++)
            objs.add(null);
        el=i;
    }
    public <E> void add(E e){
        objs.add(e);
        el++;
    }
    public <E> void set(int i, E e){
        objs.set(i,e);
    }
    public <E> E get(int i){
        return (E)objs.get(i);
    }
    public int size(){
        return el;
    }
}
