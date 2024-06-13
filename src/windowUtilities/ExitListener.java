/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package windowUtilities;

import java.awt.event.*;

public class ExitListener extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
}