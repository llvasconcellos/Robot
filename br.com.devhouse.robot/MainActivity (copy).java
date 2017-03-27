package br.com.devhouse.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView t = new TextView(this); 
        
        t = (TextView)findViewById(R.id.textView);
        //t.setText("texto=");
        
        //AndroidDebugBridge bridge = AndroidDebugBridge.createBridge(adbLocation, true);
        
        AndroidDebugBridge.init(false);
        
        
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge();
        //AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("/system/bin/adb", true);
        
        t.setText("texto=" + AndroidDebugBridge.getSocketAddress().toString());
        
        
        
        
        
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
            try {
                Thread.sleep(100);
                count++;
            } catch (InterruptedException e) {
                // pass
            }

            // let's not wait > 10 sec.
            if (count > 100) {
            	t.setText("Timeout getting device list!" + bridge.getDevices().length);
                return;
            }
        }

        // now get the devices
        IDevice[] devices = bridge.getDevices();

        if (devices.length == 0) {
        	t.setText("Step One: blast egg");
        }
        else{
        	for (IDevice d : devices) {
        		RawImage rawImage;

                try {

                	rawImage = d.getScreenshot();
                    
                    
                    String filename = "leonardo.raw";
                    File file = new File(Environment.getExternalStorageDirectory(), filename);
                    FileOutputStream fos;
                    //byte[] data = new String("data to write to file").getBytes();

                    fos = new FileOutputStream(file);
                    fos.write(rawImage.data);
                    fos.flush();
                    fos.close();

                }
                catch (FileNotFoundException fnf) {
                	t.setText("File not found: " + fnf.getMessage());
                }
                catch (IOException ioe) {
                	t.setText("Unable to get frame buffer: " + ioe.getMessage());
                    return;
                } catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AdbCommandRejectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

        
        
    }
}