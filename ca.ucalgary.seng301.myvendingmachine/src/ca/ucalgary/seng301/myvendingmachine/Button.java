package ca.ucalgary.seng301.myvendingmachine;

//stores button data
public class Button {
	private Pop pop;
	
	public Button(){
		
	}
	
	public Button (String popName, int popCost){
		pop = new Pop(popName, popCost);
	}
	
	public void setPop(Pop pop){
		this.pop = pop;
	}
	public Pop getPop(){
		return pop;
	}
}
