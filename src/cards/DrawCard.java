package cards;

/**
 * A DrawCard is a card that, well, draws you more cards. Currently in its basic state of being only spells and not
 * creatures, it will eventually gain the ability to stay on the board.
 * 
 * @author JL
 * @see BasicCard
 */
public class DrawCard extends BasicCard {
	public int drawAmount;
	
	public DrawCard(String name, String type, int cmc, String color, int coloredManaReq) {
		super(name, type, cmc, color, coloredManaReq);
		isDrawCard = true;
	}
}
