package br.com.devhouse.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

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
	
	private TextView t;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        t = new TextView(this); 
        t = (TextView)findViewById(R.id.textView);
        
        restartADBDaemon();

    }
    
    private void L(String message){
    	t.setText(message);
    }
    
    private boolean restartADBDaemon(){
    	/*property_set("service.adb.tcp.port", "5555");
	    system("setprop service.adb.tcp.port 5555");
	    system("adbd");*/
    	
    	
    	Map<String, String> map = System.getenv();
    	for (Entry<String, String> entry : map.entrySet()) {
    		t.append("Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n");
    	}
    	
    	return true;
    	
    }
}