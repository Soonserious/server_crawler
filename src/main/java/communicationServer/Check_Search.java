package communicationServer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.log4j.Level;

import kr.ac.cbnu.ubigame.analyser.common.AnalyserLogger;
/**
 * 현재 검색엔진에서 검색이 불가한 Client에게 자신의 상태를 확인해 보라는 메세지를 전송하는 클래스
 * @author user
 *
 */

public class Check_Search extends Thread{
	/**
	 * Connection은 <String,String>형태의 해쉬맵, Accept_Thread와, Accept_Dispose, Send_Thread와 연동된다
	 */
	private HashMap<String,String> connection;
	private Address address;
	private CheckList cl;
	private AnalyserLogger analyserLogger;
	public Check_Search(HashMap<String, String> connection){
		this.connection = connection;
		this.analyserLogger = new AnalyserLogger();
	}
	
	/**
	 * 프로그램을 종료되기 전 까지 Check_Search를 실행
	 */
	
	public void run(){
		while(!Thread.interrupted()){
			if(connection.containsValue("2")){
				Check_Searchmain();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 현재 검색엔진에서 결과값을 못 얻고 있는 Client를 차즌다
	 */
	private void Check_Searchmain() {
		for(Address object: Address.values()){
			this.address = object;
			if(connection.get(address.GetIP()) == "2"){
				System.out.println(this.address.GetIP() + "로 죽었는지 확인메세지 요청");
				this.cl = new CheckList(2);
				SocketConnection();
				this.address = null;
			}
			
		}
	}
	/**
	 * Client와 연결요청
	 */
	public void SocketConnection(){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			 Socket socket = new Socket(this.address.GetIP(), this.address.Getport());
			 oos = new ObjectOutputStream(socket.getOutputStream());
             oos.writeObject(this.cl);
             oos.close();
             socket.close();
		} catch (UnknownHostException e) {
			
		} catch (IOException e) {
			
		}
		
	}

}
