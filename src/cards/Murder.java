package cards;

import base.Card;

public class Murder extends Card {
	
	public Murder() {
		cardName = "Murder";
		cardColor = "black";
		cardType = "Instant";
		cmc = 3;
		coloredManaReq = 2;
		isManaSrc = false;
		isLand = false;
		nameHasSAtEnd = false;
	}
}
