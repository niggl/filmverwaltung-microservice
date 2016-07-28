package edu.kit.iai.util;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;



/**
 * a collection of various utility functions.
 *
 * @author diverse
 * @version $Revision: 1.35 $
 *
 */
@SuppressWarnings("unused")
public final class StringUtilities {

	public static final char DEFAULT_DELIMITER = ',';
    /**
     * disallowed.
     *
     * (Stereotype: utility)
     */
    private StringUtilities() {
        assert(false);
    }

	/**
	 * Transforms a given sequence of numbers of the form '1-6,9,...' into an
	 * int array containing all numbers {1,2,3,4,5,6,9,...}<br>
	 * the default delimiter is: "<b>,</b>"
	 * @param numberSequence numberSequence the sequence of numbers as String
	 * @return an int-Array with the separated values
	 * @author M.Klee N.Horchheimer
	 */
	public static int[] transformNumberSequenceToArrayNew( String numberSequence ) {
		if( numberSequence == null || numberSequence.equals( "" ) ){
			throw new IllegalArgumentException( "numberSequence not given (null or empty) " );
		}
		return transformNumberSequenceToArray( numberSequence, DEFAULT_DELIMITER );
	}
	
    public static int[] transformNumberSequenceToArrayOLD( String numberSequence )
    {
        if( numberSequence == null || numberSequence.equals("")){
            return new int[0];
        }
        Vector numbersVector = new Vector();

        StringTokenizer tok = new StringTokenizer( numberSequence, "," );
        StringTokenizer innerTok;
        int numgl = tok.countTokens();
        int first, last;
        //first token outside...
         for (int i=0; i < numgl; i++) {
             innerTok = new StringTokenizer( tok.nextToken(), "-" );
             if( innerTok.countTokens() == 2 ){
                 first = Integer.parseInt( innerTok.nextToken() );
                 last = Integer.parseInt( innerTok.nextToken() );
                 while( first <= last){
                     numbersVector.add( new Integer( first++ ) );
                 }
             }
             else{
                 numbersVector.add( new Integer( Integer.parseInt( innerTok.nextToken() ) ) );
             }
         }

         int num = numbersVector.size();
         int[] numbers = new int[ num ];
         for( int i = 0; i < num; i++){
             numbers[i] = ((Integer) numbersVector.get(i) ).intValue();
         }
         return numbers;


    }//transformNumberSequenceToArray()
    public static long[] transformNumberSequenceToArrayOLDLong( String numberSequence )
    {
    	if( numberSequence == null || numberSequence.equals("")){
    		return new long[0];
    	}
    	Vector numbersVector = new Vector();
    	
    	StringTokenizer tok = new StringTokenizer( numberSequence, "," );
    	StringTokenizer innerTok;
    	long numgl = tok.countTokens();
    	long first, last;
    	//first token outside...
    	for (int i=0; i < numgl; i++) {
    		innerTok = new StringTokenizer( tok.nextToken(), "-" );
    		if( innerTok.countTokens() == 2 ){
    			first = Long.parseLong( innerTok.nextToken() );
    			last = Long.parseLong( innerTok.nextToken() );
    			while( first <= last){
    				numbersVector.add( new Long( first++ ) );
    			}
    		}
    		else{
    			numbersVector.add( new Long( Long.parseLong( innerTok.nextToken() ) ) );
    		}
    	}
    	
    	int num = numbersVector.size();
    	long[] numbers = new long[ num ];
    	for( int i = 0; i < num; i++){
    		numbers[i] = ((Long) numbersVector.get(i) ).longValue();
    	}
    	return numbers;
    	
    	
    }//transformNumberSequenceToArray()

	
	/**
	 * Transforms a given sequence of numbers of the form '1-6,9,...' into an
	 * int array containing all numbers {1,2,3,4,5,6,9,...}
	 * 
	 * @param numberSequence numberSequence the sequence of numbers as String.
	 * @param delimiter the delimiter which separates the values
	 * @return an int-Array with the separated values
	 * @author M.Klee N.Horchheimer
	 */
	public static int[] transformNumberSequenceToArray(String numberSequence, char delimiter) {
		if( numberSequence == null || numberSequence.equals( "" ) ){
			return new int[ 0 ];
		}
		
		String[] commaValues = splitString( numberSequence, delimiter );
		
		int startPos = 0;
//		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		Vector<Integer> numbersList = new Vector<Integer>();
		
		for( int i = startPos ; i < commaValues.length ; i++ ){
			String subString = commaValues[ i ];
			int sepPos = subString.indexOf( '-' );
			if( sepPos >= 0 ){
				int firstNumber = Integer.parseInt( commaValues[ i ].substring( 0, sepPos ) );
				int secondNumber = Integer.parseInt( commaValues[ i ].substring( sepPos + 1 ) );
				while( firstNumber <= secondNumber ){
					numbersList.add( new Integer( firstNumber++ ) );
				}
			}
			else{
				numbersList.add( Integer.parseInt( commaValues[ i ] ) );
			}
		}
		
		int num = numbersList.size();
		int[] numbers = new int[ num ];
		for( int i = 0 ; i < num ; i++ ){
			numbers[ i ] = numbersList.get( i );
		}
		
		return numbers;
	}

