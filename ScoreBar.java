import greenfoot.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * A completely modular and reusable score bar with the color and text format losely based off the code by Jordan Cohen.<br /><br />
 * <b>Format</b><br />
 * The text should be formatted in a special way. Literal characters should be left as is, but anywhere where a variable will be placed, put two percentage signs (%)
 * with the variable type, and format keys.
 * @author Joey Ma
 * @version March 2017
 */
public class ScoreBar extends Actor
{
    /**Mr. Cohen stuff*/
    // Declare Objects
    private GreenfootImage scoreBoard;
    private Color background;
    private Color foreground;
    private Font textFont;
    private String text;

    // Declare Variables:
    private int width;
    /**thats it*/

    private String textFormat;
    private WeirdArray array;
    private ArrayList<String> slices;

    /**
     * Construct a ScoreBar object with the set width and format.
     * If width is set to 0, the ScoreBar will automatically grab the size of the world as it's width.
     * @param width A single integer for the width of the scoreboard
     * @param format a single string for the format of the scoreboard
     * @see format
     */
    public ScoreBar(int width, String format){
        this(width,format,Color.WHITE,new Color(175, 20, 23));
    }

    /**
     * Construct a ScoreBar with set width, format, text color and background color.
     * If width is set to 0, the ScoreBar will automatically grab the size of the world as it's width.
     * @param width A single integer for the width of the scoreboard
     * @param format a single string for the format of the scoreboard
     * @param text color of the text
     * @param bg color of the background
     * @see format
     */
    public ScoreBar(int width, String format, Color text, Color bg){
        array = new WeirdArray();
        this.width=width;
        if(width!=0){
            scoreBoard = new GreenfootImage(width, 30);
            scoreBoard.setColor(background);
            scoreBoard.fill();
            setImage (scoreBoard);
            scoreBoard.setFont(textFont);
        }
        background = bg;
        foreground = text;
        textFont = new Font("Courier", Font.BOLD, 24);
        textFormat=format;
    }

    /**
     * Construct a ScoreBar with the set format. The width will automagically be set to the width of the world.
     * @param format a single string for the format of the scoreboard
     * @see format
     */

    public ScoreBar(String format){
        this(0,format);
    }

    /**
     * When the ScoreBar is added to the world and the width is 0, automagically set it to the width of the world.<br />
     * The string is then reformatted to fit the code's tastes, and the array is flooded by nulls.
     */
    public void addedToWorld(World world){
        //System.out.println(textFormat);
        if(width==0){
            width=world.getWidth();
            scoreBoard = new GreenfootImage(width, 30);
            scoreBoard.setColor(background);
            scoreBoard.fill();
            setImage (scoreBoard);
            scoreBoard.setFont(textFont);
        }
        Pattern pattern = Pattern.compile("%[^0-9](.*?)%");
        Matcher match = pattern.matcher(textFormat);
        int ind=0;
        while(match.find()){
            // System.out.println(match.group());
            //System.out.println(textFormat.charAt(match.start()+1+2*ind));
            textFormat=textFormat.substring(0,match.start()+1+2*ind)+ind+":"+textFormat.substring(match.start()+1+2*ind);
            ind++;
        }
        array = new WeirdArray(ind);
        //System.out.println(textFormat);
    }

    /**
     * Set the element at a position to a different element.<br />
     * (Named update instead of set because I put way too many updates in the world and I don't feel like ctrl+f-ing for them)<br />
     * (IF YOU ARE PUTTING A DOUBLE PLEASE FOR THE LOVE OF OUR LORD AND SAVIOUR JESUS CHRIST DO NOT PUT A INTEGER LIKE 4 PLEASE PUT 4.0 OR A DOUBLE)
     * @param pos the index (0-based) of the element to be updated
     * @param element the element to replace the old with
     */
    public <E> void update(int pos, E element){
        array.set(pos,element);
    }

