package edu.kit.iai.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import WISA.exceptions.WISASystemException;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * a collection of various utility functions.
 *
 * @author diverse
 * @version $Revision: 1.35 $
 *
 */
public final class IOUtilities {

    /**
     * disallowed.
     *
     * (Stereotype: utility)
     */
    private IOUtilities() {
        assert(false);
    }

    /**
     * Print a given float array to standard out.
     * @param floatArray array to print out.
     */
     public static void print2DimFloatArray( float[][] floatArray ){

         float[] fa;
         int faLength = 0;
         for (int i = 0; i < floatArray.length ; i++) {
            fa = floatArray[i];
            if( fa == null ) System.out.println("WISAUtility::print2DimFloatArray: floatArray[" + i + "] == null !!!");
            else{
                System.out.print("[");
                faLength = fa.length-1;
                for (int j = 0; j < faLength; j++) {
                   System.out.print( fa[j] + ", ");
                }
                System.out.println( fa[faLength] + "]");
            }
         }//for()
     }

     /**
      * Print a given float array to standard out.
      * @param floatArray array to print out.
      */
      public static void printFloatArray( float[] floatArray ){
          if( floatArray == null ){
              AppLogger.error( "no float array given!" );
              return;
          }
          System.out.print("[");
          for (int i = 0; i < floatArray.length ; i++) {
              System.out.print( " "+floatArray[i] );
              if( i < floatArray.length -1 ) System.out.print( "," );
          }//for()
          System.out.print("]");
      }


   /**
    * Print the contents of a Vector of Vectors (zwo-dimensional vector) to standard out.
    * @param vec the 2-dim Vector to be printed out.
    */
    public static void printVector( Vector vec ){
         Vector v;
         System.out.println("Contents of the Vector:");
         for (int i = 0; i < vec.size(); i++) {

            v = (Vector)vec.get(i);

            for (int j = 0; j < v.size() ; j++) {
                System.out.print( v.get(j)+",+++" );
            }
            System.out.println("");
         }
    }



    /**
     * Descends the current threads stack trace to find the outside callee of the callee.
     *
     * <BR><b>Outside Callee:</b> Consider classes A and B. Method A.a calls Method b of B. When
     * B.b invokes {@link #getOutsideCallee} the returned {@link StackTraceElement} will
     * refer to the call to B.b in A.a. When there is an additional level of indirection
     * in B, like B.b invokes B.c which calls {@link #getOutsideCallee} the return
     * value would still be the same. This means the 'outside' of the calling class.
     *
     * @return the outside callee of the current public interface method. (May be null)
     * <br>Author: Thomas Beckmann
     */
    public static final StackTraceElement getOutsideCallee() {
        final StackTraceElement[] ste = new Throwable().getStackTrace();
        // not every VM implements this feature (Java Micro Edition):
        if(ste == null)
            return null;
        if(ste.length <= 0)
            return null;
        // we need to test a lot since the returned trace might be limited to a certain depth on same VMs:
        int i = 0;
        // skip frames inside of WISAUtility:
        final String THIS_CLASSNAME = IOUtilities.class.getName();
        for(; i < ste.length; ++i)
            if(!ste[i].getClassName().startsWith(THIS_CLASSNAME))
                break;
        if(i >= ste.length)
            return null; // end reached before we found it
        assert(i >= 0 && i < ste.length);
        // skip frames inside of the direct callee:
        final String directCalleeClassName = ste[i].getClassName();
        assert(directCalleeClassName != null);
        for(; i < ste.length; ++i)
            if(!ste[i].getClassName().startsWith(directCalleeClassName))
                break;
        if(i >= ste.length)
            return null; // end reached before we found it
        // we got it:
        assert(i >= 0 && i < ste.length);
        return ste[i];
    }



    /**
     * execute a simple shell command.
     *
     * <BR> This procedure is designed for commands like cp or rm to perform a simple
     * task. The command is executed and the current thread blocked until it
     * returns. When the return code of the external program is not 0, or when
     * the thread is interrupted during the blocking, a RuntimeException with
     * the given message is raised. Otherwise the procedure returns to the callee.<BR>
     * <BR>
     * <b>Note:</b> {@link java.io.IOExceptions} and {@link java.lang.InterruptedExceptions}
     * are wrapped in a {@link RuntimeException}, if any occures. Since this does
     * not always make sense it should be considered to unwrap them by client code.
     *
     * @param cmd command to execute including any parameters.
     * @param fail_msg message text to be included in the exception which is raised when
     * the given command does not return succesfuly.
     * @throws WISASystemException when the given command failed.
     * @return the exit code of the shell command.
     * @see Runtime#exec
     * <br>Author: Thomas Beckmann
     */
//    public static final int doOSCommand(String cmd, String fail_msg) throws WISASystemException {
    public static final int doOSCommand(String cmd, String fail_msg) throws Exception {
        AppLogger.debug("processing command >>" + cmd + "<< ...." );
        return internal_doOSCommand( null, cmd, fail_msg );
    }