    /**
     * Transforms a given sequence of numbers of the form '1-6,9,...' into a 2 dimensional int array of the form
     * {{1,6},{9,9},...}
     * @param numberSequence the sequence of numbers as String.
     * @return 2 dim int array
     */
    public static int[][] transformNumberSequenceTo2DArray( String numberSequence )
    {
        if( numberSequence == null || numberSequence.equals("")){
            return new int[0][0];
        }
        StringTokenizer tok = new StringTokenizer( numberSequence, "," );
        StringTokenizer innerTok;
        int numgl = tok.countTokens();
        int[][] array = new int[numgl][];

         for (int i=0; i < numgl; i++) {

             innerTok = new StringTokenizer( tok.nextToken(), "-" );
             array[i] = new int[2];
             if( innerTok.countTokens() == 2 ){
                 array[i][0] = Integer.parseInt( innerTok.nextToken() );
                 array[i][1] = Integer.parseInt( innerTok.nextToken() );
             }
             else{
                 array[i][0] = Integer.parseInt( innerTok.nextToken() );
                 array[i][1] = array[i][0];
             }
         }

        return array;

    }//transformNumberSequenceTo2DArray()

	/**
	 * Check whether a pattern is contained in a base string.<br>
	 * You can use <code><b>*</b></code> as wildcard.
	 * 
	 * @param pattern String to be searched for
	 * @param base String to be searched in
	 * @return Returns true, if the pattern is contained in the base string,
	 *         otherwise false.
	 */
	public static boolean searchPatternInString( String pattern, String base ) {
		StringBuffer buff = new StringBuffer( base );
		
		
		StringTokenizer tokenizer = new StringTokenizer( pattern, "*" );
		String[] terms = new String[ tokenizer.countTokens() ];
		for( int i = 0 ; i < terms.length ; i++ ){
			terms[ i ] = tokenizer.nextToken();
		}
		// return false, if the base begins neither with a wildcard nor the first part of the pattern 
		if( !pattern.startsWith( "*" ) && base.indexOf( terms[ 0 ] ) != 0 ) return false;
		// return false, if the base ends neither with a wildcard nor the last part of the pattern
		if( !pattern.endsWith( "*" )
				&& base.indexOf( terms[ terms.length - 1 ] ) != base.length() - terms[ terms.length - 1 ].length() )
			return false;
		// Check if all pattern are in correct order contained in the base
		for( int i = 0 ; i < terms.length ; i++ ){
			if( buff.indexOf( terms[ i ] ) > -1 ){
				buff.delete( buff.indexOf( terms[ i ] ), buff.indexOf( terms[ i ] ) + terms[ i ].length() );
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * Convert a float array to a String, which represents this array as a comma
	 * separated list.
	 * 
	 * @param fArr float[]
	 * @param delim the delimiter, if delim == null, a simple comma is used.
	 * @return String
	 */
	public static String convertFloatArrayToString( float[] fArr, String delim ) {
		if( fArr == null ) return "null";
		String delimiter = ( delim == null ? "," : delim );
		StringBuffer sb = new StringBuffer();
		for( int i = 0 ; i < fArr.length ; i++ ){
			sb.append( fArr[ i ] + delimiter );
		}
		if( sb.length() > 0 ) sb.deleteCharAt( sb.length() - delimiter.length() );
		return sb.toString();
	}

	/**
	 * Convert an int array to a String, which represents this array as a comma
	 * separated list.
	 * 
	 * @param iArr int[]
	 * @param delim the delimiter, if delim == null, a simple comma is used.
	 * @return String
	 */
	public static String convertIntArrayToString( int[] iArr, String delim ) {
		if( iArr == null ) return "null";
		String delimiter = ( delim == null ? "," : delim );
		StringBuffer sb = new StringBuffer();
		for( int i = 0 ; i < iArr.length ; i++ ){
			sb.append( iArr[ i ] + delimiter );
		}
		if( sb.length() > 0 ) sb.deleteCharAt( sb.length() - delimiter.length() );
		return sb.toString();
	}

	/**
	 * Convert an int array to a String, which represents this array as a comma
	 * separated list. Leading and trailing blanks are ignored.
	 * 
	 * @param s the String as a list of int values separated by a delimiter
	 * @param delim the separation delimiter.
	 * @return an int array.
	 * @throws java.lang.NumberFormatException
	 */
	public static int[] convertStringToIntArray( String s, char delim ) throws NumberFormatException {
		char[] cArr = s.toCharArray();
		int numTokens = 0, lastPos = -1;
		int[] result = new int[ s.length() ]; // maximale Laenge, wird am Ende gekuerzt zurueckgegeben
		String tmpS;
		int firstDigitPos = 0, lastDigitPos = 0;
		for( int i = 0 ; i < cArr.length ; i++ ){
			if( cArr[ i ] == delim ){ // Zeichen gefunden
				if( i == 0 ){ // ein allererstes Trennzeichen ignorieren
					if( firstDigitPos == 0 ){ // dann wurde noch kein digit gefunden
						lastPos++ ;
					}
					continue;
				}
				else{
					// "leere" Stelle (zwei Trennzeichen direkt hintereinander, v.a. am Anfang) abfangen
					if( i - lastPos <= 1 ){
						result[ numTokens++ ] = 0;
						lastPos = i;
					}
					else if( numTokens == 0 ){ // allererstes Token
						char[] cTmp = new char[ i ];
						System.arraycopy( cArr, 0, cTmp, 0, i );
						tmpS = new String( cTmp );
						// blanks entfernen, da parseInt keine Leerzeichen zulaesst
						tmpS = tmpS.trim();
						result[ numTokens++ ] = Integer.parseInt( tmpS );
						lastPos = i;
					}
					else{
						char[] cTmp = new char[ lastDigitPos - lastPos ];
						System.arraycopy( cArr, lastPos + 1, cTmp, 0, lastDigitPos - lastPos );
						// blanks entfernen, da parseInt keine Leerzeichen zulaesst
						tmpS = new String( cTmp );
						tmpS = tmpS.trim();
						result[ numTokens++ ] = Integer.parseInt( tmpS );
						lastPos = i;
					}
					firstDigitPos = 0;
				}
			} // if( cArr[i] == delim )
			else{ // must be any digit
				firstDigitPos = i;
			}
			lastDigitPos = i;
		}
		boolean isDigit = false;
		if( lastPos < cArr.length ){ // dann kommt nach dem letzten Komma noch eine Zahl
			char[] cTmp = new char[ cArr.length - lastPos - 1 ];
			System.arraycopy( cArr, lastPos + 1, cTmp, 0, lastDigitPos - lastPos );
			for( int i = 0 ; i < cTmp.length ; i++ ){
				if( cTmp[ i ] != ' ' ){
					isDigit = true;
					break;
				}
			}
			if( isDigit ){ // nur wenn noch was kommt:
				// blanks entfernen, da parseInt keine Leerzeichen zulaesst
				tmpS = new String( cTmp );
				tmpS = tmpS.trim();
				result[ numTokens++ ] = Integer.parseInt( tmpS );
			}
		}
		int[] retArr = new int[ numTokens ];
		System.arraycopy( result, 0, retArr, 0, numTokens );
		return retArr;
	}

	/**
	 * Convert a comma-separated String to a float array. Leading and trailing
	 * blanks surrounding the float values are ignored. Any empty string after
	 * the last separator occurence will be ignored.
	 * 
	 * @param s the String as a list of float values separated by a delimiter
	 * @param separator the separation character.
	 * @return an int array.
	 * @throws java.lang.NumberFormatException
	 */
	public static float[] convertStringToFloatArray( String s, char separator ) throws NumberFormatException {
		char[] cArr = s.toCharArray();
		int numTokens = 0, lastPos = -1;
		// Float-Array mit maximaler Laenge, wird am Ende gekuerzt zurueckgegeben. Dabei sind max. die
		// Haelfte aller Chars moeglich, da Separatoren ebenfalls Platz wegnehmen
		float[] result = new float[ (int)( s.length() * .5 ) ];

		String tmpS;
		int firstDigitPos = 0, lastDigitPos = 0;
		for( int i = 0 ; i < cArr.length ; i++ ){
			if( cArr[ i ] == separator ){ // Zeichen gefunden
				if( i == 0 ){ // ein allererstes Trennzeichen ignorieren
					if( firstDigitPos == 0 ){ // dann wurde noch kein digit gefunden
						lastPos++ ;
					}
					continue;
				}
				else{
					// "leere" Stelle (zwei Trennzeichen direkt hintereinander, v.a. am Anfang) abfangen
					if( i - lastPos <= 1 ){
						result[ numTokens++ ] = 0;
						lastPos = i;
					}
					else if( numTokens == 0 ){ // allererstes Token
						char[] cTmp = new char[ i ];
						System.arraycopy( cArr, 0, cTmp, 0, i );
						tmpS = new String( cTmp );
						if( tmpS.equals( "NaN" ) ){
							result[ numTokens++ ] = Float.NaN;
						}
						else{
							result[ numTokens++ ] = Float.parseFloat( tmpS );
						}
						lastPos = i;
					}
					else{
						char[] cTmp = new char[ lastDigitPos - lastPos ];
						System.arraycopy( cArr, lastPos + 1, cTmp, 0, lastDigitPos - lastPos );
						tmpS = new String( cTmp );
						if( tmpS.equals( "NaN" ) ){
							result[ numTokens++ ] = Float.NaN;
						}
						else{
							result[ numTokens++ ] = Float.parseFloat( tmpS );
						}
						lastPos = i;
					}
					firstDigitPos = 0;
				}
			}
			else{ // must be any digit
				firstDigitPos = i;
			}
			lastDigitPos = i;
		}
		boolean isDigit = false;
		if( lastPos < cArr.length ){ // dann kommt nach dem letzten Komma noch eine Zahl
			char[] cTmp = new char[ cArr.length - lastPos - 1 ];
			System.arraycopy( cArr, lastPos + 1, cTmp, 0, lastDigitPos - lastPos );
			for( int i = 0 ; i < cTmp.length ; i++ ){
				if( cTmp[ i ] != ' ' ){
					isDigit = true;
					break;
				}
			}
			if( isDigit ){ // nur wenn noch was kommt:
				tmpS = new String( cTmp );
				result[ numTokens++ ] = Float.parseFloat( tmpS );
			}
		}
		float[] retArr = new float[ numTokens ];
		System.arraycopy( result, 0, retArr, 0, numTokens );
		return retArr;
	}

	/**
	 * Splits a String containing texts separated with a given delimiter 
	 * into individual Strings carried ba y String array
	 * <br>example: "*abc*bb*" leads to: "abc" and "bb"
	 * @param string the string which will be splitted
	 * @param delimiter the delimiter which separates the Strings
	 * @return a String[] containing the separated Strings
	 */
	public static String[] splitString( String string, char delimiter ) {
		string = StringUtilities.trimCharacter( string, delimiter );
		char[] charArray = string.toCharArray();
		ArrayList<String> list = new ArrayList<String>();

		list.clear();

		int startPos = -1;
		int firstDelimPos = 0;
		int lastDelimPos = 0;

		for( int endPos = firstDelimPos ; endPos < charArray.length ; endPos++ ){
			if( charArray[ endPos ] == delimiter ){

				lastDelimPos = endPos;
				int arrayLaenge = endPos - startPos - 1;

				// Falls keine Zeichen zwischen den Delimitern sind
				if( arrayLaenge == 0 ){
					startPos = endPos;
					continue;
				}

				// char Array fuer die Zeichen bzw. Zeichenketten zwischen den Delimitern
				char[] tmp = new char[ arrayLaenge ];

				System.arraycopy( charArray, startPos + 1, tmp, 0, arrayLaenge );
				String str = new String( tmp );
				list.add( str );
				startPos = endPos;
			}
		}

		// Falls die letzten Elemente kein "*" sind
		if( lastDelimPos < charArray.length ){
			char[] tmp = new char[ charArray.length - lastDelimPos - 1 ];
			System.arraycopy( charArray, lastDelimPos + 1, tmp, 0, charArray.length - lastDelimPos - 1 );
			String str = new String( tmp );
			list.add( str );
		}
		return list.toArray( new String[ list.size() ] );
	}

      /**
       * Expand a simple list sequence of the form '23-45'
       * @param sequence the given sequence. A wrong sequence order
       * (e.g. the second value is lower than the first value) will be corrected.
       * @param sequenceDelim a delimiter to separate the sequences (may be NULL => default='-').
       * If this delimiter has more than one character, only the first character is taken!
       * @return int[]
       * @throws java.lang.Exception
       */
      public static int[] expandSequence( String sequence, String sequenceDelim ) throws Exception {
          int[] intArr = null;
          char delim = ( sequenceDelim == null || sequenceDelim.length() == 0 ? '-' : sequenceDelim.charAt(0) );
          if( sequence == null  || sequence.length() == 0 ) throw new Exception( "the given sequence is NULL or epty!" );
          char[] cArr = sequence.toCharArray();
          int delimPos = 0;
          for( int i = 0; i < cArr.length; i++ ){
              if( cArr[i] == delim ){
                  delimPos = i;
                  break;
              }
          }
          int firstNum = Integer.parseInt( new String( cArr, 0, delimPos ) );
          int secondNum = Integer.parseInt( new String( cArr, delimPos+1, cArr.length-delimPos-1 ) );
          if( firstNum > secondNum ){
              int itmp = firstNum;
              firstNum = secondNum;
              secondNum = itmp;
          }
          intArr = new int[ secondNum - firstNum + 1 ];
          for( int i = 0; i < intArr.length; i++ ){
              intArr[i] = firstNum + i;
          }
          return intArr;
      }

      /**
       * Expand a given list row. The row format is given as follows:
       * <br>'12-13,34,47-99'
       * <br>Attention: this Version does NOT check whether the result contains duplicated values!
       * @param row the given list row
       * @param sequenceDelim a delimiter to separate the sequences (may be NULL => default='-')
       * If this delimiter has more than one character, only the first character is taken!
       * @param listDelim a delimiter to separate the list (may be NULL => default=',')
       * If this delimiter has more than one character, only the first character is taken!
       * @return an int array containing the
       * @throws java.lang.Exception
       */
      public static int[] expandListRow( String row, String sequenceDelim, String listDelim ) throws Exception {
          int[] intArr = null;
          String lDelim = ( listDelim != null && listDelim.length() > 0 ? listDelim : "," );
          String sDelim = ( sequenceDelim != null && sequenceDelim.length() > 0 ? sequenceDelim : "-" );
          String[] sequences = splitString( row, listDelim.charAt(0) );

//          System.out.println( "Griven String: " + row + "\n");
//          for( int i = 0; i < sequences.length; i++ ){
//              System.out.println( "seq::" + sequences[i] );
//          }
//          System.out.println( "" );


          int totalLength = 0;
          int[][] allSeqs = new int[sequences.length][];
          for( int i = 0; i < sequences.length; i++ ){
              if( sequences[i].indexOf( sDelim.charAt(0) ) >= 0 ){ // es ist eine echte Sequenz
                  allSeqs[ i ] = expandSequence( sequences[i], sDelim );
                  totalLength += allSeqs[i].length;
              }
              else{ // einzelne Zahl
                  allSeqs[ i ] = new int[]{ Integer.parseInt( sequences[i] ) };
                  totalLength++;
              }
          }
          // zuletzt alle einzelnen Sequenzen in eiziges int-Array:
          intArr = new int[ totalLength ];
          totalLength = 0;
          for( int i = 0; i < sequences.length; i++ ){
              for( int j = 0; j < allSeqs[i].length; j++ ){
                  intArr[totalLength++] = allSeqs[i][j];
              }
          }
//          for( int i = 0; i < intArr.length; i++ ){
//              System.out.print( "" + intArr[i] + ", " );
//          }
//          System.out.println( "" );
          return intArr;
      }

	/**
	 * create a sql command extension to filter the query result for a list of
	 * given version names. In WISA, Orbits, and all kinds of GeoLocation
	 * Objects may have several versions. A version name may be any name stored
	 * in the database ('V3O', 'V1', ...) to load data with dedicated versions
	 * or 'all' to load data for all versions.
	 * 
	 * @param versions aString array containing the version names
	 * @return a resulting sql extension String containing the sql code
	 *         " AND Version IN ( 'V1', 'V2' ) " or an empty string (no
	 *         filtering) when all versions should be regarded
	 */
	private static String createVersionSelectionString( String[] versions ) {
		String sqls = "";
		if( versions != null && versions.length != 0 && !versions[ 0 ].equals( "all" ) ){
			sqls = " AND version IN (";

			for( int i = 0 ; i < versions.length ; i++ ){
				sqls += " '" + versions[ i ] + "' ";
				if( i < versions.length - 1 ) sqls += ", ";
			}
			sqls += ")";
		}
		return sqls; // empty or filled
	}

	/**
	 * remove all occurences of a given character from a given String
	 * @param s the String
	 * @param c the character
	 * @return an new (maybe shorter) String without the given character
	 */
	public static String removeCharacters( String s, char c ) {
		return removeCharacters( s.toCharArray(), c );
	}

	/**
	 * remove all occurences of a given character from a given character array
	 * @param s the character array
	 * @param c the character
	 * @return an new (maybe shorter than the given array) String without the given character
	 */
	public static String removeCharacters( char[] input, char c ) {
		return new String( removeCharacters( input, c ) );
	}

	/**
	 * remove all occurences of a given character from a given character array
	 * @param s the character array
	 * @param c the character
	 * @return an new (maybe shorter) character array without the given character
	 */
	public static char[] removeChars( char[] input, char c ) {
		int j = 0;
		for( int i = 0 ; i < input.length ; i++ ){
			if( input[ i ] == c ){
				j++ ;
				continue;
			}
			input[ i - j ] = input[ i ];
		}
		char[] output = new char[ input.length - j ];
		System.arraycopy( input, 0, output, 0, input.length - j );
		return output;
	}

	/**
	 * Parse a given 2-dimensional point to String
	 * 
	 * @param p The Point2D to be parsed.
	 * @return The given Point2D parsed to String with 1 decimal place
	 */
	static public String format( Point2D p ) {
		return format( p, 1 );
	}

	/**
	 * Parse a given 2-dimensional point with a given precision to String.
	 * 
	 * @param p The Point2D to be parsed.
	 * @param precision The precision.
	 * @return The given Point2D parsed to String.
	 */
	static public String format( Point2D p, int precision ) {
		String ret = "(";
		if( !Double.isNaN( p.getX() ) ){
			ret += p.getX();
			ret = shrinkToPrecision( ret, precision );
		}
		else
			ret += "NaN";
		ret += ";";
		if( !Double.isNaN( p.getY() ) ){
			ret += p.getY();
			ret = shrinkToPrecision( ret, precision ); // geht, da lastIndexOf in shrinkToPrecision(*)
		}
		else
			ret += "NaN";
		ret += ")";
		return ret;
	}

	/**
	 * Parse a given 2-dimensional point as String with a given precision to String.
	 * 
	 * @param s The String to be parsed.
	 * @param precision The precision.
	 * @return The newly parsed to String.
	 */
	static private String shrinkToPrecision( String s, int precision ) {
		int pos = s.lastIndexOf( '.' );
		if( pos == -1 ) return s;
		pos += precision;
		if( pos > s.length() ) return s;
		return s.substring( 0, pos + 1 );
	}

	/**
	 * Returns a string with any leading characters removed
	 * 
	 * @param s string which will be trimmed
	 * @param c the character to be trimmed
	 * @return the given string without leading given character
	 * @see Class {@link java.lang.String#trim()}
	 */
	public static String trimFirstCharacters( String s, char c ) {

		char[] ca = s.toCharArray();

		int startPos = 0;
		while( ( startPos < ca.length ) && ( ca[ startPos ] == c ) ){
			startPos++ ;
		}

		return s.substring( startPos );

	}

	/**
	 * Returns a string with any trailing characters removed
	 * 
	 * @param s string which will be trimmed
	 * @param c the character to be trimmed
	 * @return the given string without trailing given character
	 * @see Class {@link java.lang.String#trim()}
	 */
	public static String trimLastCharacters( String s, char c ) {

		char[] ca = s.toCharArray();

		int endPos = ca.length - 1;
		while( ( endPos < ca.length ) && ( ca[ endPos ] == c ) ){
			endPos-- ;
		}

		return s.substring( 0, endPos + 1 );

	}

	/**
	 * Returns a string with any leading and trailing characters removed
	 * 
	 * @param s string which will be trimmed
	 * @param c the character to be trimmed
	 * @return the given string without leading and trailing given character
	 * @see Class {@link java.lang.String#trim()}
	 */
	public static String trimCharacter( String s, char c ) {

		int len = s.length();
		int st = 0;
		char[] val = s.toCharArray(); /* avoid getfield opcode */

		while( ( st < len ) && ( val[ st ] == c ) ){
			st++ ;
		}
		while( ( st < len ) && ( val[ len - 1 ] == c ) ){
			len-- ;
		}
		if( st == 0 && len == s.length() ){
			return s;
		}
		String ret = s.substring( st, len );
		return ret;

	}

} // StringUtilities()
