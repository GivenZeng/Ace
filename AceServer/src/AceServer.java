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
	//存储信息：车号，时间范围，经纬度范围
//	public static String[] carMessageAndTime = new String[6];
	
	// 数据库的参数
//	private static final String driver = "com.mysql.jdbc.Driver";
//	private static final String url = "jdbc:mysql://localhost:3306/trafficstream?characterEncoding=utf8&useSSL=true";
//	private static final String user = "root";
//	private static final String password = "244143261";
//	private static Connection conn = null;
//	private static final String queryString1 = " select ";
//	private static final String queryString2 = " from data where ";
//	private static Statement statement = null;
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
	
	
//	private static void query(int queryType, PrintWriter writer) {
//		// TODO Auto-generated method stub
//		
//		if (queryType == Arguments.queryType_road_ASCII) {
//			System.out.println("start getRoad");
//            getRoad(writer);
//		}
//		if (queryType == Arguments.queryType_trafficStream_at_one) {
//
//		}
//		if (queryType == Arguments.queryType_trafficStream_between_two) {
//
//		}
//	}
//
//	private static boolean connectToMysql() {
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			conn = (Connection) DriverManager.getConnection(url, user, password);
//			if (!conn.isClosed()) {
//				System.out.println("succeeded connect");
//			} else
//				System.out.println("failed to connect");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement = (Statement) conn.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (statement != null)
//			return true;
//		else
//			return false;
//	}
//	 
//	private static void getRoad(PrintWriter writer)
//	{
//		int i=0;
//		String sql=queryString1+" latitude,"+" longitude "+queryString2+" carnum= "
//				+carMessageAndTime[i++]+" and time between "+"'"+carMessageAndTime[i++]
//						+"'"+" and " +"'"+carMessageAndTime[i++]+"'";
//		System.out.println(sql);
//		ResultSet results;
//		try {
//			results = statement.executeQuery(sql);
//			double coor=0.0;
//			while(results.next())
//			{
//				coor=results.getDouble("latitude");
//				//System.out.print(coor+"  ");
//			   writer.println(coor);
//			   coor=results.getDouble("longitude");
//			   writer.println(coor);
//			   //System.out.println(coor);
//			}
//			writer.println("end");
//			System.out.println("end");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//   private static void getCarNumInOneSpace()
//   {
//	   String sql=queryString1+" count(*) "+queryString2+"time between "
//				+carMessageAndTime[0]+" and "+carMessageAndTime[1]+" and latitude between "
//				+carMessageAndTime[2]+" and "+carMessageAndTime[3]+" and longitude between "
//				+carMessageAndTime[4]+" and "+carMessageAndTime[5];
//	   System.out.println(sql);
//   }
//}
