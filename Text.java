package MatchingWords1;

public class Text {
	/**
	 * This is class Text.
	 * This class has two private variables; String string, Integer weight.
	 * Also it has two compareTo methods.
	 * compareTo1 method is compares string of two element.
	 * compareTo2 method is compares ewight of two element.
	 */
	private String string;
	private int weight;
	
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int compareTo1(Text o) {
		return new String(this.string).compareTo(o.string);
	}
	public int compareTo2(Text o){
		return new Integer(this.weight).compareTo(o.weight);
	}
	
}