    /**
     * execute a simple shell command
     *
     * <BR> This procedure is designed for commands like cp or rm to perform a simple
     * task. The command is executed and the current thread blocked until it
     * returns. When the return code of the external program is not 0, or when
     * the thread is interrupted during the blocking, a RuntimeException with
     * the given message is raised. Otherwise the procedure returns to the callee.<BR>
     * <BR>
     * <b>Note:</b> {@link java.io.IOExceptions} and {@link java.lang.InterruptedExceptions}
     * are wrapped in a {@link RuntimeException}, if any occures. Since this does
     * not always make sense it should be considered to unwrap them by client code.
     *
     * @param cmdArray command to execute including any parameters given as an String array. This version of the
     * doOSCommand is designed for the usage of a proceeding /bin/sh call to be able to interpret shell built-ins.
     * In this case, the array must be
     * filled in the way that the /bin/sh command is the first component, additional arguments build the second
     * component and the rest of the command builds the third component of the array:
     * <br>cmdArr[0]="/bin/sh"; ==> important: do not use any blanks for the first component!!
     * <br>cmdArr[1]="-c"; ==> important: do not use any blanks for the second component, when there is only one flag!!
     * <br>cmdArr[2]="ls -l abc* ";
     * <br>
     * @param fail_msg message text to be included in the exception which is raised when
     * the given command does not return succesfully.
     * @throws WISASystemException when the given command failed.
     * @return the exit code of the shell command.
     * @see Runtime#exec
     * <br>Author: Thomas Beckmann
     */
    public static final int doOSCommand(String[] cmdArray, String fail_msg) throws Exception {
        final StringBuffer sb = new StringBuffer();
        for( int i = 0; i < cmdArray.length; i++ ){
            sb.append( cmdArray[ i ] + " " );
        }
        AppLogger.debug( "processing command (array) >>" + sb.toString() + "<< ...." );

        return internal_doOSCommand( cmdArray, null, fail_msg );
    }


    /**
     * This method combines both the call of a OS command with a given String and the command with a given String array
     * @param cmdArray the (UNIX) command divided into an array of command words and arguments.
     * @param cmd the command as a single string. This argument is only used, when cmdArray is NULL.
     * @param fail_msg The message that should be shown if the command fails.
     * @return the exit code of the command.
     * @throws WISASystemException
     */
    private static final int internal_doOSCommand(String[] cmdArray, String cmd, String fail_msg) throws Exception {
        int exitCode = -1;
        Process p = null;
        final java.io.ByteArrayOutputStream outstream = new java.io.ByteArrayOutputStream();
        try {
            if( cmdArray != null ) p = Runtime.getRuntime().exec(cmdArray);
            else if( cmd != null ) p = Runtime.getRuntime().exec(cmd);
            else{
//            	throw new WISASystemException( "Error performing an OS command! \n::> " + fail_msg, -2, null,  "no command given!" );
                throw new Exception( "Error performing an OS command! \n::> " + fail_msg +  "no command given!" );
            }
            final java.io.InputStream instream = p.getErrorStream();
            /* For test purposes:
               System.out.println("catching output");
               java.io.BufferedReader procout = new java.io.BufferedReader( new java.io.InputStreamReader(p.getInputStream()) );
               String line;
               while ((line = procout.readLine()) != null) {
                      System.out.println("  OUT> " + line);
               }
            */
            final byte[] buffer = new byte[0xff];
            for(;;) {
                final int len = instream.read(buffer);
                if(len == -1)
                    break;
                outstream.write(buffer, 0, len);
            }
            exitCode = p.waitFor();
            outstream.close();
            if( outstream.size() > 0 ){
                AppLogger.debug( "the output stream was not empty! \n"
                		+ "Contents: \n >>\n " + outstream.toString() + " <<" );
            }
            if( exitCode != 0 ){
//            	throw new WISASystemException( fail_msg, exitCode, "WISAUtility.doOSCommand()",
                throw new Exception( fail_msg +
                		" Error performing an OS command!\n==> EXIT-Code: " + exitCode + "\n==> "+ outstream.toString() );

            }
        }
        catch( final java.io.IOException iox ){
        	throw new Exception( fail_msg + "WISAUtility.doOSCommand(): " + iox.getMessage() ); // convert
//            throw new WISASystemException( fail_msg, exitCode, "WISAUtility.doOSCommand()", iox.getMessage() ); // convert
        }
        catch( final InterruptedException ix ){
//            throw new RuntimeException(fail_msg, ix); // convert
//        	throw new WISASystemException( fail_msg, exitCode, "WISAUtility.doOSCommand()", ix.getMessage() ); // convert
            throw new Exception( fail_msg + ", EXIT-Code: " + exitCode + ", WISAUtility.doOSCommand(): " + ix.getMessage() ); // convert
        }
        catch( final RuntimeException rx ){
//        	throw new WISASystemException( fail_msg, exitCode, "WISAUtility.doOSCommand()", rx.getMessage() ); // convert
            throw new Exception( fail_msg + ", EXIT-Code: " + exitCode + ", WISAUtility.doOSCommand(): " + rx.getMessage() ); // convert
        }
        return exitCode;
    }


