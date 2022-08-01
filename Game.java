/* Abenezer Amanuel
 * ata2152
 * 12/1/2021
 * This class plays the game by either testing a hand of cards passed through
 * the command line or by generating a random hand for the player and checking
 * its value, accounting for bets in each case.
*/

//we first import all java util packages
import java.util.*;

public class Game {
	
    //Instance variables
	private Player p;
	private Deck cards;
    private Card c;
    private ArrayList<Card> testArray;
    private String[] testHandArr;
    private ArrayList<Card> checkHandArray;
    private ArrayList<Card> displayHandArray;
    private int payout;
    private boolean gameStopped;
    
	//this version of the constructor accepts a test hand of cards passed through 
	//the command-line and checks its value and payout
	public Game(String[] testHand){
        
        //we initialize the testHandArr array and testArray arraylist
        testHandArr = testHand;
        testArray = new ArrayList<Card>();
        
        //Card requires int parameters as suit and rank, so we utilize a loop
        //and srStringToInt() to convert and create actual Card objects for
        //each card in the test array
        for(int i = 0; i < 5; i++) {            
            char[] charArray = testHandArr[i].toCharArray();
            
            switch (charArray[0]) {                    
                case 'c' : 
                    charArray[0] = '1';
                    break;
                case 's' : 
                    charArray[0] = '2';
                    break;
                case 'd' : 
                    charArray[0] = '3';
                    break;
                case 'h' : 
                    charArray[0] = '4';
                    break;
                default :
                    System.out.println("Incorrect suit input!");
                    System.exit(0);
            }
            
            testHandArr[i] = String.valueOf(charArray);
            int suitInt = (this.srStringToInt(testHandArr[i]))[0];
            int rankInt = (this.srStringToInt(testHandArr[i]))[1];
            c = new Card(suitInt, rankInt);
            testArray.add(c);
            
        }
        
        //We create a Player instance and initialize payout and gameStopped. We
        //also invoke playerBetMaker() to ask for a bet and deduct from bankroll
        p = new Player();
        payout = 0;
        gameStopped = false;        
        p.playerBetMaker();       
        checkHandArray = testArray;		
	}
    
    //This version of the constructor creates a shuffled deck, deals the top 5
    //cards, checks their value and adjusts the payout. It can repeat all this
    //for another round if the player so chooses
	public Game(){        
        p = new Player();
        cards = new Deck();
        
        //Because the test file always invokes play() a boolean was created to
        //check if the player has chosen to stop and not change bankroll if so
        gameStopped = false;
        
        //this loop creates multiple rounds if the player so chooses
        for(;;) {            
            //one round includes making bets and dealing the top cards
            p.playerBetMaker();
            p.playerHandMaker();   
            
            checkHandArray = p.getHand();
            payout = 0;
            
            //one round also includes checking the value of the hand usig play()
            this.play();
            
            System.out.println("Do you wish to continue? n/y");
            Scanner scan0 = new Scanner(System.in);
            String response = scan0.nextLine();
            
            //the game ends if the player chooses to stop or is out of bankroll
            if ((response.equals("n")) || (p.getBankroll() <= 0.0)) {
                gameStopped = true;
                payout = 0;
                System.out.println("Thanks for playing!");
                break;
            } else if (response.equals("y")) {
            
            } else {
                System.out.println("Incorrect input.");
                System.exit(0);
            }
        }
        
	}
	
    //This method checks the hand and calculates bankroll based on its value
	public void play(){
        
        //if the player has not chosen to stop, check the hand
        if (!(gameStopped)) {
            
            this.checkHand(checkHandArray);
            System.out.println("Result: " + this.checkHand(checkHandArray));
            
        } else {            
            //don't check the hand if the game has been stopped            
        }
          
        p.winnings(this.getPayout());
        System.out.println("Your balance: $" + p.getBankroll());
                
	}
	
