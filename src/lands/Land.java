package lands;
import base.Card;

/**
 * A land card is a permanent that, for the most part, simply stays on/in the board and taps for mana. Each one is a mana
 * source, but the color of mana varies by land.
 * @author JL
 * @see Card
 */
public abstract class Land extends Card {
	public boolean basic;
	public boolean anyColor;
	public String color;
	public String color2;
	
	public Land(String name) {
		super(name, "land", 0);
		isManaSrc = true;
		isLand = true;
	}
}