    /**
     * reads properties from a given InputStream and creates a new Properties object for them.
     *
     * @param istream to read the properties from.
     * @param overwritable if this is true properties already existing in the global properties
     * 			are not read. This allows the user overwrite configuration properties on the
     * 			java command line like 'java -DWISA.root=/home/Processor/software/wisa'. Usually,
     * 			this should be set to true.
     * @param global copies all properties to the global table as retrieved by {@link java.lang.System#getProperties}.
     * 			Usually this is set to false.
     * @return properties storing the information of the given stream. The returned object is always
     * 			backed by the global properties. The object is not intended to be mutated. (If 'global'
     * 			is true the global properties are returned.)
     * @throws IOException when loading the data unsuccessfully.
     *  <br>Author: Thomas Beckmann
     */
    public static final Properties loadProperties(java.io.InputStream istream, boolean overwritable, boolean global)
			throws java.io.IOException
    {
		// load
		final Properties global_properties = System.getProperties();
		Properties properties = new Properties( global_properties );
		properties.load( istream );
		// overwritable
		if( overwritable ){
			final Iterator<?> iterator = properties.keySet().iterator();
			while( iterator.hasNext() ){
				final String key = (String)iterator.next();
				if( global_properties.getProperty( key ) != null ) iterator.remove();
			}
		}
		// global
		if( global ){
			global_properties.putAll( properties );
			properties = global_properties;
		}
		// done
		return properties;
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////
      /**
       * A simplified way to see a JPanel or other Container.
       * Pops up a JFrame with specified Container as the content pane.
       * Source: 1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
       * @param content Container that should be shown
       * @param width int width of the Frame
       * @param height int height of the Frame
       * @param title String title of the Frame
       * @param bgColor the background color ot the container
       * @param exitToOS if true, the frame can be closed when
       * pressing the GUI close icon (upper right corner) and the application exits.
       * If false, the frame will only be enbled and invisible.
       * @return JFrame the Frame itself
       */
      public static JFrame openInJFrame(Container content,
                                        int width,
                                        int height,
                                        String title,
                                        Color bgColor,
                                        boolean exitToOS )
      {
          final JFrame frame = new JFrame(title);
          frame.setBackground(bgColor);
          content.setBackground(bgColor);
          frame.setSize(width, height);
          frame.getContentPane().add(content);
//          frame.setContentPane(content);
          if( exitToOS ){
              frame.addWindowListener( new WindowAdapter()
              {
                  @Override
				public void windowClosing( WindowEvent event ){
                      System.exit( 0 );
                  }
              } );
          }
          else{
              frame.addWindowListener( new WindowAdapter()
              {
                  @Override
				public void windowClosing( WindowEvent event ){
                      frame.setEnabled( false );
                      frame.setVisible( false );
//                      final Container cnt = frame.getContentPane();
                      frame.getContentPane().removeAll();  // loeschen, Speicherplatz freigeben
                  }
              } );
          }

          frame.setVisible(true);
          return(frame);
      }

      /** Uses Color.white as the background color. */

      /**
       * A simplified way to see a JPanel or other Container.
       * Pops up a JFrame with specified Container as the content pane.
       * Source: 1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
       * Uses Color.white as the background color.
       * @param content Container that should be shown
       * @param width int width of the Frame
       * @param height int height of the Frame
       * @param title String title of the Frame
       * @param exitToOS if true, the frame can be closed when
       * pressing the GUI close icon (upper right corner) and the application exits.
       * If false, the frame will only be enbled and invisible.
       * @return JFrame the Frame itself
       */
      public static JFrame openInJFrame(Container content,
                                        int width,
                                        int height,
                                        String title,
                                        boolean exitToOS) {
        return(openInJFrame(content, width, height, title, Color.white, exitToOS ));
      }

      /** Uses Color.white as the background color, and the
       *  name of the Container's class as the JFrame title.
       */

      /**
       * A simplified way to see a JPanel or other Container.
       * Pops up a JFrame with specified Container as the content pane.
       * Source: 1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
       * Uses Color.white as the background color, and the
       *  name of the Container's class as the JFrame title.
       * @param content Container that should be shown
       * @param width int width of the Frame
       * @param height int height of the Frame
       * @param exitToOS if true, the frame can be closed when
       * pressing the GUI close icon (upper right corner) and the application exits.
       * If false, the frame will only be enbled and invisible.
       * @return JFrame the Frame itself
       */
      public static JFrame openInJFrame(Container content,
                                        int width,
                                        int height,
                                        boolean exitToOS) {
        return(openInJFrame(content, width, height,
                            content.getClass().getName(),
                            Color.white, exitToOS ));
      }


      /**
       * show a input pane that requires the input of 3 float values using a spinner element.
       * @param vals a float[3] array with the initial values (e.g. x, y, z).
       * May be null, then the values {1.0, 1.0, 1.0} are used. They will be set with the selected values
       * for the next start of this pane with the last values.
       * @param labels a String[3] array with the labels that should be shown for each value.
       * May be null, then {"X:", "Y:", "Z:" } is used.
       * @param title the headline text.
       * @param source any JComponent to calculate the referencing location of the dialog, may be null.
       * @param minVals a float[3] array with the minimal values being allowed.
       * May be null, then the values {0.1, 0.1, 0.1} are used. The minVals MUST be lower than the maxValues.
       * @param maxVals a float[3] array with the maximal values being allowed.
       * May be null, then the values {10.0, 10.0, 10.0} are used. The maxVals MUST be lower than the minValues.
       * @param stepSizes a float[3] array with the step sizes for the Spinner model,
       * May be null, then the values {0.1, 0.1, 0.1} are used.
       * i.e. the increments for increasing or decreasing chosen values.
       * @param resetValues These Values will be taken when the reset button is pressed.
       * May be null, then the minimal values are used.
       * @return a float[3] array containing the chosen values or null, if the dialog cancel button was pressed.
       */
      public static float[] showInput3FloatsGUI( float[] vals, String[] labels, String title, JComponent source,
                                           float[] minVals, float[] maxVals, float[] stepSizes, float[] resetValues ){
          if( AppLogger.getSeverity() == AppLogger.DEBUG ){
              System.out.println( "-----------------------------\nArgs given for WISAUtility.showInput3FloatsGUI:" );
              System.out.println( "vals: " + (vals==null?"null":vals[0]+","+vals[1]+","+vals[2])
                                  + "\nlabels: " + (labels==null?"null":labels[0]+","+labels[1]+","+labels[2])
                                  + "\ntitle: " + (labels==null?"null":title)
                                  + "\nminVals: " + (minVals==null?"null":minVals[0]+","+minVals[1]+","+minVals[2])
                                  + "\nmaxVals: " + (maxVals==null?"null":maxVals[0]+","+maxVals[1]+","+maxVals[2])
                                  + "\nstepsizes: " + (stepSizes==null?"null":stepSizes[0]+","+stepSizes[1]+","+stepSizes[2])
                                  + "\nsource: " + (source==null?"null":source.toString().substring(0,20)) );
          }

          float[] values = new float[3];
          if( minVals == null ) minVals = new float[]{ .0f, .0f, .0f };
          if( maxVals == null ) maxVals = new float[]{ 10.f, 10.f, 10.f };
          if( stepSizes == null ) stepSizes = new float[]{ .1f, .1f, .1f };
          if( vals == null ){
              values = new float[]{ 1.f, 1.f, 1.f };
          }
          else if( vals[0] < minVals[0]  ||  vals[1] < minVals[1]  ||  vals[2] < minVals[2] ){
              minVals[0] = vals[0];
              minVals[1] = vals[1];
              minVals[2] = vals[2];
              AppLogger.warning( "Current values are greater than given minimal values! ==> corrected ..." );
          }
          else if( vals[0] > maxVals[0]  ||  vals[1] > maxVals[1]  ||  vals[2] > maxVals[2] ){
              maxVals[0] = vals[0];
              maxVals[1] = vals[1];
              maxVals[2] = vals[2];
              AppLogger.warning( "Current values are greater than given minimal values! ==> corrected ..." );
          }

          if( labels == null ){
              labels = new String[]{ "X:", "Y:", "Z:" };
          }
          final JSpinner XSpinner = new JSpinner( new SpinnerNumberModel( vals[0], minVals[0], maxVals[0], stepSizes[0] ) );
          final JSpinner YSpinner = new JSpinner( new SpinnerNumberModel( vals[1], minVals[1], maxVals[1], stepSizes[1] ) );
          final JSpinner ZSpinner = new JSpinner( new SpinnerNumberModel( vals[2], minVals[2], maxVals[2], stepSizes[2] ));
          ZSpinner.setPreferredSize( new Dimension( 80, 20 ) );

          final JButton resetButton = new JButton("reset");

          // folgende 4 final-Deklarationen, weil "XSpinner.getModel().setValue( new Float(1.0) )" zum locking fuehrte
          final float[] minV = minVals;
          final float[] maxV = maxVals;
          final float[] stepS = stepSizes;
          final float[] resetV = resetValues;
          for( int i = 0; i < resetV.length; i++ ){
              if( resetV[i] < minVals[i] ) resetV[i] = minVals[i];
              if( resetV[i] > maxVals[i] ) resetV[i] = maxVals[i];
          }

          resetButton.addActionListener( new ActionListener()
          {
              @Override
			public void actionPerformed( ActionEvent actionEvent ){
                  XSpinner.setModel( new SpinnerNumberModel( resetV[0], minV[0], maxV[0], stepS[0] ) );
                  YSpinner.setModel( new SpinnerNumberModel( resetV[1], minV[1], maxV[1], stepS[1] ) );
                  ZSpinner.setModel( new SpinnerNumberModel( resetV[2], minV[2], maxV[2], stepS[2] ) );
              }
          } );

          final JPanel pnl = new JPanel( new GridBagLayout() );
          pnl.add( new JLabel( labels[0] ), new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 5 ), 0, 0 ) );
          pnl.add( XSpinner, new GridBagConstraints( 1, 0, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
          pnl.add( new JLabel( labels[1] ), new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 5 ), 0, 0 ) );
          pnl.add( YSpinner, new GridBagConstraints( 1, 1, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
          pnl.add( new JLabel( labels[2] ), new GridBagConstraints( 0, 2, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 5 ), 0, 0 ) );
          pnl.add( ZSpinner, new GridBagConstraints( 1, 2, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
          pnl.add( resetButton, new GridBagConstraints( 2, 1, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 10, 0, 0 ), 0, 0 ) );
          final int ret = JOptionPane.showConfirmDialog( source, pnl,
                                                  title, JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE, null );
          if( ret == JOptionPane.OK_OPTION )
          {
              values[ 0 ] = ((Double)XSpinner.getValue()).floatValue();
              values[ 1 ] = ((Double)YSpinner.getValue()).floatValue();
              values[ 2 ] = ((Double)ZSpinner.getValue()).floatValue();
              if( vals != null ) { // Eingangsdaten mit neuen Werten setzen fuer nachsten Start
                  vals[0] = values[ 0 ];
                  vals[1] = values[ 1 ];
                  vals[2] = values[ 2 ];
              }
              return values;
          }
          return null;
      }

      /**
       * show a input pane that requires the input of 2 float values using a spinner element.
       * @param vals a float[2] array with the initial values (e.g. x, y).
       * May be null, then the values {1.0, 1.0} are used. They will be set with the selected values
       * for the next start of this pane with the last values.
       * @param labels a String[2] array with the labels that should be shown for each value.
       * May be null, then {"X:", "Y:"} is used.
       * @param title the headline text.
       * @param source any JComponent to calculate the referencing location of the dialog, may be null.
       * @param minVals a float[2] array with the minimal values being allowed.
       * May be null, then the values {0.1, 0.1} are used. The minVals MUST be lower than the maxValues.
       * @param maxVals a float[2] array with the maximal values being allowed.
       * May be null, then the values {10.0, 10.0} are used. The maxVals MUST be lower than the minValues.
       * @param stepSizes a float[2] array with the step sizes for the Spinner model,
       * May be null, then the values {0.1, 0.1} are used.
       * i.e. the increments for increasing or decreasing chosen values.
       * @param resetValues These Values will be taken when the reset button is pressed.
       * May be null, then the minimal values are used.
       * @return a float[2] array containing the chosen values or null, if the dialog cancel button was pressed.
       */
      public static float[] showInput2FloatsGUI( float[] vals, String[] labels, String title, JComponent source,
                                           float[] minVals, float[] maxVals, float[] stepSizes, float[] resetValues ){
          if( AppLogger.getSeverity() == AppLogger.DEBUG ){
              System.out.println( "-----------------------------\nArgs given for WISAUtility.showInput2FloatsGUI:" );
              System.out.println( "vals: " + (vals==null?"null":vals[0]+","+vals[1])
                                  + "\nlabels: " + (labels==null?"null":labels[0]+","+labels[1])
                                  + "\ntitle: " + (labels==null?"null":title)
                                  + "\nminVals: " + (minVals==null?"null":minVals[0]+","+minVals[1])
                                  + "\nmaxVals: " + (maxVals==null?"null":maxVals[0]+","+maxVals[1])
                                  + "\nstepsizes: " + (stepSizes==null?"null":stepSizes[0]+","+stepSizes[1])
                                  + "\nsource: " + (source==null?"null":source.toString().substring(0,20)) );
          }

          float[] values = new float[2];
          if( minVals == null ) minVals = new float[]{ .0f, .0f };
          if( maxVals == null ) maxVals = new float[]{ 10.f, 10.f };
          if( stepSizes == null ) stepSizes = new float[]{ .1f, .1f };
          if( vals == null ){
              values = new float[]{ 1.f, 1.f };
          }
          else if( vals[0] < minVals[0]  ||  vals[1] < minVals[1] ){
              minVals[0] = vals[0];
              minVals[1] = vals[1];
              AppLogger.warning( "Current values are greater than given minimal values! ==> corrected ...", null );
          }
          else if( vals[0] > maxVals[0]  ||  vals[1] > maxVals[1] ){
              maxVals[0] = vals[0];
              maxVals[1] = vals[1];
              AppLogger.warning( "Current values are greater than given minimal values! ==> corrected ...", null );
          }

          if( labels == null ){
              labels = new String[]{ "X:", "Y:" };
          }
          final JSpinner XSpinner = new JSpinner( new SpinnerNumberModel( vals[0], minVals[0], maxVals[0], stepSizes[0] ) );
          final JSpinner YSpinner = new JSpinner( new SpinnerNumberModel( vals[1], minVals[1], maxVals[1], stepSizes[1] ) );
          YSpinner.setPreferredSize( new Dimension( 80, 20 ) );

          final JButton resetButton = new JButton("reset");

          // folgende 4 final-Deklarationen, weil "XSpinner.getModel().setValue( new Float(1.0) )" zum locking fuehrte
          final float[] minV = minVals;
          final float[] maxV = maxVals;
          final float[] stepS = stepSizes;
          final float[] resetV = resetValues;
          for( int i = 0; i < resetV.length; i++ ){
              if( resetV[i] < minVals[i] ) resetV[i] = minVals[i];
              if( resetV[i] > maxVals[i] ) resetV[i] = maxVals[i];
          }

          resetButton.addActionListener( new ActionListener()
          {
              @Override
			public void actionPerformed( ActionEvent actionEvent ){
                  XSpinner.setModel( new SpinnerNumberModel( resetV[0], minV[0], maxV[0], stepS[0] ) );
                  YSpinner.setModel( new SpinnerNumberModel( resetV[1], minV[1], maxV[1], stepS[1] ) );
              }
          } );

          final JPanel pnl = new JPanel( new GridBagLayout() );
          pnl.add( new JLabel( labels[0] ), new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 5 ), 0, 0 ) );
          pnl.add( XSpinner, new GridBagConstraints( 1, 0, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
          pnl.add( new JLabel( labels[1] ), new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 5 ), 0, 0 ) );
          pnl.add( YSpinner, new GridBagConstraints( 1, 1, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
          pnl.add( resetButton, new GridBagConstraints( 2, 1, 1, 1, .0, .0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 10, 0, 0 ), 0, 0 ) );
          final int ret = JOptionPane.showConfirmDialog( source, pnl,
                                                  title, JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE, null );
          if( ret == JOptionPane.OK_OPTION )
          {
              values[ 0 ] = ((Double)XSpinner.getValue()).floatValue();
              values[ 1 ] = ((Double)YSpinner.getValue()).floatValue();
              if( vals != null ) { // Eingangsdaten mit neuen Werten setzen fuer nachsten Start
                  vals[0] = values[ 0 ];
                  vals[1] = values[ 1 ];
              }
              return values;
          }
          return null;
      }


