

import java.io.IOException;
import java.net.InetSocketAddress;


import com.sun.net.httpserver.HttpServer;

public class WebServer implements Runnable {
	public WebServer(int port)
	{
		shutdownFinished = false;

        try {
    		System.out.println("LIVE Webserver running on port " + port);
    		server = HttpServer.create(new InetSocketAddress(port), 0);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //server.createContext("/search", new bbServeMessageHandler(bbServeInstance,db_connection,mReporter));
        server.createContext("/", new EadCalcMessageHandler());
        server.setExecutor(null); // creates a default executor
        
    }
	
	
	public void run()
    {
		server.start();
		System.out.println("Webserver running");
    }
	
	Boolean shuttingDown;
	Boolean shutdownFinished;
	
	public void ShutDown()
    {
		System.out.println("stopping WebServer!");
    	
    	try
    	{
    		server.stop(5);	//allow 5 seconds to stop
    		
    		System.out.println("stopped WebServer!");
    		shutdownFinished = true;
    	}
    	catch (Exception ex)
    	{
    		System.out.println("failed to stop WebServer!");
    	}
    	shuttingDown = true;
    	
    }
    
    public Boolean ShutDownFinished()
    {
    	return shutdownFinished;
    }
	
	HttpServer server;

}
