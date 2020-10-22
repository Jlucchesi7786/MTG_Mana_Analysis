package cards;

import base.Card;

public abstract class BasicCard extends Card {
	
	public BasicCard() {
		isManaSrc = false;
		isLand = false;
		multicolor = false;
	}
}