      /**
       * print the contents of a 4x4-Matrix
       * @param mat double[16] the matrix
       * @param headline String
       */
      public static void print4x4Matrix( double[] mat, String headline ){
           System.out.println( (( headline == null  ||  headline.length() == 0 ) ? "\n" : "\n"+headline ) );
           for( int i = 0; i < mat.length; i+=4 ){
               System.out.println( "" + mat[i] + ", " + mat[i+1] + ", " + mat[i+2] + ", " + mat[i+3] );
           }

      }

      /**
       * show a FileChooser Dialog with given arguments.
       * @param parent the parent component (location to center the dialog), may be <code>null</code>
       * @param path the path to open the FileChooser (if path is <code>null</code>, the user's home directory will be chosen)
       * @param title the fileChooser title (may be <code>null</code>)
       * @param extensions a String[] containing extensions for setting filters.
       * The argument itself may be <code>null</code>, but the contained elements must not be <code>null</code>!
       * @param fileSelectionMode may be JFileChooser.FILES_ONLY,
       * JFileChooser.FILES_AND_DIRECTORIES or JFileChooser.DIRECTORIES_ONLY
       * @param filterTextDescriptions a String array of texts that are shown on the FileChooser for filtered extensions.
       * The argument itself may be <code>null</code>, but the contained elements must not be <code>null</code>
       * and must have the same length as argument <b>extensions</b>!
       * @return the selected file or null, if the user cancels the dialog
       */
      public static File getFileFromAFileChooser( Component parent, String path, String title, String[] extensions,
                                                  int fileSelectionMode, String[] filterTextDescriptions  ){
          final JFileChooser filechooser = new JFileChooser( path == null ?
                                                       System.getProperty( "user.dir" ) : path );
          filechooser.setFileSelectionMode( fileSelectionMode );
          if( title != null ) filechooser.setDialogTitle(title);

          final String[] exts = extensions;
          if( exts != null ){
	          String[] filterTextDescrs = filterTextDescriptions;
	          if( filterTextDescrs == null ) {
	        	  filterTextDescrs = new String[ exts.length ];
	        	  for( int i = 0 ; i < filterTextDescrs.length ; i++ ){
	        		  filterTextDescrs[i] = new String( "Filter for " + exts[i] + " files " );
				}
	          }
        	  if( exts[0] != null ) filechooser.setFileFilter( new FileNameExtensionFilter( filterTextDescrs[0], exts[0] ) );
        	  for( int i = 1 ; i < exts.length ; i++ ){
        		  filechooser.addChoosableFileFilter( new FileNameExtensionFilter( filterTextDescrs[i], exts[i] ) );
        	  }
          }
          if( filechooser.showOpenDialog( parent ) != javax.swing.JFileChooser.APPROVE_OPTION )
              return null;

          return filechooser.getSelectedFile();
      }

