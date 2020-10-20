package cards;

import base.Card;

public class Disenchant extends Card {
	// here we go
	public Disenchant() {
		cardName = "Disenchant";
		cardColor = "white";
		cardType = "Instant";
		cmc = 2;
		coloredManaReq = 1;
		isManaSrc = false;
		isLand = false;
		nameHasSAtEnd = false;
	}
}
