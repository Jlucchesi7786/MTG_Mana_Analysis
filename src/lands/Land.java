package lands;
import base.Card;

public abstract class Land extends Card {
	public boolean basic;
	public boolean anyColor;
	public String color;
	public String color2;
	
	public Land() {
		cardColor = "colorless";
		cardType = "land";
		cmc = 0;
		isManaSrc = true;
		isLand = true;
	}
}