      /**
       * show a FileChooser Dialog with given arguments.
       * @param parent the parent component (location to center the dialog), may be <code>null</code>
       * @param path the path to open the FileChooser
       * @param title the fileChooser title
       * @param fileSelectionMode may be JFileChooser.FILES_ONLY,
       * JFileChooser.FILES_AND_DIRECTORIES or JFileChooser.DIRECTORIES_ONLY
       * @param extensions and array of {@link FileNameExtensionFilter} objects to filter the possible files, may be <code>null</code>
       * @return File or null, if the user cancels the dialog
       */
      public static File getFileFromAFileChooser( Component parent, String path, String title, int fileSelectionMode,
                                                  FileNameExtensionFilter[] extensions )
      {
    	  return getFileFromAFileChooser( parent, path, title, fileSelectionMode, extensions, JFileChooser.OPEN_DIALOG );
      }

      /**
       * show a FileChooser Dialog with given arguments.
       * @param parent the parent component (location to center the dialog), may be <code>null</code>
       * @param path the path to open the FileChooser
       * @param title the fileChooser title
       * @param fileSelectionMode may be JFileChooser.FILES_ONLY,
       * JFileChooser.FILES_AND_DIRECTORIES or JFileChooser.DIRECTORIES_ONLY
       * @param extensions and array of {@link FileNameExtensionFilter} objects to filter the possible files, may be <code>null</code>
       * @param mode save or open mode (may be {@link JFileChooser#SAVE_DIALOG} or {@link JFileChooser#OPEN_DIALOG})
       * @return File or null, if the user cancels the dialog
       */
      public static File getFileFromAFileChooser( Component parent, String path, String title, int fileSelectionMode,
                                                  FileNameExtensionFilter[] extensions, int mode )
      {
    	  final JFileChooser filechooser = new JFileChooser( path == null ?
    			  System.getProperty( "user.dir" ) : path );
    	  filechooser.setFileSelectionMode( fileSelectionMode );
    	  if( title != null ) filechooser.setDialogTitle(title);

    	  final FileNameExtensionFilter[] exts = extensions;
    	  if( exts != null ){
    		  if( exts[0] != null ) filechooser.setFileFilter( exts[0] );
    		  for( int i = 1 ; i < exts.length ; i++ ){
    			  if( exts[i] != null )  filechooser.addChoosableFileFilter( exts[i] );
    		  }
    	  }
    	  if ( mode == JFileChooser.OPEN_DIALOG ) {
    		  if( filechooser.showOpenDialog( parent ) != javax.swing.JFileChooser.APPROVE_OPTION )
    			  return null;

    	  }
    	  else if ( mode == JFileChooser.SAVE_DIALOG ) {
    		  if( filechooser.showSaveDialog( parent ) != javax.swing.JFileChooser.APPROVE_OPTION  )
    			  return null;

    		  for( int i = 0 ; i < exts.length ; i++ ){
    			  if( !exts[i].accept( filechooser.getSelectedFile() )) return null;
    		  }
    	  }
    	  else {
    		  return null;
    	  }

    	  return filechooser.getSelectedFile();
      }

