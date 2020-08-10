package communicationServer;

import java.io.Serializable;

public class CheckList implements Serializable{
	private static final long serialVersionUID = 1L;
	private int step;
	private String targeturl;
	private String IP;
	private String searchword;
	
	
	public CheckList(){
		this.step = 1;
		this.targeturl = null;
		this.IP = null;
		
	}
	
	public CheckList(String IP, int step){
		this.step = step;
		this.IP = IP;
	}
	
	public CheckList(String searchword, String IP ,String targeturl, int step){
		this.searchword = searchword;
		this.step = step;
		this.targeturl = targeturl;
		this.IP = IP;
	}
	
	public CheckList(String searchword ,String targeturl, int step){
		this.searchword = searchword;
		this.step = step;
		this.targeturl = targeturl;
	}
	
	public String getSearchword(){
		return this.searchword;
	}

	public CheckList(int step){
		this.step = step;
	}
	

	/*public CheckList(int step){
		this.step = step;
	}*/
	

	public int Getstep(){
		return step;
	}
	
	public String Gettargeturl(){
		return targeturl;
	}
	
	public void SetIP(String IP){
		this.IP = IP;
	}
	
	public String GetIP(){
		return this.IP;
	}
	
	public void Setstep(int n){
		this.step = n;
	}


}
