package kr.ac.cbnu.ubigame.analyser.common;

import java.util.HashMap;
import java.util.Map;

import kr.ac.cbnu.ubigame.analyser.common.crawler.AbsCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.AsiaeconomyCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.BloterCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.DailianCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.DailysportsCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.DigitaltimesCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.DongaCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.EdailyCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.ElectronicCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.FinancialCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.GookMinCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.HaniCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.HankookilboCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.HankyungCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.HeraldeconomyCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.Inews24Crawler;
import kr.ac.cbnu.ubigame.analyser.crawler.JoesunCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.JoongangCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.JtbcCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.KBSCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.KhanCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.KoreaheraldCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.KoreatimesCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MBCCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MBNCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MediatodayCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MkCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MoneytodayCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.MydailyCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.NewdailyCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.NewsisCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.NocutnewsCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.OhmynewsCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.PressianCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SBSCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SegyeCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SeoulCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SportalkoreaCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SportsdongaCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.SportsjoesunCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.TvchosunCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.WowtvCrawler;
import kr.ac.cbnu.ubigame.analyser.crawler.YTNCrawler;



public class JournalMap {	
	public static Map<String, AbsCrawler> HashMapReturn(){
		Map<String, AbsCrawler> map = new HashMap<String, AbsCrawler>();
		
		map.put("국민일보",new GookMinCrawler());
		map.put("SBS 뉴스", new SBSCrawler());
		map.put("전자신문", new ElectronicCrawler());
		map.put("코리아타임즈", new KoreatimesCrawler());
		map.put("서울신문", new SeoulCrawler());
		map.put("YTN", new YTNCrawler());
		map.put("뉴시스", new NewsisCrawler());
		map.put("일간스포츠", new DailysportsCrawler());
		map.put("스포츠조선", new SportsjoesunCrawler());
		map.put("파이낸셜뉴스", new FinancialCrawler());
		map.put("KBS 뉴스", new KBSCrawler());
		map.put("코리아헤럴드", new KoreaheraldCrawler());
		map.put("이데일리", new EdailyCrawler());
		map.put("스포츠동아", new SportsdongaCrawler());
		map.put("아시아경제", new AsiaeconomyCrawler());
		map.put("디지털타임스", new DigitaltimesCrawler());
		map.put("헤럴드경제", new HeraldeconomyCrawler());
		map.put("조선일보", new JoesunCrawler());
		map.put("동아일보", new DongaCrawler());
		map.put("중앙일보", new JoongangCrawler());
		map.put("뉴데일리", new NewdailyCrawler());
		map.put("MBC 뉴스", new MBCCrawler());
		//map.put("OSEN", "OsenCrawler");
		map.put("머니투데이", new MoneytodayCrawler());
		map.put("블로터", new BloterCrawler());
		map.put("경향신문", new KhanCrawler());
		map.put("한국일보", new HankookilboCrawler());
		map.put("스포탈코리아", new SportalkoreaCrawler());
		map.put("매일경제", new MkCrawler());
		map.put("한국경제TV", new WowtvCrawler());
		map.put("미디어오늘", new MediatodayCrawler());
		map.put("MBN", new MBNCrawler());
		map.put("한겨레", new HaniCrawler());
		map.put("데일리안", new DailianCrawler());
		map.put("노컷뉴스", new NocutnewsCrawler());
		map.put("세계일보", new SegyeCrawler());
		map.put("ohmynews", new OhmynewsCrawler());
		map.put("한국경제", new HankyungCrawler());
		map.put("마이데일리", new MydailyCrawler());
		map.put("아이뉴스24", new Inews24Crawler());
		map.put("프레시안", new PressianCrawler());
		map.put("TV조선", new TvchosunCrawler());
		map.put("JTBC",new JtbcCrawler());
		
		return map;
	}

}

