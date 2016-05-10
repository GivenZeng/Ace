import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class MySqlUtil {
	private static final String select = " select ";
	private static final String from_data_where = " from data where ";
	private static final String time=" time ";
	private static final String latitude=" latitude ";
	private static final String longitude="longitude";
	private static final String between=" between ";
	private static final String and=" and ";
	
	public static void getRoad(Statement statement, String[] carMessageAndTime, PrintWriter writer) {
		// TODO Auto-generated method stub
		int i=0;
		String sql=select+" latitude,"+" longitude "+from_data_where+" carnum= "
				+carMessageAndTime[i++]+" and time between "+"'"+carMessageAndTime[i++]
						+"'"+" and " +"'"+carMessageAndTime[i++]+"'";//��ѯ���
		System.out.println(sql);
		ResultSet results;
		try {
			results = statement.executeQuery(sql);
			double coor=0.0;
			while(results.next())
			{
				coor=results.getDouble("latitude");
				//System.out.print(coor+"  ");
			   writer.println(coor);  //�����ݷ��ظ��ͻ���
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
//				while(results.next())
//				{
//					writer.println(String.valueOf(results.getInt(1)));
//					
//				}
				// int rowNum=results.getRow(); //ָ����������ͨ�����������ȡ���м�¼�� 
				 //results.last();  //��ָ���ƶ��������  
				// System.out.println("һ����"+rowNum+"��");
				//System.out.println(results);
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




	public static void getCarnumBetweenTwoSpace(Statement statement, String[] carMessageAndTime, PrintWriter out) {
		// TODO Auto-generated method stub
		
	}


}