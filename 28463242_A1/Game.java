import java.util.Scanner;

/**
 * This game class 
 * manages the playing of a game
 * @author Yiwei Chen
 * @version 1.0 15 April 2018
 */

public class Game
{
    private Player player1;
    private Player player2;
    private int num;
    private int max;
    private int min;
    private int count;

    /**
     * Creat a default Game
     */
    public Game()
    {
        player1 = new Player("name", -1, 0);
        player2 = new Player();
        num = 0;
        max = 101;
        min = 0;
        count = 1;
    }

    /**
     * Creat a non-default Game
     */
    public Game(Player player1, Player player2, int num, 
                   int max, int min, int count)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.num = num;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    /**
     * Compare the guess with key
     */    
    public void compareRange(int answer)
    {
        if ((min < num) && (num < max))
        {
            if(num < answer) 
                min = num;
            else
                max = num;
            System.out.println(min + " < Answer < " + max);
        }
        else
            System.out.println("Warning: Out of range! ");
    }

    /**
     * The main part of this class, control the flow of the game
     */
    public void gamePlay()
    {
        System.out.println("Welcome to the  Gue55ing Game!");
        namePlayer1();
        for (int round = 1; round < 5; round++)
        {
            RandomNumber answer = new RandomNumber(0, 101);
            RandomNumber abandonRound = new RandomNumber(0, 21);
            RandomNumber rollFirst = new RandomNumber(0,3);
            while (count < 7)
            {                
                System.out.println("Round: " + round + "/4");
                System.out.println("Turn: " + (count+1)/2 + "/3");
                if (rollFirst.getRandomNumber() == 1)
                {
                    playerPlay(abandonRound.getRandomNumber(), 
                                  answer.getRandomNumber(), player1);
                    if (count < 7)
                        playerPlay(abandonRound.getRandomNumber(), 
                                      answer.getRandomNumber(), player2);
                }
                else
                {
                    playerPlay(abandonRound.getRandomNumber(), 
                                  answer.getRandomNumber(), player2);
                    if (count < 7)
                        playerPlay(abandonRound.getRandomNumber(), 
                                      answer.getRandomNumber(), player1);
                }
                System.out.println(""); 
            }        
            if (count != 21)
                printScore(answer.getRandomNumber());
            reset();
        }
        System.out.println("Game over!");
        System.out.println(getResult());
    }

    /**
     * Get the result of the game
     */
    public String getResult()
    {
        if (player1.getScore() > player2.getScore())
            return "You win";
        else
            return (player1.getScore() == player2.getScore() ? "Draw" : "You lose");
    }

    /**
     * Name a human player 
     */
    public void namePlayer1()
    {
        Scanner scanName = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String name = scanName.nextLine().trim();
        while ((name.length() > 8) || (name.length() == 0))
        {
            System.out.println("Warning: Name should be 1-8 character(s)!");
            System.out.print("Please re-enter your name: ");
            name = scanName.nextLine().trim();
        }
        player1.setName(name);
        System.out.println("Hello, " + player1.getName());
        System.out.println("");
    }

    /**
     * Human player enters a suitable number
     */    
    public void player1MakeGuess()
    {
        boolean isInt = false;
        while (isInt == false)
        {
            Scanner scanNum = new Scanner(System.in);
            System.out.println("Please enter a number: ");
            if (scanNum.hasNextInt() == true)
            {
                num = scanNum.nextInt();
                if (((num < 1) || (num > 100)) && (num != 999))
                    System.out.println("Number must between 1-100(or 999)! Do again");
                else
                    isInt = true;
            }
            else
                System.out.println("Input error! You should enter a number!");
        }
    }

    /**
     * Computer player guesses a suitable number
     */    
    public void player2MakeGuess() 
    {
        RandomNumber p2Guess = new RandomNumber(min, max);
        num = p2Guess.getRandomNumber();
        System.out.println("Computer player : ");
        System.out.println(num);
    }

    /**
     * One Attempt made by a given player 
     */
    public void playerPlay(int abandon, int answer, Player player)
    {
        if (player == player1)
            player1MakeGuess();
        else
            player2MakeGuess(); 
        RandomNumber roll = new RandomNumber(0,21);       
        if ((num != 999 && player == player1) || 
            (abandon != roll.getRandomNumber() && player == player2))
        {   
            player.setGuesses(num);
            if (num != answer)
                compareRange(answer);
            else
            {
                player.setScoreRight(count);
                count = 10;
            }
            if (count == 6)
            {
                player1.setScoreWrong(Math.abs(player1.getGuesses() - answer));
                player2.setScoreWrong(Math.abs(player2.getGuesses() - answer));
            }
        }
        else
        {
            System.out.println("Abandon this round!");
            count = 20;
        }
        count++;
    }

    /**
     * Print the state at end of every round
     */
    public void printScore(int answer)
    {
        System.out.println("Answer is " + answer);
        System.out.println(player1.getState());
        System.out.println(player2.getState());
        System.out.println("");
    }

    /**
     * At the end of every round, rest the game
     */
    public void reset()
    {
        max = 101;
        min = 0;
        count = 1;
    }
}