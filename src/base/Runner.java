package base;
import lands.*;
import cards.*;
import java.util.*;

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
		

		while (true) {
			deck.add(new Plains());
			Sets.show(deck, "deck");
			Sets.shuffle(deck);
		}
		/*do {
			takeTurn();

		} while (!canCastGoalCard());
		*/
		//print("It took " + turn + " turns to cast one instance of " + goalCard.cardName + ".");
	}

	public static void takeFirstTurn() {
		Sets.sort(deck);
		Sets.show(deck, "deck");
		Sets.shuffle(deck);
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
		Sets.sort(hand);
		Sets.sort(board);
		Sets.show(hand, "hand");
		ArrayList<Card> hand2 = new ArrayList<Card>();
		playLand();
		addMana();
		Sets.show(board, "board");
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
		if (Sets.numTypeOfLandsInSet(hand) == 0) {
			return;
		} else if (Sets.numLandsInSet(board) == 0 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		} else if (Sets.numLandsInSet(hand) == 1 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		}

		if (Sets.cardIsInSet(goalCard, hand) && !boardHasManaReq(goalCard)) {
			for (int i = 0; i < Sets.numLandsInSet(hand); i++) {
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
				for (int i = 0; i < Sets.numLandsInSet(hand); i++) {
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
		for (int i = 0; i < Sets.numLandsInSet(board); i++) {
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

	public static void play(Card c) {
		if (canCast(c)) {
			print("Played a " + c.cardName + "!");
			cast(c);
			hand.remove(c);
			board.add(c);
		}
		Sets.sort(board);
	}

	public static void cast(Card c) {
		String req = c.cardColor;
		
		for (int i = 0; i < c.coloredManaReq; i++) {
			for (String color: manaPool) {
				if (req.equals(color)) {
					manaPool.remove(color);
					break;
				}
			}
		}
		
		for (int i = 0; i < c.cmc - c.coloredManaReq; i++) {
			for (String color: manaPool) {
				if (!req.equals(color)) {
					manaPool.remove(color);
					break;
				}
			}
		}
	}

	public static boolean canCastGoalCard() {
		if ((goalCard.cmc == 0 || (Sets.numLandsInSet(board) >= goalCard.cmc && boardHasReqForGoal())) 
				&& Sets.cardIsInSet(goalCard, hand)) {
			return true;
		}

		return false;
	}

	public static boolean canCast(Card c) {
		if ((c.cmc == 0 || (manaPool.size() >= c.cmc && boardHasManaReq(c))) && Sets.cardIsInSet(c, hand)) {
			return true;
		}

		return false;
	}

	public static void addMana() {
		for (int i = 0; i < Sets.numLandsInSet(board); i++) {
			manaPool.add(((Land) board.get(i)).color);
		}
	}

	public static void clearManaPool() {
		int size = manaPool.size();
		for (int i = 0; i < size; i++) {
			manaPool.remove(0);
		}
	}

	public static void print(String s) {
		System.out.println(s);
	}

	public static void test() {
		System.out.println("yes");
	}
}
