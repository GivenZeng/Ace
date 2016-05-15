import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

public class MySqlUtil {
	private static final String select = " select ";
	private static final String from_data_where = " from data where ";
	private static final String time=" time ";
	private static final String latitude=" latitude ";
	private static final String longitude="longitude";
	private static final String between=" between ";
	private static final String and=" and ";
	private static final String select_count="select count(distinct first.carnum) from (select carnum from data where time between '";
	private static final String as_first=" ) as first, (select carnum from data where time between '";
	private static final String as_second=" ) as second where first.carnum=second.carnum";
	
	public static void getRoad(Statement statement, String[] carMessageAndTime, PrintWriter writer) {
		// TODO Auto-generated method stub
		int i=0;
		String sql=select+" latitude,"+" longitude "+from_data_where+" carnum= "
				+carMessageAndTime[i++]+" and time between "+"'"+carMessageAndTime[i++]
						+"'"+" and " +"'"+carMessageAndTime[i++]+"'";//查询语句
		System.out.println(sql);
		ResultSet results;
		try {
			results = statement.executeQuery(sql);
			double coor=0.0;
			while(results.next())
			{
				coor=results.getDouble("latitude");
				//System.out.print(coor+"  ");
			   writer.println(coor);  //将数据返回给客户端
			   coor=results.getDouble("longitude");
			   writer.println(coor);
			   //System.out.println(coor);
			}
			writer.println("end");
			System.out.println("end");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void getCarnumFromOneSpace(Statement statement, String[] carMessageAndTime, PrintWriter writer) {
		// TODO Auto-generated method stub
		   int i=0;
	        String sql=select+"count(distinct carnum)"+" carnum "+from_data_where+time+between+" ' "+carMessageAndTime[i++]+" ' "+and 
	        		+" ' "+carMessageAndTime[i++]+" ' "+and+latitude+between+carMessageAndTime[i++]+and+carMessageAndTime[i++]
	        		+and+longitude+between+carMessageAndTime[i++]+and +carMessageAndTime[i++];
	        System.out.println(sql);
	        ResultSet results;
	        try {
				results=statement.executeQuery(sql);
				 results.first();
				 int carnum=results.getInt("carnum");
				System.out.println(String.valueOf(carnum));
				writer.println(String.valueOf(carnum));
				writer.println("end");
				System.out.println("end");
				writer.flush();
				writer.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
        



	public static void getCarnumBetweenTwoSpace(Statement statement, String[] carMessageAndTime, PrintWriter writer) {
		// TODO Auto-generated method stub
		List<String> times=new ArrayList<String>();
		DateFormat df=DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		Date firstDate=null,secondDate=null,tempDate=null;
		try {
			 firstDate=df.parse(carMessageAndTime[0]);
			 secondDate=df.parse(carMessageAndTime[1]);
			 tempDate=df.parse(carMessageAndTime[0]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String frag1="' and latitude between "
				+carMessageAndTime[2]+" and "+carMessageAndTime[3]+" and longitude between "
				+carMessageAndTime[4]+" and "+carMessageAndTime[5]
			    +as_first;
		String frag2="' and latitude between "
			    +carMessageAndTime[6]+" and "+carMessageAndTime[7]+" and longitude between "
			    +carMessageAndTime[8]+" and "+carMessageAndTime[9]
			    +as_second;
		
	
//		String sql=select_count
//				+carMessageAndTime[0]+"' and '"+carMessageAndTime[1]+"' and latitude between "
//				+carMessageAndTime[2]+" and "+carMessageAndTime[3]+" and longitude between "
//				+carMessageAndTime[4]+" and "+carMessageAndTime[5]
//			    +as_first
//			    +carMessageAndTime[0]+"' and '"+carMessageAndTime[1]+"' and latitude between "
//			    +carMessageAndTime[6]+" and "+carMessageAndTime[7]+" and longitude between "
//			    +carMessageAndTime[8]+" and "+carMessageAndTime[9]
//			    +as_second;
		ResultSet results;
		while(firstDate.before(secondDate))
		{
			tempDate.setHours(tempDate.getHours()+1);
			String time=df.format(firstDate)+"' and '"+df.format(tempDate);
			String sql=select_count+time+frag1+time+frag2;
			try {
				System.out.println(sql);
				results = statement.executeQuery(sql);
				results.first();
				writer.println("first time");
				writer.println(results.getInt("count(distinct first.carnum)"));
				System.out.println("first time  "+String.valueOf(results.getInt("count(distinct first.carnum)")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			firstDate.setHours(firstDate.getHours()+1);;
		}
		writer.println("end");
		System.out.println("end");
	}


}