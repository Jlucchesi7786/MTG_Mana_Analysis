package cards;

import base.Card;

/**
 * A <code>BasicCard</code> is the object that sets all of the booleans that matter (such as <code>isLand</code> and
 * <code>multicolor</code> to false. Cards that follow those parameters inherit directly from this class, but even 
 * the other important cards inherit from this class to make everything a lot smoother.
 * @author JL
 * @see Card
 */
public abstract class BasicCard extends Card {
	
	public BasicCard(String name, String type, int cmc, String color, int coloredManaReq) {
		super(name, type, cmc, color, coloredManaReq);
		isManaSrc = false;
		isLand = false;
		multicolor = false;
		isDrawCard = false;
	}
}
