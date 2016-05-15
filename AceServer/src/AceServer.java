import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.SynchronousQueue;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class AceServer {
	public static final int port = 8080;
	public static void main(String[] args) throws IOException  {
		
		ServerSocket server=new ServerSocket(port);
		System.out.println("server start");
	    try
	    {
	    	while(true)
			{
				Socket socket = server.accept();
				System.out.println("get connect from ace");
				new Mult(socket);
			}

	    }
	    finally
	    {
	    	server.close();
	    }
	}

	
}	
	
	
