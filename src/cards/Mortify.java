package cards;

import java.util.*;

public class Mortify extends MulticolorBasicCard {
	
	public Mortify() {
		super();
		numColors = 2;
		colors = new ArrayList<String>();
		colors.add("black");
		colors.add("white");
		manaReqs = new ArrayList<Integer>();
		manaReqs.add(1);
		manaReqs.add(1);
		
		cardName = "Mortify";
		cardType = "Instant";
		coloredManaReq = 2;
		cmc = 3;
	}
}
