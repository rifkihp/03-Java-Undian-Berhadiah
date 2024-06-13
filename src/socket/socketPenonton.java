/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import undian.penonton;
import undian.waitingdialog;

public class socketPenonton implements Runnable {
    Thread t;
  
    private int port = 9876;
    private String server = "";
    private penonton pntn1;
    private boolean openDialog = false;
    private waitingdialog wd = null;

    public socketPenonton(penonton pntn1) {
       
        this.pntn1 = pntn1;
        t = new Thread(this, "Thread hash and rnd...");
        t.start();
    }

    public void run() {
        
        String status = "";
        while(true) {
            if(!pntn1.opertor_ip.equalsIgnoreCase(server)) {
                openDialog = true;
                server = pntn1.opertor_ip;

                wd = new waitingdialog(pntn1);
            }
            
            try {
                Socket socket = new Socket(server, port);

                //System.out.println("status penonton: " + pntn1.status);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("{status: \""+status+"\", pemenang: ["+(pntn1.string_pemenang.length()>0?pntn1.string_pemenang:"")+"]}");
                
                if(openDialog) {
                    wd.dlg.dispose();
                    openDialog = false;
                }

                if(!pntn1.isShowing()) {
                    pntn1.dispose();
                    pntn1.showJPenonton();
                }

                if(status.equalsIgnoreCase("update_logo")) {                    
                    int bytesRead;
                    int current = 0;
                    FileOutputStream fos = null;
                    BufferedOutputStream bos = null;

                    try {
                      byte [] mybytearray  = new byte [6022386];
                      InputStream is = socket.getInputStream();
                      fos = new FileOutputStream("images/temp.jpg");
                      bos = new BufferedOutputStream(fos);
                      bytesRead = is.read(mybytearray,0,mybytearray.length);
                      current = bytesRead;

                      do {
                         bytesRead =
                            is.read(mybytearray, current, (mybytearray.length-current));
                         if(bytesRead >= 0) current += bytesRead;
                      } while(bytesRead > -1);

                      bos.write(mybytearray, 0 , current);
                      bos.flush();

                    } finally {
                      if (fos != null) fos.close();
                      if (bos != null) bos.close();
                    }
                    pntn1.loadLogo();
                    status = "";
                } else {

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String output = (String) ois.readObject();
                    JSONObject message = new JSONObject(output);
                    //System.out.println("respon operator: "+ output);
                    
                    String session = message.getString("session");
                    boolean reset_string_pemenang = message.isNull("saved_pemenang")?false:message.getBoolean("saved_pemenang");
                    if(reset_string_pemenang) {
                        pntn1.string_pemenang = "";
                    }
                    
                    if(!pntn1.session.equalsIgnoreCase(session)) {           
                        status = "update_logo";
                        pntn1.loadBattle(message);
                        pntn1.session = session;
                    }
                    
                    oos.close();
                    ois.close();
                }

                socket.close();
                Thread.sleep(100);
                
            } catch (JSONException ex) {
                System.out.println("1");
                //Logger.getLogger(socketJudge.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                System.out.println("2");
                //Logger.getLogger(socketJudge.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("3");
                //Logger.getLogger(socketJudge.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                if(error_5()) {
                    System.exit(0);
                    break;
                }
            } catch (IOException ex) {
                if(error_5()) {
                    System.exit(0);
                    break;
                }
            }
        }
    }

    private boolean error_5() {
        System.out.println("Error 5: Operator belum siap.");
        if(openDialog) {
            wd.dlg.dispose();
            openDialog = false;
        }
        Object[] options = {"Setting", "Keluar"};
        int n = JOptionPane.showOptionDialog(pntn1,
        "Tidak bisa tersambung dengan operator.",
        "Error",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.ERROR_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]); //default button title

        server = "";
        if(n==0) {
            String str = JOptionPane.showInputDialog(null, "IP Server: ", pntn1.opertor_ip);
            if(str != null) {
                pntn1.opertor_ip = str;
                pntn1.writeIp(pntn1.opertor_ip);
                return false;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