      /**
       *
       * @param comp Component for Mediatracker initialization
       * @param buttons an array of AbstractButtons containing the buttons
       * @param imageNames the corresponding image names
       * @param url the URL where the images are located
       * @param scaleFactor the scale factor in resize the images
       */
      public static void loadButtonImages( Component comp, AbstractButton[] buttons,
                                           URL[] imageUrls, int scaleFactor ){
          //          final int IMG_SCALE = 33;
          if( buttons == null   ||   imageUrls == null   ||  comp == null  ){
              AppLogger.error( "All arguments must be set (must not be null)!"
            		  + "\n buttons:    " + ( buttons == null ? "not set" : " set" )
            		  + "\n imageNames: " + ( imageUrls == null ? "not set" : " set" )
            		  + "\n component:  " + ( comp == null ? "not set" : " set" )  );
              return;
          }
          final MediaTracker tracker = new MediaTracker( comp );
          final int numOfImgsToSet = ( imageUrls.length > buttons.length ? buttons.length : imageUrls.length );
          final Image[] images = new Image[ numOfImgsToSet ];
          try{
              for( int i = 0; i < numOfImgsToSet; i++ ){
                  //            url = WorldMapControlPanel.class.getResource(imageNames[i]);
                  //            url = getClass().getResource( imageNames[ i ] );
                  images[ i ] = Toolkit.getDefaultToolkit().getImage( imageUrls[i] );
                  tracker.addImage( images[ i ], i );
              }
              tracker.waitForAll();
              for( int i = 0; i < numOfImgsToSet; i++ ){
                  buttons[ i ].setIcon( new ImageIcon( images[ i ].getScaledInstance( scaleFactor, scaleFactor, Image.SCALE_SMOOTH ) ) );
                  buttons[ i ].setText( null );
              }
          }
          catch( final InterruptedException exc1 ){
        	  AppLogger.error( "Error loading button icons! \nReason:" + exc1.getMessage(), null );
          }
          catch( final Exception ex ){
        	  AppLogger.error( "Error loading button icons! \nReason:" + ex.getMessage(), null );
          }
      } //  loadButtonImages()


