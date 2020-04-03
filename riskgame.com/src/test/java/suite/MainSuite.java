package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import project.riskgame.com.controller.ControllerTestSuite;
import project.riskgame.com.controller.builder.BuilderTestSuite;
import project.riskgame.com.controller.phase.ControllerPhaseTestSuite;
import project.riskgame.com.controller.strategy.StrategySuite;
import project.riskgame.com.mapeditor.MapEditorTestSuite;

/**
 * Test suite which will test all the packages.
 * 
 * @author Shubham
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({MapEditorTestSuite.class,ControllerTestSuite.class, ControllerPhaseTestSuite.class,StrategySuite.class,BuilderTestSuite.class,})
public class MainSuite {

}
