package cards;

public class Mortify extends MulticolorBasicCard {
	
	public Mortify() {
		super();
		numColors = 2;
		colors.add("black");
		colors.add("white");
		manaReqs.add(1);
		manaReqs.add(1);
		
		cardName = "Mortify";
		cardType = "Instant";
		coloredManaReq = 2;
		cmc = 3;
	}
}
