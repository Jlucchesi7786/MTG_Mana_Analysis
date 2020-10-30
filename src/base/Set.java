package base;
import java.util.*;

/**
 * A <code>Set</code> is an object that holds an ArrayList of <code>Card</code>s and can return certain information about
 * the set and the cards inside it, mainly using the <code>Sets</code> class.
 * @author JL
 * @see Card
 * @see Sets
 */
public class Set {
	ArrayList<Card> set;
	String name;
	
	public Set() {
		this("set");
	}
	
	public Set(String name) {
		this(name, new ArrayList<Card>());
	}
	
	public Set(ArrayList<Card> set) {
		this("set", set);
	}
	
	public Set(String name, ArrayList<Card> set) {
		this.set = set;
		this.name = name;
	}
	
	
	/**
	 * Shuffles the ArrayList contained by the set using the Sets object's shuffle() method.
	 */
	public void shuffle() {
		set = Sets.shuffle(set);
	}
	
	/**
	 * Shows the ArrayList contained by the set using the Sets object's show() method.
	 */
	public void show() {
		Sets.show(set, name);
	}
	
	/**
	 * Sorts the ArrayList contained by the set using the Sets object's sort() method.
	 */
	public void sort() {
		Sets.sort(set);
	}
	
	/**
	 * Searches for a Card in the ArrayList contained by the set using the Sets object's cardIsInSet() method.
	 * @param c the card to search for.
	 * @return True if the Card is found in the set, and false if otherwise.
	 */
	public boolean cardIsInSet(Card c) {
		return Sets.cardIsInSet(c, set);
	}
	
	/**
	 * Counts up the number of lands in the ArrayList contained by the set using the Sets object's numLandsInSet() method.
	 * @return The number of Lands in the set.
	 */
	public int numLands() {
		return Sets.numLandsInSet(set);
	}
	
	/**
	 * Counts the number of unique-named Land Cards in the ArrayList contained by the set using the Sets object's 
	 * numTypeOfLandsInSet() method.
	 * @return The number of type of Lands in the set.
	 */
	public int numTypeOfLands() {
		return Sets.numTypeOfLandsInSet(set);
	}
	
	/**
	 * Counts the number of Land Cards of a specific type in the ArrayList contained by the set using the Sets object's
	 * numLandsOfType() method.
	 * @param cardName the name of the Land Card to search for duplicates of.
	 * @return The number of Lands that have a specified name.
	 */
	public int numLandsOfType(String cardName) {
		return Sets.numLandsOfType(cardName, set);
	}
	
	/**
	 * Looks through the ArrayList contained by the set to see if there are any DrawCards.
	 * @return True if a single DrawCard is found, and false if not.
	 */
	public boolean hasDrawCard() {
		return Sets.drawCardInSet(set);
	}
	
	/**
	 * Checks the entire ArrayList contained by the set and finds how many DrawCards there are.
	 * @return The number of DrawCards in the set.
	 */
	public int numDrawCards() {
		return Sets.numDrawCardsInSet(set);
	}
	
	
	/**
	 * Adds an inputted Card to the ArrayList contained by the set.
	 * @param c The Card to add.
	 */
	public void add(Card c) {
		set.add(c);
	}
	
	/**
	 * Removes a specified Card from the ArrayList contained by the set.
	 * @param c The Card to remove.
	 */
	public void remove(Card c) {
		set.remove(c);
	}
	
	/**
	 * Removes the Card at the specified index in the ArrayList contained by the set.
	 * @param i the index containing the Card that is to be removed from the ArrayList.
	 */
	public void remove(int i) {
		set.remove(i);
	}
	
	/**
	 * Lets an outside force observe the Cards in the ArrayList contained by the set.
	 * @param index the index to see.
	 * @return The Card found at the specified index.
	 */
	public Card get(int index) {
		return set.get(index);
	}
	
	/**
	 * Checks the size of the ArrayList contained by the set.
	 * @return The size of the ArrayList contained by the set.
	 */
	public int size() {
		return set.size();
	}
}
