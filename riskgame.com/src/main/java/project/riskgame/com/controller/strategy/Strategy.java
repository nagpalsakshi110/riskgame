package project.riskgame.com.controller.strategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import project.riskgame.com.model.Player;

/**
 * Class responsible for executing the strategy
 * @author s_sawana
 * @version 1.3
 */
@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "type")
	@JsonSubTypes({
	    @Type(value = AggressiveStrategy.class, name = "mvdimpl") ,
	    @Type(value = BenevolentStrategy.class, name = "mvdimpl"),
	    @Type(value = CheaterStrategy.class, name = "mvdimpl"),
	    @Type(value = HumanStrategy.class, name = "mvdimpl"),
	    @Type(value = RandomPlayerStrategy.class, name = "mvdimpl"),})
public interface Strategy {
	
	/**
	 * reinforce method to be executed according to strategy object
	 * @param player: current player
	 */
	public void reinforce(Player player);
	
	/**
	 * attack method to be executed according to strategy object
	 * @param player object of the player
	 */
	public void attack(Player player);
	
	/**
	 * fortify method to be executed according to strategy object
	 * @param player object of the player
	 */
	public void fortify(Player player);
	
	/**
	 * this method gets the strategy name
	 * @return strategy name
	 */
	@JsonIgnore
	public String getStrategyName();

}
