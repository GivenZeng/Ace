import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Mult extends Thread {
	
	public static final int port = 8080;
	// 数据库的参数
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/trafficstream?characterEncoding=utf8&useSSL=true";
	private static final String user = "root";
	private static final String password = "244143261";
	
	private  Statement statement = null;
	public  String[] carMessageAndTime = new String[10];
     private  Socket socket;
     private BufferedReader in;
     private PrintWriter out;
     private  Connection conn = null;
     private String queryType;
     public Mult(Socket s)
     {
    	 socket=s;
    	 try {
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			System.out.println("create a mult thread");
			start();//调用run
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
     }
     
     @Override
    public void run() {
    	// TODO Auto-generated method stub
    	super.run();
    	if(!connectToMysql())  //连接数据库
    	  return;
    	getMessageFromRequest();
    	queryAccordingToQueryType();
    	close();    //关闭数据库连接
    	try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
     
     private void close() {
		// TODO Auto-generated method stub
    	 try {
    		statement.close();
 			conn.close();
 			System.out.println("关闭statement连接");
 			System.out.println();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
	}

	private void getMessageFromRequest()
     {
    	 try {
			queryType=in.readLine();       //获取查询类型
			System.out.println(queryType);
			int num = 0;
			String temp;
			while ((temp=in.readLine())!=null) {  //获取查询所用参数：carnum、time范围、latitude范围、longitude范围
				if (temp.equals("end")) break;     //当传来end时，参数传输完成
				carMessageAndTime[num] = temp;
				System.out.println(temp);
				num++;
			}
			System.out.println("get message from request secceed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	
	
     private  boolean connectToMysql() {
 		try {
 			Class.forName(driver);
 		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
 		}
 		try {
 			conn = (Connection) DriverManager.getConnection(url, user, password);
 			if (!conn.isClosed()) {
 				System.out.println("succeeded connect to mysql");
 			} else
 				System.out.println("failed to connect to mysql");
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		try {
 			statement = (Statement) conn.createStatement();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		if (statement != null)
 			return true;
 		else
 			return false;
 	}
 	 private void queryAccordingToQueryType()
 	 {
 		if (queryType.equals(Arguments.queryType_road) ) {
			System.out.println("start getRoad");
            MySqlUtil.getRoad(statement,carMessageAndTime,out);
		}
		if (queryType.equals( Arguments.queryType_carnum_at_one_space)) {
			System.out.println("start get carnum from one space");
            MySqlUtil.getCarnumFromOneSpace(statement,carMessageAndTime,out);
		}
		if (queryType .equals(Arguments.queryType_trafficStream_between_two)) {
			MySqlUtil.getCarnumBetweenTwoSpace(statement,carMessageAndTime,out);
		}
 	 }
}
