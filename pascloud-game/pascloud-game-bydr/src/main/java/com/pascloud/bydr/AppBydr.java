package com.pascloud.bydr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class AppBydr 
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppBydr.class);
	private static String configPath;
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    public static String getConfigPath() {
		return configPath;
	}
}
