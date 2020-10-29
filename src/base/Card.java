package base;

/**
 * The <code>Card</code> object contains all of the things universal across cards, such as name and cmc, but also 
 * contains booleans that tell what type of card it is. All of the different cards have this as the base of their 
 * class pathway to accommodate the card ArrayLists that make the different sets of cards.
 * @author JL
 * @see Set
 */
public abstract class Card {
	protected String cardName;
	protected String cardType;
	protected int cmc;
	protected String cardColor;
	protected int coloredManaReq;

	protected boolean isManaSrc;
	protected boolean isLand;
	protected boolean isDrawCard;
	protected boolean multicolor;
	
	public Card(String name, String type, int cmc, String color, int coloredManaReq) {
		this.cardName = name;
		this.cardType = type;
		this.cmc = cmc;
		this.cardColor = color;
		this.coloredManaReq = coloredManaReq;
	}
	
	//public Card() {}
	
	public Card(String name, String type, int cmc) {
		this(name, type, cmc, "colorless", 0);
	}
	
	/**
	 * Checks if this card is equal to another card by simply checking the name, as that is the one identifying factor
	 * for cards.
	 * @param c The other card to check.
	 * @return <code>true</code> if the other card's name equal this card's name, otherwise <code>false</code>.
	 */
	public boolean equals(Card c) {
		if (c.cardName != this.cardName) {
			return false;
		}
		
		return true;
	}
}
