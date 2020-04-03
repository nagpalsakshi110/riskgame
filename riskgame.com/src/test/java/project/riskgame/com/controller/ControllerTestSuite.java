package project.riskgame.com.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Test suite for Controller package
 * @author Dhaval
 * @version 1.0
 *
 */
@RunWith(Suite.class)
@SuiteClasses({PlayerControllerTest.class,GamePlayValidatorTest.class,GamePlayControllerTest.class,TournamentTest.class})
public class ControllerTestSuite {

}
