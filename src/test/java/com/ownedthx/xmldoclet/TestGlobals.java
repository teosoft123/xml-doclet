package com.ownedthx.xmldoclet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;

/**
 * A class to throw static stuff in for logic common across all test suites
 */
public class TestGlobals
{
    static Logger log = Logger.getLogger("com.ownedthx.xmldoclet");

    private static boolean initializedLogging = false;

    /**
     * A simple method meant to be called by every test suite
     * in the setUp method.  This method will only run once, though,
     * no matter how many test suites invoke it.
     */
    public static void initializeLogging()
    {
        if(!initializedLogging)
        {
            initializedLogging = true;

            BasicConfigurator.configure();

            log.setLevel( Level.ERROR );
        }
    }
}
