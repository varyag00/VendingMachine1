package ca.ucalgary.seng301.myvendingmachine;

import java.util.List;


//This command takes a sequence of comma-separated integers each representing a valid coin kind; 
//there must be at least one valid coin kind. The integer represents the value of the coin kind; 
//each coin kind must have a unique value. Each value must be a positive integer. 
//The final, semicolon-separated integer represents the number of selection buttons; it must be a positive integer.
public class VendingMachine {
	
	//stores each coin with its corresponding count
	private Coin[] PiggyBank;
	private Button[] ButtonBank;
	
	public VendingMachine(List<Integer> coinKinds, int selectionButtonCount)
	{
		PiggyBank = new Coin[coinKinds.size()];
		for (int i = 0; i < coinKinds.size(); i++) {
			PiggyBank[i] = new Coin(coinKinds.get(i));
		}
		
		
	}
}
