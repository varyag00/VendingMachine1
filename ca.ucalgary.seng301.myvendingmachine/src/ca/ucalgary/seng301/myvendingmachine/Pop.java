package ca.ucalgary.seng301.myvendingmachine;

//stores pop data
public class Pop {
	private String name;
	private int count;
	private int cost;
	
	public Pop(String name, int cost){
		this.name = name;
		this.cost = cost;
	}

	//accessors and mutators
	public void setCount(int count){
		this.count = count;
	}
	public int getCount(){
		return count;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public void setCost(int price){
		this.cost = price;
	}
	public int getCost(){
		return cost;
	}
}
