/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public abstract class EscapableFrame extends JFrame
{
    public EscapableFrame()
    {
        // on ESC key close frame
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK), "Setting"); //$NON-NLS-1$

        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
            public void actionPerformed(ActionEvent e) {

                JOptionPane pane = new JOptionPane("Yakin ingin keluar?");
                Object[] options = new String[] { "Ya", "Tidak" };
                pane.setOptions(options);
                JDialog dialog = pane.createDialog(new JFrame(), "Keluar");
                dialog.show();
                Object obj = pane.getValue(); 
                int result = -1;
                for (int k = 0; k < options.length; k++)
                  if (options[k].equals(obj)) result = k;
    
                if(result==0) System.exit(0);

            }
        });

        getRootPane().getActionMap().put("Setting", new AbstractAction() { //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                close();
            }
        });

        // on close window the close method is called
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                close();
            }
        });
    }

    /**
     * Is called when the frame is closed by pressing ESC or closing it by
     * clicking on the close icon.
     *
     * @return True if the frame got closed; false otherwise.
     */
    abstract public boolean close();
}
