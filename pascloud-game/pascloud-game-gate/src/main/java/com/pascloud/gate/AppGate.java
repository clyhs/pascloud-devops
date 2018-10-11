package com.pascloud.gate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hello world!
 *
 */
public class AppGate 
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppGate.class);

	private static String configPath;
	
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    

	public static String getConfigPath() {
		return configPath;
	}
}
