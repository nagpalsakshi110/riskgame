package project.riskgame.com.controller.strategy;

import project.riskgame.com.model.Player;

/**
 * Class responsible for human strategy
 * @author Kshitij
 * @version 1.3
 */
public class HumanStrategy implements Strategy{
	/**
	 *method for reinforcement in cheater strategy
	 *@param player: player with cheater strategy 
	 */
	@Override
	public void reinforce(Player player) {
		
	}
	
	/**
	 * attack method to be executed according to strategy object
	 * @param player object of the player
	 */
	@Override
	public void attack(Player player) {
		
	}
	
	/**
	 * fortify method to be executed according to strategy object
	 * @param player object of the player
	 */
	@Override
	public void fortify(Player player) {
		
	}
	
	
	
	
	public HumanStrategy() {
	}

	/**
	 *returns the name of the strategy
	 *@return strategy name
	 */
	@Override
	public String getStrategyName() {
		return "human";
	}

}