      /**
       * Saves the given 2 dimensional double array into the given File.
       *
       * @param file the given file where the array should get saved
       * @param append true if the double array should appended to the given file, false otherwise
       * @param da the double array which should get saved
       * @param seperator the seperator between the double values of the array
       */
      public static void save2DimDoubleArray( File file, boolean append, double[][] da, String seperator ) throws IOException {

    	  BufferedWriter bw = new BufferedWriter( new FileWriter( file, append ) );

    	  save2DimDoubleArray( bw, da, seperator );

    	  bw.close();
      }

      /**
       * Saves the given 2 dimensional double array into the given writer. Note: the writer will not be closed!
       *
       * @param writer the given writer where the array should get saved
       * @param da the double array which should get saved
       * @param seperator the seperator between the double values of the array
       */
      public static void save2DimDoubleArray( BufferedWriter bw, double[][] da, String seperator ) throws IOException {

			for( int i = 0 ; i < da[0].length ; i++ ){
				for( int j = 0; j < da.length; j++ ) {
					bw.write( da[j][i] + seperator );
				}
				bw.newLine();
			}

			bw.flush();

      }

      /**
       * get a simple file name extension (e.g. "abc.txt" leads to "txt")
       * @param filename the file name
       * @return the extension without leading point or <code><b>null</b></code> when no extension is found (e.g. no last dot exists)
       */
      public static String getFileNameExtension( String filename ) {
    	  if( filename == null  ||  filename.length() == 0 ) return null;
    	  int lastPos = filename.lastIndexOf( '.' );
    	  if( lastPos == -1 ) return null;
    	  return filename.substring( lastPos+1 );
      }

