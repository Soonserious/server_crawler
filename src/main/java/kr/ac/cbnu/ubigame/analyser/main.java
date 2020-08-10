package kr.ac.cbnu.ubigame.analyser;

import communicationServer.ServerMain;

public class main {
	static public void main(String[] args) throws Exception {
		try{
		ServerMain servermain = new ServerMain();
		//servermain.modifyStart();
		servermain.CrawlerStart();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
