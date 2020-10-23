package base;
import lands.*;
import cards.*;
import java.util.*;

public class Runner {
	static Set deck = new Set("deck");
	static Set hand = new Set("hand");
	static Set board = new Set("board");
	//static boolean landPlayed = false;
	static Scanner reader = new Scanner(System.in);
	static int goalCmc;
	static String goalCardName;
	static Card goalCard;

	static ArrayList<String> manaPool = new ArrayList<String>();

	static int turn;
	static boolean drewFirstTurn = false;

	static final int REPLIM = 100000;
	static int reps;
	static int totalTurns = 0;
	static boolean showStateOfPlay = false;
	
	
	static final boolean INPUTTINGPARAMS = false;
	

	public static void main(String[] args) {
		reset();
		if (INPUTTINGPARAMS) {
			System.out.println("Enter a card name:");
			goalCardName = reader.nextLine();
			do {
				print("Please enter a number of trials:");
				reps = reader.nextInt();
				if (reps <= 0) {
					print("Pick a number greater than 0.");
				} else if (reps >= REPLIM) {
					print("That number exceeds the trial limit. Please pick a number less than or equal to 100,000.");
				}
			} while (reps <= 0 || reps > REPLIM);
			print("Would you like to see updates on the state of the game as the trial goes on? Enter 'yes' if so:");
			String ans = reader.next();
			if (ans.equals("")) {
				ans = reader.nextLine();
			}
			if (reps == 1 || ans.equals("yes") || ans.equals("y")) {
				showStateOfPlay = true;
			}
			print("\n");
		} else {
			goalCardName = "Mortify";
			reps = 1;
			showStateOfPlay = true;
		}
		
		for (int i = 0; i < deck.size(); i++) {
			Card c = deck.get(i);
			if (c.cardName.equals(goalCardName)) {
				goalCard = c;
				break;
			}
		}
		
		
		deck.show();
		
		for (int i = 0; i < reps; i++) {
			reset();
			takeFirstTurn();
			
			do {
				takeTurn();
			} while (!canCastGoalCard() && deck.size() > 0);
			if (showStateOfPlay) {
				print("It took " + turn + " turns to cast one instance of " + goalCard.cardName + ".");
				print("");
				print("");
			}
			totalTurns += turn;
		}
		
		if (showStateOfPlay) {
			reset();
			deck.show();
		}
		print("\nAcross " + reps + " trials, " + goalCard.cardName + " took an average of " + 
				(((double) totalTurns)/(double) reps) + " turns to cast.");
	}

	
	
	public static void takeFirstTurn() {
		deck.shuffle();
		draw(7);
		drewFirstTurn = false;
		turn = 0;
		if (Math.random() > .5) {
			if (showStateOfPlay) {
				print("Drew on first turn!");
			}
			draw();
			if (showStateOfPlay) {
				print("\n");
			}
			drewFirstTurn = true;
		}
	}

	public static void takeTurn() {
		turn++;

		if (turn != 1) {
			draw();
		}
		hand.sort();
		board.sort();
		if (showStateOfPlay) {
			hand.show();
		}
		ArrayList<Card> hand2 = new ArrayList<Card>();
		playLand();
		addMana();
		/*if (boardHasManaReq(new Mortify())) {
			test();
		}*/
		if (showStateOfPlay) {
			print("State of board after playing a land:");
			board.show();
		}
		for (int i = hand.numLands(); i < hand.size(); i++) {
			Card c = hand.get(i);
			hand2.add(c);
		}
		for (int i = 0; i < hand2.size(); i++) {
			Card c = hand2.get(i);
			if (c.equals(goalCard) && canCastGoalCard()) {
				break;
			}
			if (!c.isLand) {
				play(c);
			}
		}
		clearManaPool();
		if (showStateOfPlay) {
			print("State of board at end of turn:");
			board.show();
			print("\n");
		}
	}

	public static void draw(int num) {
		for (int i = 0; i < num; i++) {
			draw();
		}
	}

	public static void draw() {
		if (deck.size() > 0) {
			hand.add(deck.get(0));
			if (showStateOfPlay) {
				print("Drew a "+ deck.get(0).cardName + "!");				
			}
			deck.remove(0);
		}
	}