  	/**
  	 * print the keys of a given HashMap
  	 * @param hashMap the HashMap
  	 */
  	public static void printHashMapKeys( HashMap<?, ?> hashMap ) {
  		Set<Object> set = (Set<Object>)hashMap.keySet();
  		int ii = 0;
  		for( Object keyObject: set ){
  			System.out.println( "Key(" + ( ii++ ) + "): " + keyObject );
  		}
  	}

  	/**
  	 * print the Values of a given HashMap
  	 * @param hashMap the HashMap
  	 */
  	public static void printHashMapValues( HashMap<?, ?> hashMap ) {
  		Collection<Object> col = (Collection<Object>)hashMap.values();
  		int ii = 0;
  		for( Object valObject: col ){
  			System.out.println( "Value(" + ( ii++ ) + "): " + valObject );
  		}
  	}
  	/**
  	 * print the Contents of a given HashMap (Keys and Values)
  	 * @param hashMap the HashMap
  	 */
  	public static void printHashMapContents( HashMap<?, ?> hashMap ) {
  		Set<Object> set = (Set<Object>)hashMap.keySet();
  		int ii = 0;
  		for( Object keyObject: set ){
  			System.out.println( "Key(" + ( ii++ ) + "): " + keyObject + "\tValue: " + hashMap.get( keyObject ));
  		}
  	}

  	/**
  	 * print the Contents of a given Array 
  	 * @param hashMap the HashMap
  	 */
  	public static void printArrayContents( Object[] array ) {
  		if( array != null ) {
  			int ii = 0;
  			for( Object object: array ){
  				System.out.println( "(" + ( ii++ ) + "): " + object.toString() );
				System.out.println(  );
			}
  		}
  	}
  	

} // IOUtilities()