    /**
     * Update the text the scorebar is displaying with the variables regardless of whether or not the variables have been updated, just for safety.
     */
    public void updateText(){
        text="";
        Pattern pattern = Pattern.compile("%[0-9]+(.*?)%");
        Matcher match = pattern.matcher(textFormat);
        int prev=0,ind=0;
        while(match.find()){
            text+=textFormat.substring(prev,match.start());
            if(array.get(ind)==null){
                text+="NULL";
            }
            else{
                Matcher match2 = Pattern.compile(":[isdb]").matcher(textFormat);
                match2.find(match.start());
                //System.out.println(textFormat.charAt(match2.start()+1));
                Matcher mat,mat2;
                int leng;
                switch(textFormat.charAt(match2.start()+1)){
                    case 'i':
                    //System.out.println(ind+" INT");
                    int inte = (Integer)array.get(ind);
                    mat = Pattern.compile(":[0-9]+%").matcher(textFormat);
                    mat.find(match2.start());
                    leng = Integer.parseInt(textFormat.substring(mat.start()+1,mat.end()-1));
                    //System.out.println(Integer.parseInt(textFormat.substring(mat.start()+1,mat.end()-1)));
                    text+=stringLengthLimiter(zeroAdder(inte,leng),leng);
                    break;

                    case 's':
                    //System.out.println(ind+" STRING");
                    String str = (String)array.get(ind);
                    text+=str;
                    break;

                    case 'd':
                    //System.out.println(ind+" DOUBLE");
                    mat = Pattern.compile(":[0-9]+\\.").matcher(textFormat);
                    mat.find(match2.start());
                    leng = Integer.parseInt(textFormat.substring(mat.start()+1,mat.end()-1));
                    mat2 = Pattern.compile("\\.[0-9]+%").matcher(textFormat);
                    mat2.find(mat.start());
                    int dec = Integer.parseInt(textFormat.substring(mat2.start()+1,mat2.end()-1));
                    double temp = (double) ((Double)array.get(ind))*Math.pow(10,dec-1);
                    temp=(int)temp;
                    temp=temp/Math.pow(10,dec-1);
                    text+=doubleConvert(temp,dec,leng);
                    break;

                    case 'b':
                    //System.out.println(ind+" BOOL");
                    boolean bool = (Boolean)array.get(ind); 
                    if(bool){
                        mat=Pattern.compile(":\\w+,").matcher(textFormat);
                        mat.find(match2.start());
                        text+=textFormat.substring(mat.start()+1,mat.end()-1);
                    }
                    else{
                        mat=Pattern.compile(",\\w+%").matcher(textFormat);
                        mat.find(match2.start());
                        text+=textFormat.substring(mat.start()+1,mat.end()-1);
                    }
                    break;
                }
            }
            prev=match.end();
            ind++;
        }
        //System.out.println(text);
        /**CODE PAWNED*/
        // Refill the background with background color
        scoreBoard.setColor(background);
        scoreBoard.fill();

        // Write text over the solid background
        scoreBoard.setColor(foreground);  
        // Smart piece of code that centers text
        int centeredY = (width/2) - ((text.length() * 14)/2);
        // Draw the text onto the image
        scoreBoard.drawString(text, centeredY, 22);
        /**CODE PAWNED END*/
    }

    /**
     * Convert a double to a string, but formatted with the number of decimal places specified, and as long as the length specified.<br />
     * Length takes precidence over decimals, so if the number has 15 places before the decimal and 4 after, dec is set to 4 and leng to 10, the string will only have
     * 10 digits and no decimals, as if it were a string of an int.
     * @param val the double to be converted
     * @param dec the number of decimal places to truncate to (no rounding)
     * @param leng the number of digits (the . is not a digit)
     * @return String formatted decimal as a String
     */
    public static String doubleConvert(double val, int dec, int leng){
        //preliminary conversion
        String temp = Double.toString(val);
        //find a . using regex
        Matcher mat = Pattern.compile("\\.").matcher(temp);
        if(mat.find()){
            //System.out.println(temp);
            //System.out.println(temp.length()-mat.end());
            
            //fatten up the decimals
            while(temp.length()-mat.end()<dec)
                temp+="0";
        }
        
        //System.out.println(temp);
        //fatten up the number overall
        while(temp.length()<=leng){
            temp="0"+temp;
        }
        
        //YOUR PRICE IS WAY TO HIGH YOU NEED TO CUT IT
        temp=stringLengthLimiter(temp,leng+1);
        
        //check if there is a decimal and change length of string depending on that
        mat = Pattern.compile("\\.").matcher(temp);
        if(!mat.find()||temp.charAt(leng)=='.')
            temp=stringLengthLimiter(temp,leng);
        //System.out.println(temp);
        //GIMME STRING
        return temp;
    }
    
    /**
     * POWER LEVEL LIMITER. Limit the length of a string starting from the right. DOES NOT ADD CHARACTERS.
     * @params value starting string
     * @params digits number of letters to keep
     */
    public static String stringLengthLimiter(String value, int digits){
        //9001
        return value.substring(0,digits);
    }

    /**
     * Method that aids in the appearance of the scoreboard by generating
     * Strings that fill in zeros before the score. For example:
     * 
     * 27 ===> to 5 digits ===> 00027
     * 
     * Ripped from Mr. Cohen
     * @param   value   integer value to use for score output
     * @param   digits  number of zeros desired in the return String
     * @return  String  built score, ready for display
     */
    public static String zeroAdder (int value, int digits)
    {
        // Figure out how many digits the number is
        int numDigits = digitCounter(value);

        // If not extra digits are needed
        if (numDigits >= digits)
            return Integer.toString(value);

        else // Build the number with zeroes for extra place values:
        {
            String zeroes = "";
            for (int i = 0; i < (digits - numDigits); i++)
            {
                zeroes += "0";
            }
            return (zeroes + value);
        }
    }

    /**
     * Useful private method that counts the digit in any integer.
     * 
     * Ripped from Mr. Cohen
     * @param number    The number whose digits you want to count
     * @return  int     The number of digits in the given number
     */
    private static int digitCounter (int number)
    {
        if (number < 10) {
            return 1;
        }
        int count = 0;
        while (number > 0) {
            number /= 10;
            count++;
        }
        return count;
    }
}