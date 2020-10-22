package base;
import java.util.*;

public class Set {
	ArrayList<Card> set;
	String name;
	
	public Set() {
		this("set");
	}
	
	public Set(String name) {
		this(name, new ArrayList<Card>());
	}
	
	public Set(ArrayList<Card> set) {
		this("set", set);
	}
	
	public Set(String name, ArrayList<Card> set) {
		this.set = set;
		this.name = name;
	}
	
	
	public void shuffle() {
		set = Sets.shuffle(set);
	}
	
	public void show() {
		Sets.show(set, name);
	}
	
	public void sort() {
		Sets.sort(set);
	}
	
	public boolean cardIsInSet(Card c) {
		return Sets.cardIsInSet(c, set);
	}
	
	public int numLands() {
		return Sets.numLandsInSet(set);
	}
	
	public int numTypeOfLands() {
		return Sets.numTypeOfLandsInSet(set);
	}
	
	public int numLandsOfType(String cardName) {
		return Sets.numLandsOfType(cardName, set);
	}
	
	
	
	
	public void add(Card c) {
		set.add(c);
	}
	
	public void remove(Card c) {
		set.remove(c);
	}
	
	public void remove(int i) {
		set.remove(i);
	}
	
	public Card get(int index) {
		return set.get(index);
	}
	
	public int size() {
		return set.size();
	}
}