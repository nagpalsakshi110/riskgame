package project.riskgame.com.controller;

import java.util.List;
import java.util.Random;

/**
 * This class contains methods for random generation of numbers
 * 
 * @author Shubham
 * @author Dhaval
 * @param <T> the class of the objects in the list
 * @version 1.0
 */
public class RandomGenerator<T> {

	/** 
	 * This method generates random number
	 * 
	 * @param range range of the number
	 * @return return random number between 0 to range - 1
	 */
	public static int getRandomNumber(int range) {
		return new Random().nextInt(range);
	}
	
	
	/** 
	 * This method generates random number
	 * 
	 * @param range range of the number
	 * @return return random number between 1 to range if outbound is true
	 */
	public static int getRandomNumber(int range,boolean outbound) {
		return outbound ? (new Random().nextInt(range)+1):new Random().nextInt(range);
	}
	
	
	/**
	 * This method gets the random object from the list of objects
	 * 
	 * @param list list of objects
	 * @return the random object from the list
	 */
	public static <T extends Object> T getRandomObject(List<T> list) {
		int index = getRandomNumber(list.size());
		return list.get(index);
	}
}
