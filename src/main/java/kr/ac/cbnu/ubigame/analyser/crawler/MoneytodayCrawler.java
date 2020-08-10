package kr.ac.cbnu.ubigame.analyser.crawler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.cbnu.ubigame.analyser.common.crawler.AbsCrawler;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public class MoneytodayCrawler extends AbsCrawler {
	private Document doc;
	private String journal;
	
	public void SetJournal(){
		this.journal = "머니투데이";
	}
	
	public String GetJournal(){
		return this.journal;
	}

	public void SetDocument(Document tempdoc){
		this.doc = tempdoc;
	}
	
	public Document GetDocument(){
		return this.doc;
	}
	
	public String bodyCrawler() throws IOException{/*신문 본문 크롤링*/
		
		Elements l = GetDocument().select("div[id=textBody]");
		return l.text();
	}


	public String journalIDSearch() {
		Elements l = GetDocument().select("input[name=no]");
		return l.text();
	}

	public Date timeAdjust() {/*기사게시시간을 yyyymmdd 형식으로 변형시켜주는 함수*/
		Elements div = GetDocument().select("span[class=num]");
		String articleTime = div.text();
		articleTime.trim();
		articleTime = articleTime.replaceAll("[^0-9]", "");
		if(!(articleTime.startsWith("20"))){
			articleTime = "20" + articleTime;
		}
		try{
			articleTime = articleTime.substring(0,8);
		}catch(Exception e){
			e.fillInStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(articleTime);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return date;
	}


	public String titleCrawler() {/*기사 제목 크롤링*/
		Elements l = GetDocument().select("div[id=article] > h1");
		return l.text();
	}

	public void setURL(String targetURL) throws IOException {
		Document tempdoc = Jsoup.connect(targetURL).get();
		SetDocument(tempdoc);
		SetJournal();
	}

}