	public static void playLand() {
		if (hand.numTypeOfLands() == 0) {
			return;
		} else if (board.numLands() == 0 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		} else if (hand.numLands() == 1 && hand.get(0).isLand) {
			play(hand.get(0));
			return;
		}

		if (hand.cardIsInSet(goalCard)) {
			if (!boardHasReqForGoal()) {
				for (int i = 0; i < hand.numLands(); i++) {
					Land z = (Land) hand.get(i);
					if (!goalCard.multicolor) {
						if (z.color.equals(goalCard.cardColor)) {
							play(hand.get(i));
							break;
						}
					} else {
						ArrayList<String> landColorReqs = whatIsBoardMissingToCastGoal();
						
						boolean playThisLand = false;
						for (String color: landColorReqs) {
							if (color.equals(z.color)) {
								playThisLand = true;
								break;
							}
						}
						if (playThisLand) {
							play(hand.get(i));
							break;
						}
					}
				}
			} else {
				if (hand.get(0).isLand) {
					play(hand.get(0));
				}
			}
		} else {
			boolean otherCardsInHand = false;
			String manaNeeded = "";
			for (int i = hand.numLands(); i < hand.size(); i++) {
				Card c = hand.get(i);
				if (!c.isLand) {
					otherCardsInHand = true;
					if (!c.multicolor) {
						manaNeeded = c.cardColor;
					} else {
						ArrayList<String> missingMana = whatIsBoardMissingToCast(c);
						if (missingMana.size() > 0) {
							manaNeeded = missingMana.get((int) (Math.random()*((double) missingMana.size())));
						} else {
							if (hand.get(0).isLand) {
								play(hand.get(0));
							}
						}
					}
					break;
				}
			}
			if (otherCardsInHand) {
				for (int i = 0; i < hand.numLands(); i++) {
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
	
	public static ArrayList<String> whatIsBoardMissingToCastGoal() {
		ArrayList<String> missingColors = new ArrayList<String>();
		for (String color: goalCard.colors) {
			boolean boardDoesNotHave = true;
			for (int i = 0; i < board.numLands(); i++) {
				Land z = (Land) board.get(i);
				if (color.equals(z.color)) {
					boardDoesNotHave = false;
					break;
				}
			}
			if (boardDoesNotHave) {
				missingColors.add(color);
			}
		}
		
		return missingColors;
	}
	
	public static ArrayList<String> whatIsBoardMissingToCast(Card c) {
		ArrayList<String> missingColors = new ArrayList<String>();
		for (String color: c.colors) {
			boolean boardDoesNotHave = true;
			for (int i = 0; i < board.numLands(); i++) {
				Land z = (Land) board.get(i);
				if (color.equals(z.color)) {
					boardDoesNotHave = false;
					break;
				}
			}
			if (boardDoesNotHave) {
				missingColors.add(color);
			}
		}
		
		return missingColors;
	}

	public static boolean boardHasManaReq(Card c) {
		if (!c.multicolor) {
			int num = 0;
			for (String color: manaPool) {
				if (color.equals(c.cardColor)) {
					num++;
					if (num == c.coloredManaReq) {
						return true;
					}
				}
			}
		} else {
			int[] nums = new int[c.manaReqs.size()];
			//nums[0] = 0; nums[1] = 0;
			for (String color: manaPool) {
				for (int i = 0; i < c.colors.size(); i++) {
					String col = c.colors.get(i);
					if (color.equals(col)) {
						nums[i] = nums[i] + 1;
						break;
					}
				}
				boolean hasReq = true;
				for (int i = 0; i < nums.length; i++) {
					if (nums[i] != c.manaReqs.get(i)) {
						hasReq = false;
						break;
					}
				}
				if (hasReq) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean boardHasReqForGoal() {
		//System.out.println(goalCard.multicolor);
		if (!goalCard.multicolor) {
			int num = 0;
			for (int i = 0; i < board.numLands(); i++) {
				Land z = (Land) board.get(i);
				if (z.color.equals(goalCard.cardColor)) {
					num++;
					if (num == goalCard.coloredManaReq) {
						return true;
					}
				}
			}
		} else {
			int[] nums = new int[goalCard.manaReqs.size()];
			for (int z = 0; z < board.numLands(); z++) {
				String color = ((Land) board.get(z)).color;
				for (int i = 0; i < goalCard.colors.size(); i++) {
					String col = goalCard.colors.get(i);
					if (col.equals(color)) {
						nums[i]++;
						break;
					}
				}
				boolean hasReq = true;
				for (int i = 0; i < nums.length; i++) {
					if (!(nums[i] >= goalCard.manaReqs.get(i))) {
						hasReq = false;
						break;
					}
				}
				if (hasReq) {
					return true;
				}
			}
			
		}
		
		return false;
	}

	public static void play(Card c) {
		if (canCast(c)) {
			if (showStateOfPlay) {
				print("Played a " + c.cardName + "!");
			}
			cast(c);
			hand.remove(c);
			board.add(c);
		}
		board.sort();
	}

	public static void cast(Card c) {
		if (!c.multicolor) {
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
		} else {
			ArrayList<String> reqs = c.colors;
			for (int z = 0; z < reqs.size(); z++) {
				String req = reqs.get(z);
				for (int i = 0; i < c.manaReqs.get(z); i++) {
					for (String color: manaPool) {
						if (req.equals(color)) {
							manaPool.remove(color);
							break;
						}
					}
				}
			}
			
			for (int i = 0; i < c.cmc - c.coloredManaReq; i++) {
				for (String color: manaPool) {
					if (reqs.get(0).equals(color)) {
						manaPool.remove(color);
						break;
					}
				}
			}
		}
	}

	public static boolean canCastGoalCard() {
		if ((goalCard.cmc == 0 || (board.numLands() >= goalCard.cmc && boardHasReqForGoal())) 
				&& hand.cardIsInSet(goalCard)) {
			return true;
		}

		return false;
	}

	public static boolean canCast(Card c) {
		if ((c.cmc == 0 || (manaPool.size() >= c.cmc && boardHasManaReq(c))) && hand.cardIsInSet(c)) {
			return true;
		}

		return false;
	}

	public static void addMana() {
		for (int i = 0; i < board.numLands(); i++) {
			manaPool.add(((Land) board.get(i)).color);
		}
	}

	public static void clearManaPool() {
		int size = manaPool.size();
		for (int i = 0; i < size; i++) {
			manaPool.remove(0);
		}
	}
	
	public static void reset() {
		while (deck.size() > 0) {
			if (deck.size() != 0) {
				deck.remove(deck.get(0));
			}
		}
		while (hand.size() > 0) {
			if (hand.size() != 0) {
				hand.remove(hand.get(0));
			}
		}
		while (board.size() > 0) {
			if (board.size() != 0) {
				board.remove(board.get(0));
			}
		}
		for (int i = 0; i < 15; i++) {
			deck.add(new Swamp());
			deck.add(new Plains());
		}
		for (int i = 0; i < 4; i++) {
			deck.add(new Murder());
			deck.add(new Disenchant());
			deck.add(new Mortify());
		}
	}

	public static void print(String s) {
		System.out.println(s);
	}

	public static void test() {
		System.out.println("yes");
	}
}
