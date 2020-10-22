package base;
import java.util.*;

public class Sets {
	//static ArrayList<Card> newSet = new ArrayList<Card>();
	
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

	public static void swap(ArrayList<Card> list, int index1, int index2) {
		Card c = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, c);
	}
	
	public static boolean cardIsInSet(Card c, ArrayList<Card> set) {
		for (Card z: set) {
			if (z.equals(c)) {
				return true;
			}
		}

		return false;
	}
	
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
	
	public static int numLandsOfType(String name, ArrayList<Card> set) {
		int num = 0;
		for (Card c: set) {
			if (c.cardName.equals(name)) {
				num++;
			}
		}

		return num;
	}
	
	public static int numTypeOfLandsInSet(ArrayList<Card> set) {
		ArrayList<String> listOfLands = new ArrayList<String>();
		for (Card c: set) {
			if (c.isLand && (listOfLands.size() == 0 || !isInList(c.cardName, listOfLands))) {
				listOfLands.add(c.cardName);
			}
		}

		return listOfLands.size();
	}
	
	public static boolean isInList(String s, ArrayList<String> list) {
		for (String z: list) {
			if (s.equals(z)) {
				return true;
			}
		}
		return false;
	}
	
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
	
	public static ArrayList<Card> shuffle(ArrayList<Card> set) {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (set.size() > 0) {
			int index = (int) (Math.random()*set.size());
			newDeck.add(set.get(index));
			set.remove(index);
		}
		return newDeck;
	}
	
	public static void show(ArrayList<Card> set, String setName) {
		System.out.println(read(set, setName));
	}
}
