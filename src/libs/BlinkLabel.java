/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class BlinkLabel extends JLabel {
    private Color bg;
    private Color fg;

    private static final long serialVersionUID = 1L;

    private static final int BLINKING_RATE = 1000; // in ms

    private boolean blinkingOn = true;

  public BlinkLabel() {
    Timer timer = new Timer( BLINKING_RATE , new TimerListener(this));
    timer.setInitialDelay(0);
    timer.start();
  }

  public void setColor(Color fg, Color bg) {
      this.fg = fg;
      this.bg = bg;
  }

  public void setBlinking(boolean flag) {
    this.blinkingOn = flag;
  }
  public boolean getBlinking(boolean flag) {
    return this.blinkingOn;
  }


  private class TimerListener implements ActionListener {
    private BlinkLabel bl;
    
    private boolean isForeground = true;

    public TimerListener(BlinkLabel bl) {
      this.bl = bl;
      fg = bl.getForeground();
      bg = bl.getBackground();
    }

    public void actionPerformed(ActionEvent e) {
      if (bl.blinkingOn) {
        if (isForeground) {
          bl.setForeground(fg);
        }
        else {
          bl.setForeground(bg);
        }
        isForeground = !isForeground;
      }
      else {
        // here we want to make sure that the label is visible
        // if the blinking is off.
        if (isForeground) {
          bl.setForeground(fg);
          isForeground = false;
        }
      }
    }

  }

  // --- for testing
//  private static void createAndShowUI() {
//    JFrame frame = new JFrame("BlinkLabel");
//    final BlinkLabel bl = new BlinkLabel("I'm blinking!");
//
//    frame.getContentPane().setLayout(new java.awt.FlowLayout());
//    frame.getContentPane().add(bl);
//
//    JButton b = new JButton("toogle blink");
//    b.addActionListener(
//        new ActionListener() {
//          public void actionPerformed(ActionEvent ae) {
//            bl.blinkingOn = !bl.blinkingOn;
//          }
//        });
//    frame.getContentPane().add(b);
//
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.pack();
//    frame.setLocationRelativeTo(null);
//    frame.setVisible(true);
//
//  }
//
//  public static void main(String[] args)  {
//    java.awt.EventQueue.invokeLater(new Runnable(){
//      public void run(){
//        createAndShowUI();
//      }
//    });
//  }
  // ---
}