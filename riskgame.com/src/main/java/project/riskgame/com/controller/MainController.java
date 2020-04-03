package project.riskgame.com.controller;

import java.awt.EventQueue;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import project.riskgame.com.view.MainWindow;

/**
 * contains main class  which launches the application
 * 
 * @author Devdutt
 * @version 1.0
 */
public class MainController {

	
	/**
	 * Logger object
	 */
	public static Logger logger = Logger.getLogger("Log");  
    
	/**
	 * filehandler object
	 */
	private static FileHandler fileHandler; 
    
	/**
	 * classpath variable for storing path
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/logs/";

	/**
	 * This is main method to launch application
	 * @param args arguments of main method.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					fileHandler = new FileHandler(CLASSPATH + "log.txt");
					fileHandler.setFormatter(new Formatter() {
						
						@Override
						public String format(LogRecord record) {
							
							StringBuilder sb = new StringBuilder();
					        sb.append(record.getLevel()).append(':');
					        sb.append(record.getMessage()).append('\n');
					        return sb.toString();
						}
						
					});  
					logger.addHandler(fileHandler); 
					
					MainWindow window = new MainWindow();
					window.getFrame().setVisible(true);
					
					logger.info("MainWindow started"); 
					
				} catch (Exception e) {
					System.out.println("Error occured : "+e.getClass().getName()+" - "+e.getMessage());
				}
			}
			
		});
	}

}
