/* Name: 	J. Daniel Gonzalez
 * UCID: 	10058656
 * Class: 	SENG301
 * Ass:		1
 */

package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;
import java.util.List;


//This command takes a sequence of comma-separated integers each representing a valid coin kind; 
//there must be at least one valid coin kind. The integer represents the value of the coin kind; 
//each coin kind must have a unique value. Each value must be a positive integer. 
//The final, semicolon-separated integer represents the number of selection buttons; it must be a positive integer.
public class VendingMachine {
	private int total;
	//delivery shoot keeps track of pops
	private DeliveryChute dc;
	//used to keep track of revenue
	private int payment;
	//stores each coin with its corresponding count
	private Coin[] PiggyBank;
	//stores each button
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

		dc = new DeliveryChute();

		payment = 0;
		total = 0;
	}

	//configures the names and costs of pops aon the vending machine 
	public void configurePops(List<String> popNames, List<Integer> popCosts){
		//error checking
		
		//list lengths must be identical
		if (popNames.size() != popCosts.size())
			throw new IllegalArgumentException("Every pop must have a corresponding valid cost");
		//number of names and number of costs must be equal to the number of buttons
		if (popNames.size() != ButtonBank.length)
			throw new IllegalArgumentException("Number of pop names and number of pop costs must be equal to the number of buttons");
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
	
	//loads coins + coin counts and pops + pop counts to the vending machine
	public void load(List<Integer> coinCounts, List<Integer> popCounts){
		//error checking
		
		//coin counts and pop counts must be non-negative
		for (int count : coinCounts){
			if (count < 0)
				throw new IllegalArgumentException("Coin loading cannot be negative");
		}
		for (int count : popCounts){
			if (count < 0)
				throw new IllegalArgumentException("Pop loading cannot be negative");
		}
		
		//loading coin counts and pop counts
		for (int i = 0; i < coinCounts.size(); i++){
			PiggyBank[i].setCount(coinCounts.get(i));
		}
		for (int i = 0; i < popCounts.size(); i++){
			ButtonBank[i].getPop().setCount(popCounts.get(i));
		}
	}
	
	//This command causes the total value of remaining unused coins, total value of payment coins, 
	//and individual names of unsold pops to be unloaded from the interior of the machine (for checking).
	public ArrayList<Object> unload(){
		
		ArrayList<Object> returnList = new ArrayList<Object>();
		
		//accumulating total value of remaining unused coins and adding it to list
		long acc = 0;
		for (Coin coin : PiggyBank){
			acc += (coin.getValue() * coin.getCount());
		}
		returnList.add(acc);
		
		//adding total value of payment coins to list
		returnList.add(payment - acc);
	
		//adding names of unsold pops
		for (Button button : ButtonBank){
			if (button.getPop().getCount() > 0){
				returnList.add(button.getPop().getName());
			}
		}
		return returnList;
	}
	
	//extracts and returns the contents of the vending machine's delivery chute
	public ArrayList<Object> extract(){
		
		ArrayList<Object> returnList = new ArrayList<Object>();
		
		//adding change to return
		if (!dc.returnChange().isEmpty()){
			for (Coin coin : dc.returnChange()){
				returnList.add(coin.getCount());
			}
		}
		//adding pop to return
		if(!dc.returnPop().isEmpty()){
			for (Pop pop : dc.returnPop()){
				returnList.add(pop.getName());
			}
		}
		//clears delivery chute
		dc.Extract();
		
		return returnList;
	}

	//An error will occur if the integer is not positive. The coin will immediately be deposited in the delivery chute 
	//if its value does not correspond to a coin kind supported by the current vending machine.
	public void insert(int value){
		
		//error checking
		if (value < 1)
			throw new IllegalArgumentException("Coin value must be positive");
		
		//checking if value is a valid coin type
		Boolean valid = false;
		for (Coin coin : PiggyBank){
			//if value is valid, add it to the total and return
			if (value == coin.getValue()){
				total += value;
				return;
			}
		}
		
		//return the coin via chute if not valid
		total = 0;
		ArrayList<Coin> change = new ArrayList<Coin>();
		change.add(new Coin(value));
	
		dc.addChange(change);
	}
	
	//touch the butt
	public void press(int butt){
		
		//error checking
		if (butt < 0)
			throw new IllegalArgumentException("Index of button pressed cannot be negative");
		if (butt > ButtonBank.length - 1)
			throw new IllegalArgumentException("Index of button pressed is out of bounds");
		
		//valid button pressed will result in pop and change being dispensed (if total >= pop cost)
		if (total >= ButtonBank[butt].getPop().getCost()){
			//if there is pop
			if (ButtonBank[butt].getPop().buyPop())
				dc.addPop(ButtonBank[butt].getPop());
			
			//return change if applicable
			if (total > ButtonBank[butt].getPop().getCost()){
				ArrayList<Coin> change = new ArrayList<Coin>();
				change.add(new Coin(total - ButtonBank[butt].getPop().getCost()));
				dc.addChange(change);
				
				//for updating payment, total paid = total inserted - change given
				total -= (total - ButtonBank[butt].getPop().getCost());
			}
		}
		
		payment += total;
		total = 0;
	}
}



