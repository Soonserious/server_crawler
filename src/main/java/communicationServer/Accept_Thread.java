package communicationServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Level;

import kr.ac.cbnu.ubigame.analyser.common.AnalyserLogger;

public class Accept_Thread extends Thread{
	private ArrayList<CheckList> cl;
	private ServerSocket serverSocket;
	private AnalyserLogger analyserLogger;
	
	/**
	 * 
	 * @param connection <String,String> 형태의 Hash, Accept_Dispose 클래스와 Send_Thread 클래스와 공유, 클라이언트의 상태를 저장
	 * @param cl <CheckList>형태의 배열, Accept_Dispose와 공유, 수신된 CheckList를 저장
	 * @param port ServerPC에서 사용할 포트
	 * @throws Exception 
	 */
	
	public Accept_Thread(HashMap<String,String> connection, ArrayList<CheckList> cl, int port) throws Exception{
		this.cl = cl;
		this.analyserLogger = new AnalyserLogger();
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			this.analyserLogger.setLoggerConf("Accept_Thread_Constructor", "10240", Level.FATAL);
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(!Thread.interrupted()){
			try {
				Connection();
			} catch (Exception e) {
				
			}
		}
	} 
	
	/* 일단 받자 
	 * 받은 후 상태를 판단되는 부분은 이부분과 독립해서 구현한다
	 * 다시 받자
	 */
	
	
	public void Connection() throws Exception{
		InputStream is = null;
		ObjectInputStream in =null;
		Socket socket = null;	
		try {		
			socket = serverSocket.accept();
			socket.setKeepAlive(true);
			is = socket.getInputStream();
			in = new ObjectInputStream(is);
			SetCheckList(in);
		} catch (IOException e) {
			this.analyserLogger.setLoggerConf("Accept_Thread_Connection_IO", "10240", Level.ERROR);
		}finally{
			try {
					if(in != null){
					is.close();
					in.close();
					socket.close();
					}
			} catch (IOException e) {
				this.analyserLogger.setLoggerConf("Accept_Thread_Connection_Finally", "10240", Level.ERROR);
			}
		}
		
			
	}
	
	public void SetCheckList(ObjectInputStream in) throws Exception{
		CheckList temp = null;
		try {
			temp = (CheckList)in.readObject();
			System.out.println("temp 생성");
		} catch (ClassNotFoundException e) {
			this.analyserLogger.setLoggerConf("Accept_Thread_SetCheckList", "10240", Level.FATAL);
		} catch (IOException e) {
			this.analyserLogger.setLoggerConf("Accept_Thread_SetCheckList", "10240", Level.FATAL);
		}
		this.cl.add(temp);
	}
	
	
	
}
