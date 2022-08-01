/* Abenezer Amanuel
 * ata2152
 * 12/1/2021
 * This class creates a deck, shuffles it, and deals the top card
*/

//we first import all java util packages
import java.util.*;

public class Deck {
	
    //instance variables
	private Card[] cards;
	private int top; 

	//this constructor initializes instance variables and constructs a deck of
    //unshuffled cards
	public Deck(){
		cards = new Card[52];
        top = 52;
        
        for(int i = 0; i < 4; i++) {
            
            for(int j = 0; j < 13; j++) {
                
                int tempIndex = (13*i) + j;
                cards[tempIndex] = new Card(i+1, j+1);
                
            }            
        }
        
	}
	
    //this method shuffles the deck
	public void shuffle(){
        
        top = 52;
        Random rando = new Random();
        
        for (int i = 0; i < 52; i++) {            
            int randIndex = rando.nextInt(52 - i) + i;
            
            Card temp = cards[i];
            cards[i] = cards[randIndex];
            cards[randIndex] = temp;            
        }

	}
	
    //this method deals the card at the top (last index), subtracting its index
    //by one each time it does so
	public Card deal(){
        top--;
        return cards[top];
	} 
	
    //this method returns the deck of shuffled or unshuffled cards
    public Card[] getDeck() {
        return cards;
    }

}
