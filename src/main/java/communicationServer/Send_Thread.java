package communicationServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


/**
 * Client에 검색페이지를 전송하는 클래스
 * @author user
 *
 */

public class Send_Thread extends Thread{
	
	/**
	 * address는 Client의 아이피를 갖게되는 변수
	 * sendcl은  ServerCralwer에서 만들어진 CheckList의 배열, Client에 전송될 메세지를 저장하고 있다
	 * connection은 Accept_Thread, Accept_Dispose, Check_Search와 공유되는 HashMap, Send_Thread에서는 정상작동하는 Client에만 작업을 할당한다
	 * dead는 Integer형의 배열이며 현재 connection에서 검색불가이거나 작업중인 컴퓨터의 Address 넘버를 저장한다
	 * DEFINE은 클라이언트의 수를 저장한다
	 * step은 Server프로그램이 종료되는지를 판별하여 다른쓰레드를 종료시키는 역할을 한다
	 */
	
	private ArrayList<CheckList> sendcl;
	private HashMap<String, String> connection;
	private ArrayList<Integer> dead;
	private Check_Search check_search;
	private final int DEFINE = 4;
	private int step;
	
	/**
	 * 
	 * @param connection
	 * @param sendcl
	 * @param step
	 */
	
	public Send_Thread(HashMap<String,String>connection, ArrayList<CheckList> sendcl,int step){
		this.connection = connection;
		this.sendcl = sendcl;
		this.dead = new ArrayList<Integer>();
		this.check_search = new Check_Search(this.connection);
		this.step = step;
	}

	/**
	 * check_search를 실행시킨다
	 * step이 2이면 프로그램을 종료한다
	 * 종료되기 전까지 url을 열심히 보낸다
	 * step이 2가되면 반복문을 탈출하여 check_search를 종료시킨다
	 */
	public void run(){
		super.run();
		check_search.start();
		long startTime = System.currentTimeMillis();
		while(step != 2){
			if(!sendcl.isEmpty()){
				System.out.println("연결요청");
				Sendpageurl();
			}
		}
		System.out.println("send끝");
		long endTime = System.currentTimeMillis();
		System.out.println("실행시간 : " + (endTime - startTime));
		check_search.interrupt();
	}
	
	/**
	 * dead의 상태를 갱신해주는 메소드
	 * connection 상태를 확인하고 연결되어져 있거나 검색이 안되는 컴퓨터를 걸러내어 dead에 해당 Client가 없다면 dead에 추가
	 * 연결이 가능하나 dead에 있는 경우 해당 Client를 dead에서 삭제
	 */
	
	private void Setdead(){	
		for(int i = 0; i<DEFINE;i++){
			if(connection.get(Getaddress(i).GetIP())!=null){
				System.out.println("setDead 부분 " + this.connection.get(this.Getaddress(i).GetIP()));
			}
			if(connection.get(Getaddress(i).GetIP())!= null && !(this.dead.contains(i))){
				System.out.println("setdead 1 if");
				this.dead.add(i);
			}
			if(this.connection.get(Getaddress(i).GetIP()) == null && this.dead.contains(i)){
				for(int j = 0; j < this.dead.size(); j++){
					if(this.dead.get(j) == i){
						this.dead.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * Send_Thread의 실제 메인
	 * dead의 크기가 DEFINE과 같은면 2초간 재운다
	 * dead의 크기가 DEFINE보다 작다면 메세지를 보낸 준비를 한다
	 */
	
	public void Sendpageurl(){
		while(!sendcl.isEmpty()){
			System.out.println("dead size : " + String.valueOf(dead.size()));
			Setdead();
			if(dead.size() == DEFINE){
				try {
					System.out.println("시스템재웁니다");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				System.out.println("연결요청");
				CheckList temp; 		
				temp= this.sendcl.remove(0);
				int num = ConfirmRandom();
				Address n_address = Getaddress(num);
				SocketConnection(n_address.GetIP(),n_address.Getport(),temp);
			}
		}
		this.step = 2;
	}
	
	/**
	 * 
	 * @param IP Client의 IP
	 * @param port Client의 PORT, 3081로 통일
	 * @param clist 보내야하는 CheckList
	 * 파라미터로 들어온 Client로 clist를 전송
	 */
	
	public void SocketConnection(String IP, int port, CheckList clist){
		ObjectOutputStream oos = null;
		Socket socket = null;
		try {
			System.out.println("IP : " + IP + " PORT : " + port);
			socket = new Socket(IP, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			 //oos.reset();
			oos.writeObject(clist);
			oos.close();
            socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
             this.connection.put(IP, clist.Gettargeturl());
             System.out.println("targeturl : " + clist.Gettargeturl());
		}
		
	}
	/*
	private int CalculateRandom(){
		int num = DEFINE;
		if(this.dead.size() != DEFINE){
			num = ConfirmRandom();
		}
		System.out.
		return num;
	}*/
	
	/**
	 * 검색엔진에서 결과값을 갖고 올 수 있으며 현재 작업이 없는 Client중에서 하나의 Client를 선택하는 메소드
	 * @return 값은 int형이며 return값의 인자는 지역변수 num이다
	 */
	private int ConfirmRandom() {
		int num = DEFINE;
		while(num == DEFINE){
			Random random = new Random();
			int temp = random.nextInt(100) + 1;
			temp = (temp * Calendar.SECOND) % DEFINE;
			if(!(dead.contains(temp))){
				num = temp;
				System.out.println("confirmrandom 확인");
			}
		}
		return num;
	}

	/*
	public void Sendtargeturl(){
		for(int i = 0; i< DEFINE; i++){
			Address n_address = Getaddress(i);
			if(connection.get(n_address.GetIP()) ==  null){
				SocketConnection(n_address.GetIP(),n_address.Getport(),targeturl[i]);
			}
			else continue;
		}
	}*/
	/*
	public void Checklife(){
		for(Address n_address : address.values()){
			InetAddress iaddr;
			try {
				iaddr = InetAddress.getByName(n_address.GetIP());
				try {
					boolean reachable = iaddr.isReachable(2000);
					if(reachable){
						life.put(n_address.GetIP(), true);
					}
					else{
						life.put(n_address.GetIP(), false);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}*/
	
	
	/**
	 * 
	 * @param i Address에 저장되어 있는 이름의 번호째수를 계산
	 * @return Address형을 리턴함
	 */

	private Address Getaddress(int i) {
		String enum_name = "Computer";
		enum_name = enum_name + String.valueOf(i);
		Address n_address = Address.valueOf(enum_name);
		return n_address;
	}
}
