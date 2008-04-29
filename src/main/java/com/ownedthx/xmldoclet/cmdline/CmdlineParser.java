package com.ownedthx.xmldoclet.cmdline;

import org.apache.commons.cli.*;

/** Command-line Utility
 *  Provides simple utilities to parse command line arguments
 *  and to offer up the values with simple getters
 */
public class CmdlineParser
{
    private static final String classPathArg = "cp";
    private static final String sourcePathArg = "sp";
    private static final String subPackagesArg = "sub";
    private static final String sourceFilesArg = "sf";
    private static final String packagesArg = "packages";
    private static final String logPropertiesPath = "log";
    private static final String outArg = "out";
    private static final String helpArg = "help";

    private static CommandLine commandLine;
    private static Options options;

    /**
     * Used to parse an array of command line arguments.
     * Once used, one can then use the getters in this utility class
     * @param args The command line arguments.
     * @throws ParseException
     */
    public static void parse(String[] args) throws ParseException
    {
        CommandLineParser parser = new GnuParser();

        options = CmdlineParser.initialize();

        commandLine = parser.parse(options, args);
    }

    public static void printHelp()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "xml-doclet", options );
    }

    /** check if help was requested by user
     *
     * @return true if -help/--help specified
     */
    public static boolean helpRequested()
    {
        return commandLine.hasOption( helpArg );
    }

    /**
     * Retrieves the path to the log properties file
     * @return Path to the log properties file, or null if not specified
     */
    public static String getLogPropertiesPath()
    {
        String logPropertiesPathValue = null;

        if(commandLine.hasOption( classPathArg ))
        {
            logPropertiesPathValue = commandLine.getOptionValue( logPropertiesPath );
        }

        return logPropertiesPathValue;
    }

    /**
     * Retrieves the classpath supplied
     * @return Returns the specified classpath, or null if not specified
     */
    public static String getClassPath()
    {       
        String classPathValue = null;

        if(commandLine.hasOption( classPathArg ))
        {
            classPathValue = commandLine.getOptionValue( classPathArg );
        }

        return classPathValue;
    }

    /**
     * Retrieves the path(s) to the source
     * @return Returns the specified sourcepath, or null if not specified
     */
    public static String getSourcePath()
    {
        String sourcePathValue = null;

        if(commandLine.hasOption( sourcePathArg ))
        {
            sourcePathValue = commandLine.getOptionValue( sourcePathArg );
        }

        return sourcePathValue;
    }

    /**
     * Retrieves the subpackages to process
     * @return Returns the specified subpackages, or null if not specified
     */
    public static String getSubPackages()
    {
        String subPackageValue = null;

        if(commandLine.hasOption( subPackagesArg ))
        {
            subPackageValue = commandLine.getOptionValue( subPackagesArg );
        }

        return subPackageValue;
    }

    /**
     * Retrieves the sourcefiles specified by the user
     * @return Returns the specified sourcefiles, or null if not specified
     */
    public static String getSourceFiles()
    {
        String sourceFilesValue = null;

        if(commandLine.hasOption( sourceFilesArg ))
        {
            sourceFilesValue = commandLine.getOptionValue( sourceFilesArg );
        }

        return sourceFilesValue;
    }

    /**
     * Retrieves the packages specified by the user
     * @return Returns the specified packages, or null if not specified
     */
    public static String getPackages()
    {
        String packagesValue = null;

        if(commandLine.hasOption( packagesArg ))
        {
            packagesValue = commandLine.getOptionValue( packagesArg );
        }

        return packagesValue;
    }

    /**
     * Retrieves the output specified by the user
     * @return Returns the specified output location, or null if not specified
     */
    public static String getOut()
    {
        String outValue = null;

        if(commandLine.hasOption( outArg ))
        {
            outValue = commandLine.getOptionValue( outArg );
        }

        return outValue;
    }

    /**
     * Defines the arguments allowed by the xmldoclet program
     * @return Returns the built-out options
     */
    private static Options initialize()
    {
        Option classPath = OptionBuilder.withArgName( "classpath" )
                            .hasArg()
                            .withDescription("The classpath containing both doclet and source-referenced packages.")
                            .create(classPathArg);

        Option sourcePath = OptionBuilder.withArgName("sourcepathlist")
                            .hasArg()
                            .withDescription("A list of paths containing source packages.")
                            .create(sourcePathArg);

        Option subPackages = OptionBuilder.withArgName("subpackagelist")
                            .hasArg()
                            .withDescription("A system-dependent seperated list of sub-packages (;|:).")
                            .create(subPackagesArg);

        Option sourceFiles = OptionBuilder.withArgName( "sourcefilelist" )
                            .hasArg()
                            .withDescription("A system-dependent seperated list of sourcefiles (;|:).")
                            .create(sourceFilesArg);

        Option packages = OptionBuilder.withArgName("packageslist")
                            .hasArg()
                            .withDescription("A system-dependent seperated list of packages (;|:).")
                            .create(packagesArg);

        Option out = OptionBuilder.withArgName("outfile")
                            .hasArg()
                            .withDescription("The output directory of javadoc.xml. Default='.'")
                            .create(outArg);

        Option log = OptionBuilder.withArgName("logpropertiespath")
                                    .hasArg()
                                    .withDescription("The log4j properties location. Log to console when not set.")
                                    .create(logPropertiesPath);

        Option help = OptionBuilder.hasArg( false )
                                   .withDescription("Specify for help.")
                                   .create (helpArg);




        Options options = new Options();

        options.addOption( classPath );
        options.addOption( sourcePath );
        options.addOption( subPackages );
        options.addOption( sourceFiles );
        options.addOption( packages );
        options.addOption( out );
        options.addOption( log );
        options.addOption( help );

        return options;
    }
}