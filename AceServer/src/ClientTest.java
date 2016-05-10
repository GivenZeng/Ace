import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress address=null;
        try {
		  address=InetAddress.getByName("125.216.246.211");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Socket socket=null;
        try {
			socket=new Socket(address, 8080);
			System.out.println("socket="+socket);
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer=new PrintWriter(new BufferedWriter
					(new OutputStreamWriter(socket.getOutputStream())),true);
			writer.print(Arguments.queryType_road);   
			writer.println("6565");
			writer.println("2008-02-03");
			writer.println("2008-02-04");
			writer.println("end");       //传查询参数给服务端
			while(true)
			{
				String latitude=reader.readLine();
				if(latitude.equals("end")) 
					{
					 System.out.println("end");
					 break;
					}
				  System.out.println(latitude+" " +reader.readLine());
			}
			System.out.println("client over");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
			socket.close();
		}
        
	}

}
