package cards;

import java.util.ArrayList;

public class MulticolorBasicCard extends BasicCard {
	public int numColors;
	public ArrayList<String> colors;
	public ArrayList<Integer> manaReqs;
	
	public MulticolorBasicCard() {
		super();
		multicolor = true;
		colors = new ArrayList<String>();
		manaReqs = new ArrayList<Integer>();
		
	}
}
