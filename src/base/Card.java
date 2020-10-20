package base;

public abstract class Card {
	protected String cardColor;
	protected String cardName;
	protected String cardType;
	protected boolean isManaSrc;
	protected boolean isLand;
	protected int cmc;
	protected int coloredManaReq;
	protected boolean nameHasSAtEnd;
	
	public Card() {
		
	}
	
	public boolean equals(Card c) {
		if (c.cardName != this.cardName) {
			return false;
		}
		
		return true;
	}
}
