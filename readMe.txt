This program simulates a game of video poker, where the user is dealt a hand of
five cards from a shuffled deck and, based on the specific combination of cards
(the value) of this hand and on an initial bet made before the dealing of cards,
the user is paid out money. The user has the option of continuing for another 
round. The program also has a test feauture whereby the value of any set of 5 
cards passed through the command-line can be checked.

The PokerTest.java file contains the main method which can either take a hand of
5 cards as command-line arguments to test the working of the evaluation and bet-
making parts of the code or it can take in zero arguments, in which case the 
program works on a random hand of cards.

The two scenarios outlined above are accomplished by overriding the constructor
in the Game class. The actual game involving a random hand is ran by the 
constructor taking in no paramenters. This constructor creates an instance of
the object Player called p, which itself creates a deck of unshuffled cards and
an empty arraylist ready to hold the hand-to-be. The next steps are repeated 
for each round of game played. First, the constructor takes in and stores the 
bet issued by the user by invoking the playerBetMaker() method on p. By invoking
yet another method, playerHandMaker(), it shuffles the deck and fills said array
list with the top 5 cards in the deck. (For details on these processes, see 
descriptions of Deck and Player classes below) So on this array list of hands, 
the constructor invokes play() which checks the value and calculates payout. In
the end, the user is asked whether they'd like to play another round. If either
their bankroll is zero or negative or they have chosen to stop, the program
terminates.

The play() method calculates and prints the value and payout of the player's 
hand. However the layout of the PokerTest file is such that it will be invoked 
atleast twice: once for each round and once more at the end of every round 
because the for loop (the essence behind multiple rounds) is in the Game 
constructor. This would result in furthur evaluation of the card and, thus,
increment or decrement of the bankroll even after the user has stopped the game.
To counteract this, we use the boolean gameStopped to check if the game has not
been stopped and only then perform evaluation on the hand.

play() does this card evaluation by passing through the arraylist of hands into
checkHand(), which in turn invokes sorter() to sort the cards (details below)
and check their value. It first searches for similar sets of cards: pairs, 
three of a kind, four of a kind, full house or no pair, all using one complex
loop. The loop goes through the list keeping track of the number of similar sets
, the number of repeated numbers in each set (their size), and the index of the
first element of these sets. Using this index, it is able to recognize if it has
already counted a repeated number (instead of setting it as the first element of
yet another similar set) and whether one set of repeating numbers near the end 
is different from another, already accounted-for set. checkHand() then goes on
to check if the hand is a straight, flush, or royal flush using nested if
statements that check the similarity from left to right (or linear increment by 
1 in straights) of the suits and ranks of the cards. Using these variables,
play() first determines the similar sets in the hand, defines their payout, and
returns their value as a string. If there are no similar sets, it does the same
checking procedures for straight and flush properties.

The sorter() method accepts the arraylist hand and sorts it by increasing order
of rank. It does so by implementing the comparable interface of Card, which
returns the difference of the rank variable used to make the explicit Card
from that of the implicit Card. A negative compareTo value means the implicit
(the card to the right) has a smaller rank and so they swap places. The loop
goes through the entire list and returns a sorted list in the end.

The methods discussed up to this point pertain to the actual game involving no
commandline arguments through Game. If we were to pass through a set of five
cards in the command line using the following sytax:
                 ...~/workspace$ PokerTest c1 s8 d4 h13 s6
we could test the evaluation and bet-making processes of the program. Note above
c = clubs(1)      1 = Ace                13 = King
s = spades(2)     2-10 = Numeric values
d = diamonds(3)   11 = Jack
h = hearts(4)     12 = Queen
*So, h13 would represent the King of hearts, and s8 the Eight of spades

The Game constructor accepting a string array as a parameter receives these
input cards and converts the letters to integers representing suits according to
the encoding above and the numeric strings into numeric integers. This is done
by invoking the srStringToInt() method which returns, for each card, an array of
2 elements, its suit and rank in integer values. Using these int values, 5 Card
ojects are created and stored in an arraylist. This arraylist is the conjunction
point of the two processes and is treated similarly to the arraylist in the
other Game constructor: its value is checked and its payout (times the bet) is
added to the user's bankroll.

One important aspect of both processes is the creation of Card objects
representing cards. The Card constructor requires two parameters representing
the suit and the rank of the card. And so, any time we "create" a card, we're
effectively instantiating a Card Object and setting the values of two important
instance variables. Another feature to note is that we've overridden the
toString() of Card so that whenever we print a Card object, we're printing a
phrase containing its suite and rank in the following manner:
    (Card(3, 11) prints to "Jack of diamonds"
The necessary conversions of rank strings into int (with special ranks 1, 11, 12
, 13 accounted for) and that of suit strings into ints using the encoding 
discussed above are done by invoking rankIntToString() and suitIntToString()
respectively in the Card class.

Both the Deck and Player classes relate to the actual game involving random 
hands. A Deck object is instantiated first in tandem with the instantiation of
Player p in the Game constructor taking no parameters. Here the Player 
constructor creates an instance of Deck called deck1. The Deck constructor
creates an unshuffled array of 52 cards. The shuffle() method, when called,
shuffles the deck by going through each index, generating a random index, then
swapping the Cards at these two indices. Another method, deal(), deals the Card
at the top index, which is being tracked and decremented evertime the top card
is dealt. getDeck() returns the current state of a Deck object, shuffled or
unshuffled.

The Player constructor instantiates a Deck deck1 and initializes the bankroll to
a fixed value of $50. The Player class also contains playerBetMaker() which is
used by both Game constructors to ask for a bet of tokens from the user. It
first deducts this bet from the bankroll using bets() and prints out the
remaining bankroll before the hand is valued. The class also contains winnings()
which adds the product of the bet and value of the card onto bankroll when it is
invoked in the Game constructor.

Another important method in the Player class is playerHandMaker which creates a 
hand of 5 cards for the user to be evaluated. It does so by shuffling the deck
d1 and invoking deal() 5 times, adding the dealt card to the hand arraylist each
time. It prints this arraylist to the user, who has the option to replace none,
some or all of the cards in the hand. The program accomplishes this by asking
for the index of a single card to replace and repeating this for as long as the
user input is non-negative to a maximum of 5 instances (where all 5 cards are
replaced). The Card at the inputted index is removed using removeCard() and in
its place the next Card at the top of the shuffled Deck deck1 is added using
deal(). The finalized array is printed out to the user only after the final
Card is replaced (a negative is inputted). The getHand() method, when invoked on
a Player object, returns this finalized hand as an arraylist of Cards.

Ultimately, it's this arraylist of cards that gets checked by checkHand() in
Game and determines the payout to be multiplied with the bet and added on to
the bankroll. This final bankroll is printed out to the user: the ultimate goal
of the program.