package net.srcz.android.screencast.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

	static boolean debug = false;
    int port;
    
	public Main(int port, boolean debug) {
		this.port = port;
		this.debug = debug;
	}
	
	public void execute() throws IOException {
	    ServerSocket ss = new ServerSocket(port);
	    while(true) {
            final Socket s = ss.accept();
            if(s == null || ss.isClosed())
            	break;
            if(debug)
            	System.out.println("New client ! ");
            Thread t = new Thread() {
            	public void run() {
            		try {
            			new ClientHandler(s);
            		} catch(Exception ex) {
            			ex.printStackTrace();
            		}
            		try {
            			s.close();
            		} catch(Exception ex) {
            			// ignor�
            		}
            	}
            };
            t.start();
            /*
            Thread t2 = new Thread() {
            	public void run() {
            		try {
            			File f = new File("/dev/graphics/fb0");
            			FileInputStream fis = new FileInputStream(f);
            			byte[] data = new byte[16];
            			OutputStream os = s.getOutputStream();
            			while(true) {
            				int val = fis.read(data);
            				if(val <= 0) {
            					sleep(0);
            					continue;
            				}
            				os.write(data,0,val);
            				os.flush();
            			}
            		} catch(Exception ex) {
            			ex.printStackTrace();
            		}
            	}
            };
            t2.start();
            */
        }
		
	    
	}
	


	
    public static void main(String[] args) {
		System.out.println("[agent] Starting ...");

		try {
    		if(args.length == 0)
    			throw new RuntimeException("Need >= 1 param");
    	    int port = Integer.parseInt(args[0]);
    	    boolean debug = args.length >= 2 && args[1].equals("debug");
    	    new Main(port, debug).execute();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

    /*
    public static void keystroke2(int keycode) throws RemoteException {
    
		import android.app.Instrumentation;
		Instrumentation ins = new Instrumentation();
		ins.sendKeyDownUpSync(keycode);
    }
    */
    

}
