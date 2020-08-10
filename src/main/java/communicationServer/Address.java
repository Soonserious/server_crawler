package communicationServer;


/**
 * IP를 미리 설정해주는 Enum형
 * @author user
 *
 */
public enum Address {	
	Computer0(
			"52.79.125.107",
			3080
	),
	
	Computer1(
			"52.78.49.137",
			3080
	),
	
	Computer2(
			"52.78.148.108",
			3080
	),
	
//	Computer3(
//			"13.124.232.86",
//			3080
//	),
	
	Computer3(
			"52.78.120.182",
			3080
	);
	
	private String IP;
	private int port;
	Address(String IP, int port){
		this.IP = IP;
		this.port = port;
	}
	
	public String GetIP(){
		return this.IP;
	}
	
	public int Getport(){
		return this.port;
	}
}
