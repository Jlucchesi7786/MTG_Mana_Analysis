package base;
import lands.*;
import cards.*;
import java.util.*;

// we did it!
public class Runner {
	static ArrayList<Card> deck = new ArrayList<Card>();
	static ArrayList<Card> hand = new ArrayList<Card>();
	static ArrayList<Card> board = new ArrayList<Card>();
	//static boolean landPlayed = false;
	static Scanner reader = new Scanner(System.in);
	static int goalCmc;
	static String goalCardName;
	static Card goalCard;

	static ArrayList<String> manaPool = new ArrayList<String>();

	static int turn;
	static boolean drewFirstTurn = false;

	static int numTypeLandsInHand = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 15; i++) {
			deck.add(new Swamp());
			deck.add(new Plains());
		}
		for (int i = 0; i < 4; i++) {
			deck.add(new Murder());
			deck.add(new Disenchant());
		}
		/*System.out.println("Enter a card name:");
		goalCardName = reader.nextLine();*/
		goalCardName = "Murder";
		for (Card c: deck) {
			if (c.cardName.equals(goalCardName)) {
				goalCard = c;
				break;
			}
		}
		takeFirstTurn();

		do {
			takeTurn();
		} while (!canCastGoalCard());

		print("It took " + turn + " turns to cast one instance of " + goalCard.cardName + ".");
	}

	public static void takeFirstTurn() {
		sort(deck);
		print(readSet(deck, "deck"));
		shuffle();
		draw(7);
		drewFirstTurn = false;
		turn = 0;
		if (Math.random() > .5) {
			print("Drew on first turn!");
			draw();
			print("\n");
			drewFirstTurn = true;
		}
	}

	public static void takeTurn() {
		turn++;

		if (turn != 1) {
			draw();
		}
		sortHand();
		sortBoard();
		showHand();
		ArrayList<Card> hand2 = new ArrayList<Card>();
		playLand();
		addMana();
		showBoard();
		print("\n");
		for (Card c: hand) {
			hand2.add(c);
		}
		for (Card c: hand2) {
			if (c.equals(goalCard) && canCastGoalCard()) {
				break;
			}
			if (!c.isLand) {
				play(c);
			}
		}
		clearManaPool();
	}

	public static void draw(int num) {
		for (int i = 0; i < num; i++) {
			draw();
		}
	}

	public static void draw() {
		if (deck.size() > 0) {
			hand.add(deck.get(0));
			print("Drew a "+ deck.get(0).cardName + "!");
			deck.remove(0);
		}
	}

	public static void playLand() {
		if (numTypeOfLandsInHand() == 0) {
			return;
		} else if (numLands() == 0 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		} else if (numTypeOfLandsInHand() == 1 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		}

		if (isInHand(goalCard) && !boardHasManaReq(goalCard)) {
			for (int i = 0; i < numLandsInHand(); i++) {
				Land z = (Land) hand.get(i);
				if (z.color.equals(goalCard.cardColor)) {
					play(hand.get(i));
					break;
				}
			}
		} else {
			boolean otherCardsInHand = false;
			String manaNeeded = "";
			for (Card c: hand) {
				if (!c.isLand) {
					otherCardsInHand = true;
					manaNeeded = c.cardColor;
					break;
				}
			}
			if (otherCardsInHand) {
				for (int i = 0; i < numLandsInHand(); i++) {
					Land z = (Land) hand.get(i);
					if (z.color.equals(manaNeeded)) {
						play(hand.get(i));
						break;
					}
				}
			} else {
				if (hand.get(0).isLand) {
					play(hand.get(0));
				}
			}
		}
		/* else if (numTypeOfLandsInHand() == 2) {
			if (isInHand(goalCard) && !boardHasManaReq(goalCard)) {
				for (int i = 0; i < numLandsInHand(); i++) {
					Land z = (Land) hand.get(i);
					if (z.color.equals(goalCard.cardColor)) {
						play(hand.get(i));
						break;
					}
				}
			} else {
				play(hand.get(0));
			}
			//play(hand.get(numLandsOfType(board.get(0), hand)));
		}*/
	}

	public static boolean boardHasManaReq(Card c) {
		int num = 0;
		for (String color: manaPool) {
			if (color.equals(c.cardColor)) {
				num++;
				if (num == c.coloredManaReq) {
					return true;
				}
			}
		}
		/*for (int i = 0; i < numLands(); i++) {
			Land z = (Land) board.get(i);
			if (z.color.equals(c.cardColor)) {
				num++;
				if (num == c.coloredManaReq) {
					return true;
				}
			}
		}*/

		return false;
	}

	public static boolean boardHasReqForGoal() {
		int num = 0;
		for (int i = 0; i < numLands(); i++) {
			Land z = (Land) board.get(i);
			if (z.color.equals(goalCard.cardColor)) {
				num++;
				if (num == goalCard.coloredManaReq) {
					return true;
				}
			}
		}

		return false;
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

	public static int numTypeOfLandsInHand() {
		return numTypeOfLandsInSet(hand);
	}

	public static int numTypeOfLandsOnBoard() {
		return numTypeOfLandsInSet(board);
	}

	public static int numLandsOfType(String name, ArrayList<Card> list) {
		int num = 0;
		for (Card c: list) {
			if (c.cardName.equals(name)) {
				num++;
			}
		}

		return num;
	}

	public static int numLandsOfType(Card c, ArrayList<Card> list) {
		return numLandsOfType(c.cardName, list);
	}

	public static boolean isInHand(Card c) {
		return isInSet(c, hand);
	}

	public static boolean isOnBoard(Card c) {
		return isInSet(c, board);
	}

	public static boolean isInSet(Card c, ArrayList<Card> set) {
		for (Card z: set) {
			if (z.equals(c)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isInList(String s, ArrayList<String> list) {
		for (String z: list) {
			if (s.equals(z)) {
				return true;
			}
		}
		return false;
	}

	public static void play(Card c) {
		if (canCast(c)) {
			board.add(c);
			hand.remove(c);
		}
		sortBoard();
	}

	public static void cast(Card c) {
		String req = c.cardColor;
	}

	public static int numLands() {
		return numLandsInSet(board);
	}

	public static int numLandsInHand() {
		return numLandsInSet(hand);
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

	public static boolean canCastGoalCard() {
		if ((goalCard.cmc == 0 || (numLands() >= goalCard.cmc && boardHasReqForGoal())) && cardInHand(goalCard)) {
			return true;
		}

		return false;
	}

	public static boolean canCast(Card c) {
		if ((c.cmc == 0 || (manaPool.size() >= c.cmc && boardHasManaReq(c))) && cardInHand(c)) {
			return true;
		}

		return false;
	}

	public static boolean cardInHand(Card c) {
		for (Card z: hand) {
			if (z.equals(c)) {
				return true;
			}
		}

		return false;
	}

	public static void shuffle() {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (deck.size() > 0) {
			int index = (int) (Math.random()*deck.size());
			newDeck.add(deck.get(index));
			deck.remove(index);
		}
		deck = newDeck;
	}

	public static void addMana() {
		for (int i = 0; i < numLands(); i++) {
			manaPool.add(((Land) board.get(i)).color);
		}
	}

	public static void clearManaPool() {
		int size = manaPool.size();
		for (int i = 0; i < size; i++) {
			manaPool.remove(0);
		}
	}

	public static void sort(ArrayList<Card> list) {
		for (int i = 0; i < list.size()-1; i++) {
			for (int z = i+1; z < list.size(); z++) {
				if (!list.get(i).isLand && list.get(z).isLand) {
					swap(list, i, z);
				}
			}
		}
		for (int i = 0; i < list.size()-1; i++) {
			for (int z = i+1; z < list.size(); z++) {
				if (list.get(z).cardName.equals(list.get(i).cardName)) {
					swap(list, i+1, z);
					break;
				}
			}
		}
	}

	public static void sortHand() {
		sort(hand);
	}

	public static void sortBoard() {
		sort(board);
	}

	public static void swap(ArrayList<Card> list, int index1, int index2) {
		Card c = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, c);
	}

	public static void showHand() {
		print(readHand());
	}

	public static void showBoard() {
		print(readBoard());
	}

	public static String readBoard() {
		return readSet(board, "board");
	}

	public static String readSet(ArrayList<Card> set, String setName) {
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

	public static String readHand() {
		return readSet(hand, "hand");
	}

	public static void print(String s) {
		System.out.println(s);
	}

	public static void test() {
		System.out.println("yes");
	}
}
