/**
 * Deal with the name, score and 
 * last guess of a player
 * and return a player's state.
 * @author Yiwei Chen
 * @version 1.0 15 April 2018
 */

public class Player
{
    private String name;
    private int score;
    private int guesses;

    /**
     * Creat a default Player
     */
    public Player()
    {
        name = "Siri";
        guesses = -1;
        score = 0;        
    }

    /**
     * Creat a non-default Player
     */
    public Player(String newName, int guess, int newScore)
    {
        name = newName;
        guesses = guess;
        score = newScore;
    }

    /**
     * Return the last guess
     */  
    public int getGuesses()
    {
        return guesses;
    }

    /**
     * Return player's name
     */
    public String getName()
    {
        return name;
    }    

    /**
     * Return player's score
     */    
    public int getScore()
    {
        return score;
    }  

    /**
     * Return player's state
     */    
    public String getState()
    {
        return name + "'s last guess is " + guesses + " and his score is " + score;
    }

    /**
     * record the last guess
     */
    public void setGuesses(int input)
    {
        guesses = input;
    }

    /**
     * change player's name
     */
    public void setName(String newName)
    {
        name = newName;
    }

    /**
     * set player's score when answer is right
     */
    public void setScoreRight(int count)
    {
        score = score + 26 - count * (6 + (7 - count)) / 2;
    }

    /**
     * set player's score when nobody get the right answer
     */
    public void setScoreWrong(int distance)
    {
        if ((10 - distance) > 0)
            score = score + 10 - distance;    
    }
}
