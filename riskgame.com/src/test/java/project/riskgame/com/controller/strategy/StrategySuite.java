package project.riskgame.com.controller.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for Strategy package
 * @author Dhaval
 * @version 1.3
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AggressiveStrategyTest.class, BenevolentStrategyTest.class, CheaterStrategyTest.class })
public class StrategySuite {

}
