package communicationServer;

import java.util.ArrayList;
import java.util.HashMap;
import kr.ac.cbnu.ubigame.analyser.common.AnalyserLogger;
import servercrawler.ServerCrawler;
import org.apache.log4j.Level;

public class Server extends Thread{
	private Accept_Thread accept_main;
	private Accept_Thread accept_extra;
	private HashMap<String, String> connection;
	private ArrayList<CheckList> receivecl;
	private ArrayList<CheckList> sendcl;
	private Accept_Dispose dispose;
	private Send_Thread send;
	private int step;
	private AnalyserLogger analyserLogger;
	
	/**
	 * Send_Thread, Accept_Dispose, Accept_Thread와 연관짓고 있으며 통신에 관한 모든것을 총괄한다
	 * @param sendcl
	 * @param step
	 * @throws Exception 
	 */
	
	public Server(ArrayList<CheckList> sendcl, int step) throws Exception{
		this.sendcl = sendcl;
		this.step = step;
		this.connection = new HashMap<String,String>();
		this.receivecl = new ArrayList<CheckList>();
		accept_main = new Accept_Thread(this.connection,this.receivecl,3082);
		accept_extra = new Accept_Thread(this.connection,this.receivecl,3083);
		send = new Send_Thread(this.connection,this.sendcl,this.step);
		dispose = new Accept_Dispose(this.receivecl,this.connection);
		this.analyserLogger = new AnalyserLogger();
	}
	
	public void run(){
		this.accept_main.start();
		this.accept_extra.start();
		this.send.start();
		this.dispose.start();
			
		try {
			send.join();
			accept_main.interrupt();
			accept_extra.interrupt();
			dispose.interrupt();
		} catch (InterruptedException e) {
			try {
				this.analyserLogger.setLoggerConf("Threadjoinfail", "10240", Level.FATAL);
			} catch (Exception e1) {
					e1.printStackTrace();
			}
		}
		
	}
	
	
}
