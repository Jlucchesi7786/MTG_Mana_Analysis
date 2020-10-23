package base;

public abstract class Card {
	protected String cardName;
	protected String cardType;
	protected int cmc;

	protected boolean isManaSrc;
	protected boolean isLand;
	protected boolean isDrawCard;
	
	protected boolean multicolor;
	protected String cardColor;
	protected int coloredManaReq;
	
	public boolean equals(Card c) {
		if (c.cardName != this.cardName) {
			return false;
		}
		
		return true;
	}
}
