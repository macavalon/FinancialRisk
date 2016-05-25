

import java.util.ArrayList;


public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8085;
		if (args.length >= 1 ) {
		    try {
		    	port = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
		        System.err.println("Port" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		}
		
		ArrayList<Runnable> Tasks = new ArrayList<Runnable>();

		final WebServer webServer = new WebServer(port);
		Tasks.add(webServer);
        
        ArrayList<Thread> threadpool = new ArrayList<Thread>();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() 
            {    
                 System.out.println("Inside Add Shutdown Hook : " + Thread.currentThread().getName()) ;

            	 
            	//stop webServer
            	 webServer.ShutDown();
                	 
            	 while(!webServer.ShutDownFinished())
            	 {
            		 try {
     					Thread.sleep(1000);
     					System.out.print(".");
     				} catch (InterruptedException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				}
            	 }
            	 System.out.println("Finished Shutting down webServer");

            	System.out.println("Tasks exit");
             }
           });

        System.out.println("Added Shutting down hook");
        

        // create all threads
        for (Runnable task : Tasks)
        {
            Thread thread = new Thread(task);
            threadpool.add(thread);
            thread.start();
        }

        // join all threads
        for (Thread thread : threadpool)
        {
            try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        threadpool.clear();

	}

}
