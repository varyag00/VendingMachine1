/* Name: 	J. Daniel Gonzalez
 * UCID: 	10058656
 * Class: 	SENG301
 * Ass:		1
 */

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
	
	//for synching pop counts across different buttons, might not need this if buttons have their own independent supply of pop
	private Pop[] PopBank; 		
	
	public VendingMachine(List<Integer> coinKinds, int selectionButtonCount)
	{
    	//error checking
    	
    	//must be at least one coin type
    	if (coinKinds.size() == 0){
    		throw new IllegalArgumentException("There must be at least one valid coin type");
    	}
    	//test for duplicates and non-positive coin values
    	for (int i=0; i < coinKinds.size(); i++){
    		if (coinKinds.get(i) < 1)
	    		throw new IllegalArgumentException("Coin values must have a positive value");

    		for (int k = i+1; k < coinKinds.size(); k++){
    			if (coinKinds.get(k) == coinKinds.get(i) && i != k)
    	    		throw new IllegalArgumentException("Coin values must be unique");
    		}
    	}    	
		
		//constructing coins
		PiggyBank = new Coin[coinKinds.size()];
		for (int i = 0; i < coinKinds.size(); i++) {
			PiggyBank[i] = new Coin(coinKinds.get(i));
		}
		
		//constructing buttons
		ButtonBank = new Button[selectionButtonCount];
		for (int i = 0; i< ButtonBank.length; i++){
			ButtonBank[i] = new Button();
		}
	}

	public void configurePops(List<String> popNames, List<Integer> popCosts){
		//error checking
		
		//list lengths must be identical
		if (popNames.size() != popCosts.size())
			throw new IllegalArgumentException("Every pop must have a corresponding valid cost");
		//pop costs cannot be negative
		for (int cost : popCosts){
			if (cost < 0)
				throw new IllegalArgumentException("Pop costs cannot be negative");
		}
		
		//configuring buttons
		for (int i = 0; i < popNames.size(); i++)
		{
			//creating new button with the given pop name and pop cost
			ButtonBank[i] = new Button(popNames.get(i), popCosts.get(i));
		}
	}
}


