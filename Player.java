/* Abenezer Amanuel
 * ata2152
 * 12/1/2021
 * This class creates a hand for the player, takes in a bet, and calculates
 * bankroll based on the value of the player's hand
*/

//we first import all java util packages
import java.util.*;

public class Player {
	
	// instance variables	
	private ArrayList<Card> hand;
	private double bankroll;
    private double bet;
    private Deck deck1;
	
    //this constructor creates an instance of Deck and initializes hand and 
    //bankroll
	public Player(){
        
        hand = new ArrayList<Card>();        
        deck1 = new Deck();        
        bankroll = 50.0;
                    
    }
    
    //this method receives the bet and deducts it from the bankroll
    public void playerBetMaker() {
        
        System.out.println("Enter the amount of tokens you'd like to bet"
                           + "(1-5): ");
        Scanner scan1 = new Scanner(System.in);
        bet = scan1.nextDouble();        
        this.bets(bet);
        System.out.println("Your balance remaining after bet: $" 
                           + this.getBankroll());
    }
    
    //this method creates a hand containing the top 5 cards of the shuffled deck
    public void playerHandMaker() {
        
        hand.clear();   //in case this isn't the first round
        
        deck1.shuffle();               
        hand.add(deck1.deal());
        hand.add(deck1.deal());
        hand.add(deck1.deal());
        hand.add(deck1.deal());
        hand.add(deck1.deal());        
              
        System.out.println("Your hand: " + hand);
        
        //this loop replaces cards (if the player chooses) from the hand
        for(int i = 0; i < 5; i++) {
                                    
            System.out.println("Enter the index of a card you wish to " + 
                               "replace (0-4) (Enter a negative to proceed to game): ");
            Scanner scan2 = new Scanner(System.in);
            int remIndex = scan2.nextInt();
                       
            if(remIndex >= 0) {                
               this.removeCard(hand.get(remIndex));
               this.addCard(remIndex, deck1.deal());
            } else {
                break; //the replacement ends if the user input is negative
            }
                    
        }
        
        System.out.println("Your hand after replacement: " + hand);
	}
    
    //this method adds a card to the player's hand
	public void addCard(int index, Card c){        
        hand.add(index, c);
	}
    
    //this method removes a card from the player's hand
	public void removeCard(Card c){        
        hand.remove(c);
    }
    
    //this method returns the player's hand as an arraylist
    public ArrayList<Card> getHand() {       
        return hand;        
    }
	
    //this method deducts the bet from the bakroll and checks validity of tokens
    public void bets(double amt){
        
        if ((amt <= 5) && (amt >= 1)) {            
            bankroll = bankroll - amt;                        
        } else {
            System.out.println("You can only bet 1-5 tokens!");
            System.exit(0);
        }
    }
    
    //this method receives the result of the players hand and adds its product
    //with the bet onto bankroll
    public void winnings(double odds){        
        bankroll = bankroll + (bet * odds);
    }
    
    //this method returns the player's current balance/bankroll
    public double getBankroll(){
        return bankroll;
    }

}


