package base;
import java.util.*;

/**
 * The <code>Sets</code> class contains many methods that deal with modifying ArrayLists of Cards. Although the methods
 * located here are primarily used in the <code>Set</code> class, they can be used anywhere in the project.
 * @author JL
 * @see Card
 * @see Set
 */
public class Sets {
	
	/**
	 * Sorts an inputted ArrayList by swapping the cards around until each card with the same name is next to 
	 * another. Uses the <code>swap()</code> method. Sorts lands to the front of the set.
	 * @param set The ArrayList of cards to sort.
	 */
	public static void sort(ArrayList<Card> set) {
		for (int i = 0; i < set.size()-1; i++) {
			for (int z = i+1; z < set.size(); z++) {
				if (!set.get(i).isLand && set.get(z).isLand) {
					swap(set, i, z);
				}
			}
		}
		for (int i = 0; i < set.size()-1; i++) {
			for (int z = i+1; z < set.size(); z++) {
				if (set.get(z).cardName.equals(set.get(i).cardName)) {
					swap(set, i+1, z);
					break;
				}
			}
		}
	}

	/**
	 * Swaps the contents located at two different indexes in a specified ArrayList of Cards.
	 * @param list The ArrayList of Cards
	 * @param index1 The first index
	 * @param index2 The second index
	 */
	public static void swap(ArrayList<Card> list, int index1, int index2) {
		Card c = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, c);
	}
	
	/**
	 * Searches an inputted array of Cards for a particular card.
	 * @param c The Card to search for to chekc
	 * @param set The ArrayList of Cards
	 * @return true if the card is found, and false if not.
	 */
	public static boolean cardIsInSet(Card c, ArrayList<Card> set) {
		for (Card z: set) {
			if (z.equals(c)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Searches an inputted array of Cards for any DrawCard using the isDrawCard boolean.
	 * @param set The ArrayList of Cards to check
	 * @return true if any card in the set is a draw card, and false if not.
	 */
	public static boolean drawCardInSet(ArrayList<Card> set) {
		for (Card c: set) {
			if (c.isDrawCard) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks the entire set for draw cards, incrementing a variable each time one is found.
	 * @param set The ArrayList of Cards to check
	 * @return The number of Cards in the set with the isDrawCard boolean set to true.
	 */
	public static int numDrawCardsInSet(ArrayList<Card> set) {
		if (!drawCardInSet(set)) {
			return 0;
		}
		int num = 0;
		for (Card c: set) {
			if (c.isDrawCard) {
				num++;
			}
		}
		
		return num;
	}
	
	/**
	 * Checks the entire set for land cards, incrementing a variable each time one is found.
	 * @param set The ArrayList of cards to check
	 * @return the number of Cards in the set with the isLand boolean set to true.
	 */
	public static int numLandsInSet(ArrayList<Card> set) {
		if (set.size() == 0) {
			return 0;
		}

		int num = 0;
		for (Card c: set) {
			if (c.isLand) {
				num++;
			}
		}

		return num;
	}
	
	/**
	 * Checks the entire set for land cards of a specific type by checking the names of the cards in the set against
	 * the name of the target land.
	 * @param name The name of the land to look for
	 * @param set The ArrayList of cards to check
	 * @return the number of lands of the type inputted, or the number of cards with the same name as the one inputted.
	 */
	public static int numLandsOfType(String name, ArrayList<Card> set) {
		int num = 0;
		for (Card c: set) {
			if (c.cardName.equals(name)) {
				num++;
			}
		}

		return num;
	}
	
	/**
	 * Checks the entire set for Cards that are lands, then adds them to an ArrayList of names if the name is unique.
	 * @param set The ArrayList of Cards to check
	 * @return the number of types of lands in a set, or the size of the ArrayList of unique names of lands in the set.
	 */
	public static int numTypeOfLandsInSet(ArrayList<Card> set) {
		ArrayList<String> listOfLands = new ArrayList<String>();
		for (Card c: set) {
			if (c.isLand && (listOfLands.size() == 0 || !isInList(c.cardName, listOfLands))) {
				listOfLands.add(c.cardName);
			}
		}

		return listOfLands.size();
	}
	
	/**
	 * Checks if an inputted String is located in an inputted ArrayList of Strings, similar to the cardIsInSet() method.
	 * @param s The String to look for
	 * @param list The ArrayList of Strings to check
	 * @return true if the ArrayList contains at least one copy of the String inputted, or false if not.
	 */
	public static boolean isInList(String s, ArrayList<String> list) {
		for (String z: list) {
			if (s.equals(z)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructs a String that outputs the entire contents of an ArrayList of Cards in a readable format.
	 * @param set The ArrayList of cards to read
	 * @param setName the name of the set in String format
	 * @return a readable String representation of cards in the set.
	 */
	public static String read(ArrayList<Card> set, String setName) {
		sort(set);
		String s = "";
		if (set.size() > 0) {
			s += "Your " + setName + " consists of: ";
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<Integer> nums = new ArrayList<Integer>();

			names.add(set.get(0).cardName);
			for (Card c: set) {
				if (c.cardName.equals(names.get(names.size()-1))) continue;
				boolean newName = false;
				for (String z: names) {
					if (!c.cardName.equals(z)) {
						newName = true;
						break;
					}
				}
				if (newName) {
					names.add(c.cardName);
				}
			}
			for (String z: names) {
				nums.add(0);
				for (Card c: set) {
					if (c.cardName.equals(z)) {
						nums.set(nums.size()-1, nums.get(nums.size()-1) + 1);
					}
				}
			}

			for (int i = 0; i < names.size(); i++) {
				Character end = names.get(i).charAt(names.get(i).length()-1);
				if (i != names.size()-1) {
					if (nums.get(i) > 1 && !end.equals('s')) {
						s += nums.get(i) + " " + names.get(i) + "s, ";
					} else {
						s += nums.get(i) + " " + names.get(i) + ", ";
					}
				} else {
					if (names.size() > 1) {
						s += "and ";
					}
					if (nums.get(i) > 1 && !end.equals('s')) {
						s += nums.get(i) + " " + names.get(i) + "s.";
					} else {
						s += nums.get(i) + " " + names.get(i) + ".";
					}
				}
			}
		} else {
			s += "Your " + setName + " has no cards in it.";
		}

		return s;
	}
	
	/**
	 * Shuffles the inputted set of Cards by constructing a new set of the same contents with random placement.
	 * @param set The set to base the shuffled set on
	 * @return the shuffled set.
	 */
	public static ArrayList<Card> shuffle(ArrayList<Card> set) {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (set.size() > 0) {
			int index = (int) (Math.random()*set.size());
			newDeck.add(set.get(index));
			set.remove(index);
		}
		return newDeck;
	}
	
	/**
	 * Prints to the console the String representation of the set using the read() method.
	 * @param set the set to read/output
	 * @param setName the name of the set
	 */
	public static void show(ArrayList<Card> set, String setName) {
		System.out.println(read(set, setName));
	}
}
