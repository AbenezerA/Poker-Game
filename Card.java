/* Abenezer Amanuel
 * ata2152
 * 12/1/2021
 * This class creates a card and determines what happens whenever we compare or
 * print a card
*/

//we first import all java util packages
import java.util.*;

public class Card implements Comparable<Card>{
	
    //instance variables
	private int suit;
	private int rank;
    private String suitString;
    private String rankString;
	
    //this constructor constructs a Card with integer suit and rank values
	public Card(int s, int r) {
        suit = s;
        rank = r;
        
	}
    
    //this method decrees that everytime it is called on a Card, the difference
    //in the rank of the implicit and explicit Cards is retruned.
    //useful to sort and to determine pairs of cards
	public int compareTo(Card c) {                
        return this.rank - c.rank;
	}
    
    //this method returns the suit value of a card as an integer
    public int getSuit() {        
        return suit;        
    }
    
    //this method converts the int suit value to a string
    public String suitIntToString() {     
        
        switch (suit) {                       
            case 1 :
                suitString = "clubs";
                break;
            case 2 :
                suitString = "spades";
                break;
            case 3 :
                suitString = "diamonds";
                break;
            case 4 :
                suitString = "hearts";
                break;                        
        }
        
        return suitString;
    }
    
    //this method returns the int rank value to a string
    public String rankIntToString() {
        
        switch (rank) {                        
            case 1 :
                rankString = "Ace";  //special strings for ranks 1, 11, 12, 13
                break;
            case 11 :
                rankString = "Jack";
                break;
            case 12 :
                rankString = "Queen";
                break;
            case 13 :
                rankString = "King";
                break;
            default :
                rankString = String.valueOf(rank);
                break;
        }
        
        return rankString;        
    }
    
	//this method returns a meaningful phrase to print everytime a Card object is
	//printed
	public String toString(){
        return this.rankIntToString() + " of " + this.suitIntToString();
	}

}
