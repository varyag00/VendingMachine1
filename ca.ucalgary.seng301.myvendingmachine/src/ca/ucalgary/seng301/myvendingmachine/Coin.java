package ca.ucalgary.seng301.myvendingmachine;

//stores a coin's (value,count) data, essentially a tuple where the second element is mutable
public class Coin{
	public final int value;
	public int count;
	
	public Coin(int value){
		this.value = value;
		count = 0; 			//TODO: coin may need "positive integer value"
	}
}
