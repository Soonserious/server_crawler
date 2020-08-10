package communicationServer;


import java.util.ArrayList;
import java.util.HashMap;
public class Accept_Dispose extends Thread{
	private ArrayList<CheckList> arrayList;
	private HashMap<String, String> connection;
	private CheckList cl;
	
	/**
	 * 
	 * @param arrayList 보내야 하는 객체인 CheckList를 저장하는 배열을 설정
	 * @param connection 현재 작업을 하고있는 클라이언트 및 검색엔진을 이용할 수 없는 클라이언트 저장
	 */
	public Accept_Dispose(ArrayList<CheckList> arrayList, HashMap<String, String> connection){
		this.arrayList = arrayList;
		this.connection = connection;
		this.cl = null;
		this.arrayList.clear();
	}
	
	/**
	 * Accept_Dispose의 메인, 수신한 CheckList객체가 있다면 Disposemain호출
	 */
	public void run(){
		while(!Thread.interrupted()){
			if(arrayList.isEmpty()) {
				continue;
			}
			else{
				Disposemain();
			}
		}
	}
	
	/**
	 * arrayList에 가장 첫번째 CheckList를 arrayList에서 제거
	 * CheckList로 Client상태 판별
	 */
	private void Disposemain() {
		while(!arrayList.isEmpty()){
			this.cl = arrayList.remove(0);
			if(cl == null) continue;
			if(cl.Getstep() == 1){
				Dispose_Step1();
			}
			if(cl.Getstep() == 2){
				Dispose_Step2();
			}
			
		}
	}
	
	/**
	 * connection에 저장되어 있는 해당 Client의 상태를 검색불가로 바꿈
	 */
	private void Dispose_Step2(){
		connection.put(cl.GetIP(), "2");
		System.out.println(cl.GetIP() + " 뒤짐");
	}
	
	/**
	 * connection에 저장되어 있는 해당 Client의 상태를 검색가능으로 바꿈
	 */
	
	private void Dispose_Step1() {
		connection.put(cl.GetIP(), null);
	}
	
}
