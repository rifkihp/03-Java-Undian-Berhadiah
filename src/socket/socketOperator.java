/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import undian.operator;

public class socketOperator implements Runnable {
    Thread t;

    private ServerSocket server;
    private operator oprtr;

    public socketOperator(int port, operator oprtr) {
        this.oprtr = oprtr;

        try {
            System.out.println("Cek port "+port+"...");
            server = new ServerSocket(port);

            t = new Thread(this, "Thread hash and rnd...");
            t.start();
            oprtr.showOperator();

        } catch (IOException ex) {
            System.out.println("Error! Port "+port+" sudah terpakai.");
            JOptionPane.showMessageDialog(oprtr, "Port "+port+" sudah terpakai.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            //Logger.getLogger(socketOperator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        while(true) {
            try {
                
                Socket socket = server.accept();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                JSONObject message = new JSONObject((String) ois.readObject());
                String status =  message.getString("status");
                JSONArray data_pemenang = message.getJSONArray("pemenang");
                
                boolean saved_pemenang = false;
                if(data_pemenang.length()>0) {
                    oprtr.insertPemenang(data_pemenang);
                    oprtr.listOfPemenang();
                    oprtr.loadDataPemenang();
                    saved_pemenang = true;
                    //oprtr.session = UUID.randomUUID().toString();
                }
                
                //System.out.println(status);
                if(status.equalsIgnoreCase("update_logo")) {
                    FileInputStream fis = null;
                    BufferedInputStream bis = null;
                    OutputStream os = null;
                    try {
                        File myFile = new File (oprtr.file_logo);
                        byte [] mybytearray  = new byte [(int)myFile.length()];
                        fis = new FileInputStream(myFile);
                        bis = new BufferedInputStream(fis);
                        bis.read(mybytearray,0,mybytearray.length);
                        os = socket.getOutputStream();

                        os.write(mybytearray,0,mybytearray.length);
                        os.flush();
                    }
                    finally {
                      if (bis != null) bis.close();
                      if (os != null) os.close();

                    }
                } else {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(oprtr.getJsonDataFormat(saved_pemenang));
                    
                    oos.close();
                }

                socket.close();

            } catch (JSONException ex) {
                Logger.getLogger(socketOperator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(socketOperator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(socketOperator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
