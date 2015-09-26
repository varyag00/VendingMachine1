package ca.ucalgary.seng301.myvendingmachine;

//stores a coin's (value,count) data, essentially a tuple where the second element is mutable
public class Coin{
	private final int value;
	private int count;
	
	public Coin(int value){
		this.value = value;
		count = 0; 			
	}
	
	//accessors and mutators
	public int getValue(){
		return value;
	}
	
	public int getCount(){
		return count;
	}
	public void setCount(int count){
		this.count = count;
	}
	
}
