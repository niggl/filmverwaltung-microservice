package edu.kit.iai.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppLogger {

	/**
	 * Severity flag for message output control. 
	 * Print every message on the lowest debug level (very detailed, not for customers)
	 */
	public static final int DEBUG_LOW = 0;
	/**
	 * Severity flag for message output control. 
	 * Print every message on the debug level (detailed, not for customers)
	 */
	public static final int DEBUG = 1;
	/**
	 * Severity flag for message output control. 
	 * Print messages on the debug level as well as messages for the warning and error level
	 */
	public static final int INFO = 2;
	/**
	 * Severity flag for message output control. 
	 * Print messages on the warning level as well as messages for the error level
	 */
	public static final int WARNING = 3;
	/**
	 * Severity flag for message output control. 
	 * Print only messages on the error level, no warnings and no infos
	 */
	public static final int ERROR = 4;
	
	/**
	 * the names for the severity names as plain text.
	 */
	public static final String[] severityNames = new String[]{ "DEBUG_LOW", "DEBUG", "INFO", "WARNING", "ERROR" };

	private static int severity = DEBUG;
	private static boolean showClassInfos = severity < INFO;  // true
	
	private static boolean log2FileMode = false;
    private static String logFileName = "CMLogger.log";
    private static BufferedWriter writeLogBuffer = null;

	
	private AppLogger(){};

	/**
	 * Get the current severity flag.
	 * @return the severity flag, one of {@link #ERROR}, {@link #WARNING}, {@link #INFO}, {@link #DEBUG}, {@link #DEBUG_LOW}
	 */
	public static int getSeverity() {
		return severity;
	}

	/**
	 * set the severity for the output of messages. Possible values are (sorted by severity) 
	 * DEBUG_LOW, DEBUG, INFO, WARNING, ERROR. All messages more 'important' than the given severity is printed.
	 * (using DEBIG_LOW, ALL messages are printed, using ERROR, only the errors are printed.
	 * @param severity the severity of the messages that should be printed.
	 */
	public static void setSeverity( int severity ) {
		setSeverity( severity, true );
	}

	/**
	 * set the severity for the output of messages. Possible values are (sorted by severity) 
	 * DEBUG_LOW, DEBUG, INFO, WARNING, ERROR. All messages more 'important' than the given severity is printed.
	 * (using DEBIG_LOW, ALL messages are printed, using ERROR, only the errors are printed.
	 * @param severity the severity of the messages that should be printed.
	 * @param showInfo if true, the change to another severity is printed, too.
	 */
	public static void setSeverity( int severity, boolean showInfo  ) {
		if( severity < DEBUG_LOW   ||  severity > ERROR ){
//			printLog_old( "CMLogger::", " the given severity is not valid! Severity set to " +  severityNames[ERROR], "INFO");
			printLog( "CMLogger:: the given severity is not valid! Severity set to " +  severityNames[ERROR], WARNING );
			AppLogger.severity = ERROR;
		}
		AppLogger.severity = severity;
		if( showInfo ) printLog( "severity set to " +  severityNames[severity], INFO );
		showClassInfos = severity < INFO;
	}
	
	public static void showClassInfos( boolean show ){
		showClassInfos = show;
	}
	/**
	 * print an information message
	 * The information is only printed, if the severity is set to {@link #DEBUG} or {@link #INFO}   
	 * If the severity is set to {@link #INFO}, only the warnings, errors  and infos are printed.
	 * @param o either the instance of a class ("this") or an arbitrary  String
	 * @param msg the message
	 */
	public static void info( Object o, String msg ) {
		if( severity <= INFO ){
//			printLog( o, msg, "INFO" );
			printLog_old( o, msg, INFO );
		}
	}

	/**
	 * print a debug message. 
	 * The debug message is only printed, if the severity is set to {@link #DEBUG} 
	 * @param o either the instance of a class ("this") or an arbitrary  String
	 * @param msg the message
	 */
	public static void debug( Object o, String msg ) {
		if( severity <= DEBUG ){
//			printLog( o, msg, "DEBUG" );
			printLog_old( o, msg, DEBUG );
		}
	}

	/**
	 * print a lowest level debug message. 
	 * The debug message is only printed, if the severity is set to {@link #DEBUG_LOW} 
	 * @param o either the instance of a class ("this") or an arbitrary  String
	 * @param msg the message
	 */
	public static void debugLow( Object o, String msg ) {
		if( severity == DEBUG_LOW ){
//			printLog( o, msg, "DEBUG_LOW" );
			printLog_old( o, msg, DEBUG_LOW );
		}
	}
	
	/**
	 * print a warning message. 
	 * The warning message is only printed, if the severity is set to {@link #DEBUG}, {@link #INFO} or {@link #WARNING}
	 * If the severity is set to {@link #WARNING}, only the warnings and errors are printed.
	 * @param o either the instance of a class ("this") or an arbitrary  String
	 * @param msg the message
	 */
	public static void warning( Object o, String msg ) {
		if( severity <= WARNING ){
			printLog_old( o, msg, WARNING );
		}
	}

	/**
	 * print an error message. 
	 * The error message is always printed, If the severity is set to {@link #ERROR}, only the errors are printed. 
	 * @param o either the instance of a class ("this") or an arbitrary  String
	 * @param msg the message
	 */
	public static void error( Object o, String msg ) {
		// immer
//		printLog( o, msg, "ERROR" );
		printLog_old( o, msg, ERROR );
}

	private static void printLog_old( Object o, String msg, int severity ) {
		if( o != null  &&  o instanceof String ){
			System.out.println( "--------------------PLEASE REPLACE the CMLogger call with the new one! --------------------------------------------------" );
		}
		if( o == null ) o = "--";

        if( showClassInfos ){
        	printLog( msg, severity );
        }
        else{
			if( o instanceof String ){
				System.out.println( severityNames[ severity ] + " => " + o + ":: " + String.valueOf( msg ) );
			}
			else{
				System.out.println( severityNames[ severity ] + " => " + o.getClass().getName() + ":: " + String.valueOf( msg ) );
			}
        }
	}

    private static void printLog( String msg, int severity ) {

        if( showClassInfos ){
        	final String THIS_CLASSNAME = AppLogger.class.getName();
        	StackTraceElement[] ste = Thread.currentThread().getStackTrace() ;
        	int i = 2; // kann mit 2 beginnen, da StackTrace grundsaetzlich mit Thread vor CMLogger beginnt und damit kein ArrayOverFlow spaeter...
        	for( ; i < ste.length ; ++i ){ // ++i: damit i nach verlassen der Schleife nicht veraendert wird
        		if( ! ste[i].getClassName().startsWith( THIS_CLASSNAME )) break;
        	}
        	String resMsg = severityNames[ severity ] + " => (" + ste[ i ].getClassName() + ".java:"
        					+ ste[ i ].getLineNumber() + ")::" + ste[ i ].getMethodName() + ": " + String.valueOf( msg );
			if( ste[ i ] != null ){
				System.out.println( resMsg );
				if( log2FileMode ) write2File( resMsg );
			}
		}
        else{
        	System.out.println( severityNames[ severity ] + " => " + msg );
        	if( log2FileMode ) write2File( severityNames[ severity ] + " => " + msg );
        }
	}

	/**
	 * print an error message. 
	 * The error message is always printed, If the severity is set to {@link #ERROR}, only the errors are printed. 
	 * @param msg the message
	 */
	public static void error( String msg ) {
		// immer
		printLog( msg, ERROR );
	}

	/**
	 * print an error message for a given Throwable instance. 
	 * The error message is always printed, If the severity is set to {@link #ERROR}, only the errors are printed. 
	 * @param th the throwable containing the message
	 */
	public static void error( Throwable th ) {
		String msg = ( th == null ? "exception is null!!" : th.getMessage() );
		printLog( msg, ERROR );
	}
	
	/**
	 * print an information message
	 * The information is only printed, if the severity is set to {@link #DEBUG} or {@link #INFO}   
	 * If the severity is set to {@link #INFO}, only the warnings, errors  and infos are printed.
	 * @param msg the message
	 */
	public static void info( String msg ) {
		if( severity <= INFO ){
			printLog( msg, INFO );
		}
	}

	/**
	 * print a debug message. 
	 * The debug message is only printed, if the severity is set to {@link #DEBUG} 
	 * @param msg the message
	 */
	public static void debug( String msg ) {
		if( severity <= DEBUG ){
			printLog( msg, DEBUG );
		}
	}

	/**
	 * print a lowest level debug message. 
	 * The debug message is only printed, if the severity is set to {@link #DEBUG_LOW} 
	 * @param msg the message
	 */
	public static void debugLow( String msg ) {
		if( severity == DEBUG_LOW ){
//			printLog( o, msg, "DEBUG_LOW" );
			printLog( msg, DEBUG_LOW );
		}
	}
	
	/**
	 * print a warning message. 
	 * The warning message is only printed, if the severity is set to {@link #DEBUG}, {@link #INFO} or {@link #WARNING}
	 * If the severity is set to {@link #WARNING}, only the warnings and errors are printed.
	 * @param msg the message
	 */
	public static void warning( String msg ) {
		if( severity <= WARNING ){
			printLog( msg, WARNING );
		}
	}
	
	/**
	 * Get the information whether the severity is set to {@link #DEBUG} or {@link #DEBUG_LOW}.
	 * @return true, if the severity is in debug mode
	 */
	public static boolean isDebugMode(){
		return severity < INFO;
	}
	
	/**
	 * Get the information whether the severity is set to {@link #DEBUG_LOW}.
	 * @return true, if the severity is in low debug mode
	 */
	public static boolean isLowDebugMode(){
		return severity < DEBUG;
	}

	/**
	 * Get the information whether the severity is set to {@link #DEBUG_LOW}.
	 * @return true, if the severity is in low debug mode
	 */
	public static boolean isLog2FileMode(){
		return log2FileMode;
	}
	
	/**
	 * indicate that the loggings should be written to a file 
	 * @param fileName the name of the log file (may include the whole path). If fileName is <code>null</code> or fileName is empty,
	 * no logging is initialized.
	 * @param append if true, the log data will be appended to the log file
	 * @return true, if the file could be opened, if false, no logging is initialized (no file name given or an error)
	 */
	public static boolean log2File( String fileName, boolean append ){
		if( fileName == null  ||  fileName.length() == 0 ){
			return false;
		}
		
		logFileName = fileName;
		File logFileTmp = new File( logFileName );
		try{
			if( ! logFileTmp.exists() ){
				logFileTmp.getParentFile().mkdirs();
				logFileTmp.createNewFile();
			}
			writeLogBuffer = new BufferedWriter( new FileWriter( logFileName, append ) );  // append
			log2FileMode = true;
		}
		catch( IOException e ){
			
			e.printStackTrace();
		}
		return log2FileMode;
	}
	
	private static void write2File( String line ){
		try{
			writeLogBuffer.write( line );
			writeLogBuffer.newLine();
			writeLogBuffer.flush();
		}
		catch( IOException e ){
			System.err.println("Error writing to logfile: " + logFileName + ": " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	public static void printStackTrace( String pattern ){
    	final String THIS_CLASSNAME = AppLogger.class.getName();
    	StackTraceElement[] ste = Thread.currentThread().getStackTrace() ;
    	int i = 2; // kann mit 2 beginnen, da StackTrace grundsaetzlich mit Thread vor CMLogger beginnt und damit kein ArrayOverFlow spaeter...
    	for( ; i < ste.length ; ++i ){ // ++i: damit i nach verlassen der Schleife nicht veraendert wird
    		if( ! ste[i].getClassName().startsWith( THIS_CLASSNAME )) break;
    	}
    	String resMsg = severityNames[ severity ] + " => (" + ste[ i ].getClassName() + ".java:"
    					+ ste[ i ].getLineNumber() + ")::" + ste[ i ].getMethodName() + ": '" + pattern +"' STACK: ";
    	for( ; i < ste.length ; ++i ){ // ++i: damit i nach verlassen der Schleife nicht veraendert wird
    		if( ste[ i ] != null  &&   ste[i].getClassName().indexOf( pattern )  >= 0 ){
    			resMsg = severityNames[ severity ] + " => (" + ste[ i ].getClassName() + ".java:"
    					+ ste[ i ].getLineNumber() + ")::" + ste[ i ].getMethodName();
    			System.out.println( resMsg );
    			if( log2FileMode ) write2File( resMsg );
    		}
		}
    	System.out.println( "==>> STACK printing done ..." );
    	if( log2FileMode ) write2File( "==>> STACK printing done ..." );

	}
	
}
