/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package undian;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import windowUtilities.WindowUtilities;

/**
 *
 * @author Kiezie
 */
public class Main {

    public static void main(String args[]) {
        WindowUtilities.setNativeLookAndFeel();
        showOnScreen( 0, new operator());

        //System.out.println(cekScreenLength());
        if(cekScreenLength()>1) new penonton(1);
    }

    public static void showOnScreen( int screen, JFrame frame ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        if( screen > -1 && screen < gs.length ) {
            gs[screen].setFullScreenWindow( frame );
        } else if( gs.length > 0 ) {
            gs[0].setFullScreenWindow( frame );
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }

    public static int cekScreenLength() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        return gs.length;
    }

}