    //this method returns the value of the hand after sorting and checking it
	public String checkHand(ArrayList<Card> playerHand){        
        //we invoke sorter() to sort the hand
        this.sorter(playerHand);
        
        boolean similarSetFound = false;
        int simSetQuantity = 0;
        int simSetIndex = 0;
        int simSetSize = 1;
        int simSetSizeExtra = 1;
        
        //this loop checks for pair values
        for (int i= 0; i < 4; i++) {
                                    
            if((playerHand.get(i).compareTo(playerHand.get(simSetIndex)) == 0) && (i != 0) ) {                
                continue;                
            } else {                
                for (int j = i+1; j < 5; j++) {                
                 
                    if(playerHand.get(j).compareTo(playerHand.get(i)) == 0) {
                        similarSetFound = true;
                        if((playerHand.get(j).compareTo(playerHand.get(simSetIndex)) != 0) 
                           && (simSetIndex !=0)) {
                            simSetQuantity++;
                            simSetSizeExtra++;
                        } else if (simSetIndex == 0) {
                            simSetQuantity++;
                            simSetSize++;
                        } else {
                            simSetSize++;
                        }
                        simSetIndex = j;                    
                    }
                
                }                 
            }
            
        }
        
        boolean isStraight = false;
        boolean isFlush = false;
        boolean isRoyal = false;
        
        //these if statements check for a straight
        if ((((playerHand.get(1)).compareTo(playerHand.get(0))) == 1) 
            || (((playerHand.get(1)).compareTo(playerHand.get(0))) == 9)) {
            if ((((playerHand.get(2)).compareTo(playerHand.get(1))) == 1) 
                || (((playerHand.get(2)).compareTo(playerHand.get(1))) == 9)) {
                if ((((playerHand.get(3)).compareTo(playerHand.get(2))) == 1) 
                    || (((playerHand.get(3)).compareTo(playerHand.get(2))) == 9)) {
                    if ((((playerHand.get(4)).compareTo(playerHand.get(3))) == 1) 
                        || (((playerHand.get(4)).compareTo(playerHand.get(3))) == 9)) {
                            isStraight = true;
                    }
                }
            }
        }
        
        //these if statements check for a flush
        if (((playerHand.get(1)).getSuit()) == ((playerHand.get(0)).getSuit())) {
            if (((playerHand.get(2)).getSuit()) == ((playerHand.get(1)).getSuit())) {
                if (((playerHand.get(3)).getSuit()) == ((playerHand.get(2)).getSuit())) {
                    if (((playerHand.get(4)).getSuit()) == ((playerHand.get(3)).getSuit())) {
                        isFlush = true;
                    }
                }
            }
        }
        
        //this if statement checks for a royal flush
        if ((isStraight) && (isFlush) 
            && ((playerHand.get(1).compareTo(playerHand.get(0))) == 9)) {
                isRoyal = true;
        }
        
        //these if statements compare the checks above, return the result as a
        //string, and adjust the value of payout accordingly
        if (similarSetFound == true) {
                  
            if ((simSetQuantity == 1) && (simSetSize == 2)) {
                payout = 1;
                return "One Pair";
            } else if ((simSetQuantity == 2) && (simSetSize == 2) && (simSetSizeExtra ==2)) {
                payout = 2;
                return "Two Pairs";
            } else if ((simSetQuantity == 1) && (simSetSize == 3)) {
                payout = 3;
                return "Three of a kind";
            } else if ((simSetQuantity == 2) && (simSetSize == 3) && (simSetSizeExtra ==2)) {
                payout = 6;
                return "Full House";
            } else if ((simSetQuantity == 1) && (simSetSize == 4)) {
                payout = 25;
                return "Four of a kind";
            } else {
                return "Error";
            }
            
        } else {
            
            if ((isStraight) && (isFlush)) {
                if (isRoyal) {
                    payout = 250;
                    return "Royal Flush";
                } else {
                    payout = 50;
                    return "Straight Flush";
                }
            } else if ((isStraight) && (!(isFlush))) {
                payout = 4;
                return "Straight";
            } else if ((!(isStraight)) && (isFlush)) {
                payout = 5;
                return "Flush";
            } else {
                payout = 0;
                return "No pair";
            }
                       
        }
             
    }
    
    //this method accepts a hand and sorts it using the Comparable interface
    public ArrayList<Card> sorter(ArrayList<Card> playerHand) {
        
        for (int i = 0; i < 5; i++) {  
            
            for (int j = i + 1; j < 5; j++) {
                
                if(playerHand.get(j).compareTo(playerHand.get(i)) < 0) {
                    Collections.swap(playerHand, i, j);
                }
                
            }
            
        }
        
        return playerHand;        
    }
    
    //this method converts the commandline inputs of the test hand from strings
    //into an integer array to pass into the Card constructor
    public int[] srStringToInt(String srString) {
        int[] srInt = new int[2];
        int suit1;
        int rank1;
        
        suit1 = Character.getNumericValue(srString.charAt(0));
        
        if (srString.length() > 2) {
            rank1 = Character.getNumericValue(srString.charAt(1)) + Character.getNumericValue(srString.charAt(2)) + 9; 
        } else {
            rank1 = Character.getNumericValue(srString.charAt(1));
        }
        srInt[0] = suit1;
        srInt[1] = rank1;
        return srInt;
    }
    
    //this method returns the payout accourding to the value of the hand
    public int getPayout() {
        return payout;
    }
}


