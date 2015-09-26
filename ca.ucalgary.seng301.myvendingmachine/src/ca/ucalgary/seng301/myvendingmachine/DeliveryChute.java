package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;

public class DeliveryChute {
	private ArrayList<Pop> popChute;
	private ArrayList<Coin> changeChute;
	private Boolean isEmpty;
	
	public DeliveryChute(){
		popChute = new ArrayList<Pop>();
		changeChute = new ArrayList<Coin>();
		isEmpty = true;
	}
	
	//adds pop to the extract queue
	public void addPop(Pop pop){
		popChute.add(pop);
		
		isEmpty = false;
	}
	
	//adds change to the extract queue
	public void addChange(ArrayList<Coin> change){

		for (Coin coin: change){
			changeChute.add(coin);
			
			if (coin.getValue() != 0)
				isEmpty = false;
		}
	}
	
	public ArrayList<Pop> returnPop(){
		return popChute;
	}
	
	public ArrayList<Coin> returnChange(){
		return changeChute;
	}
	
	//clears delivery chute
	public void Extract(){
		popChute.clear();
		changeChute.clear();
		
		isEmpty = true;
	}
	
	public boolean IsEmpty(){
		if (isEmpty)
			return true;
		
		//check if delivery chute has any pops
		for (Pop pop : popChute){
			if (pop.getCount() > 0){
				isEmpty = false;
				return isEmpty;
			}
		}
		//check if delivery chute has any change
		for (Coin coin : changeChute){
			if (coin.getCount() > 0){
				isEmpty = false;
				return isEmpty;
			}
		}
		
		//if there are no pops and no coins, delivery chute is empty, though this code should never have to run
		isEmpty = true;
		return isEmpty;
	}
}
