package cards;

import java.util.ArrayList;

/**
 * A <code>MulticolorBasicCard</code> is a basic card that requires multiple colors of mana to cast it. Because it was
 * useless for the objects higher up the chain to hold them, this object holds the <code>numColors</code>, 
 * <code>colors</code>, and <code>manaReqs</code> variables which are multicolor card specific.
 * @author JL
 * @see BasicCard
 */
public class MulticolorBasicCard extends BasicCard {
	public int numColors;
	public ArrayList<String> colors;
	public ArrayList<Integer> manaReqs;
	
	public MulticolorBasicCard(String name, String type, int cmc, String color, int coloredManaReq) {
		super(name, type, cmc, color, coloredManaReq);
		multicolor = true;
		colors = new ArrayList<String>();
		manaReqs = new ArrayList<Integer>();
	}
}
