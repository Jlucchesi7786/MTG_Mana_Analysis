package base;

import java.util.*;

public abstract class Card {
	protected String cardName;
	protected String cardType;
	protected int cmc;

	protected boolean isManaSrc;
	protected boolean isLand;
	
	protected boolean multicolor;
	protected String cardColor;
	protected int coloredManaReq;
	protected int numColors;
	protected ArrayList<String> colors;
	protected ArrayList<Integer> manaReqs;
	
	public boolean equals(Card c) {
		if (c.cardName != this.cardName) {
			return false;
		}
		
		return true;
	}
}
