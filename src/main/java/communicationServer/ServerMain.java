package communicationServer;

import java.io.IOException;
import java.util.ArrayList;
import servercrawler.ServerCrawler;

/**
 * Server의 통신과 Crawler를 이어주는 클래스
 * @author user
 *
 */

public class ServerMain {
	private ArrayList<CheckList> sendcl;
	private Server server;
	private ServerCrawler servercrawler;
	private int step;
	
	public ServerMain() throws Exception{
		this.sendcl = new ArrayList<CheckList>();
		this.step = 1;
		this.server = new Server(this.sendcl,step);
		this.servercrawler = new ServerCrawler(sendcl,step);
	}
	/**
	 * 검색어를 입력 받고 검색어 결과값이 있는 검색어에 대해서만 크롤링 시작
	 * 이후 Server는 다른 Thread로 프로그램이 종료될때까지 Servercrawler와 별도의 Thread로 작동
	 * 서버 Thread가 작동 되고 난 이후 보내야하는 페이지에 따른 결과값을 도출한다.
	 * @throws IOException
	 */
	
	public void CrawlerStart() throws IOException{
		this.servercrawler.searchStart();
		this.server.start();
	}
	
	/*public void modifyStart() {
		// TODO Auto-generated method stub
		this.servercrawler.researchDBData();
		this.server.start();
	}*/
}
