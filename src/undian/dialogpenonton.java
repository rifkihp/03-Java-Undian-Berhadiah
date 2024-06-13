/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package undian;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Kiezie
 */
public class dialogpenonton implements Runnable {
Thread t;
    public JDialog dlg;
    /** Creates new form waitingdialog */
    public dialogpenonton(JFrame parent, JPanel panel) {
        
        dlg = new JDialog(parent);
        dlg.setSize(parent.getWidth(), parent.getHeight());
        dlg.add(panel);
        t = new Thread(this, "Thread hash and rnd...");
        t.start();
    }

     public void run() {
        dlg.setLocationRelativeTo(null);
        dlg.setModal(true);
        dlg.setUndecorated(true);
        dlg.setVisible(true);
     }

}
