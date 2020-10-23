package cards;

public class PainfulLesson extends DrawCard {
	
	public PainfulLesson() {
		super();
		cardName = "Painful Lesson";
		cardColor = "black";
		cardType = "Sorcery";
		cmc = 3;
		coloredManaReq = 1;
		
		drawAmount = 2;
	}
}
