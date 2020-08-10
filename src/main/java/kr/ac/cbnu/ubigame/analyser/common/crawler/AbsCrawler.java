package kr.ac.cbnu.ubigame.analyser.common.crawler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;
import kr.ac.cbnu.ubigame.analyser.common.webdata.AbsWebDataManager;
import kr.ac.cbnu.ubigame.analyser.datacollector.TestDataCollector;

public abstract class AbsCrawler {
	protected Document doc;
	protected String journal;
	protected ArrayList<String> arrayTitle;
	protected ArrayList<String> arrayBody;
	private String targetURL;
	private String searchcompany;
	
	public AbsCrawler(String targetURL) throws IOException {
		this.targetURL = targetURL;
		this.doc = Jsoup.connect(targetURL).get();
		this.arrayBody = new ArrayList<String>();
		this.arrayTitle = new ArrayList<String>();
		initArrayTitle();
		initArrayBody();
	}
	
	public AbsCrawler(String targetURL, String searchcompany) throws IOException{
		this(targetURL);
		this.searchcompany = searchcompany;
	}
	
	protected abstract void  initArrayBody();
	protected abstract void initArrayTitle(); 
	
	public String getJournal() {
		return this.journal;
	}
	
	public void setSearchCompany(String searchcompany){
		this.searchcompany = searchcompany;
	}
	
	public WebDatatype getWebDatatype() throws IOException, ParseException {
		WebDatatype result = new WebDatatype();
		result.SetTargetCompany(this.searchcompany);
		result.SettargetUrl(this.targetURL);
		result.SetArticleBody(this.bodyCrawler());
		result.SetDate(this.timeAdjust());
		result.SetJournal(this.journal);
		result.SetJournalId(this.journalIDSearch());
		result.SetArticleTitle(this.titleCrawler());
		return result;
	}
	
	/**
	 * 신문본문 크롤링
	 */
	public String bodyCrawler() throws IOException{
		return this.iterator(this.arrayBody);
	}
	public String journalIDSearch(){
		return this.iterator(this.arrayTitle);
	}
	
	/**
	 * 기사게시시간을 yyyymmdd 형식으로 변형시켜주는 함수
	 * @return
	 * @throws ParseException 
	 */
	public abstract Date timeAdjust() throws ParseException;
	public abstract String titleCrawler();
	
	protected String iterator(ArrayList<String> array){
		for(String s: array){
			Elements e = this.doc.select(s);
			if(!e.text().isEmpty())
				return e.text();
		}
		return null;
	}
}
