package kr.ac.cbnu.ubigame.analyser.common.crawler;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import kr.ac.cbnu.ubigame.analyser.common.JournalEnum;
import kr.ac.cbnu.ubigame.analyser.common.datatype.Journal;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public class CrawlerTest {
	WebDatatype wd;

	@Test
	public void testBodyCrawler() throws IOException {
		//선행조건
		this.wd = new WebDatatype();
		Document doc = Jsoup.connect("http://news.jtbc.joins.com/html/147/NB11407147.html").get();
		//기능수행
		Elements l = doc.select("div[class=article_content]");
		wd.SetArticleBody(l.text());
		//검증방법
		assertNotNull(l);
		assertEquals(l.text(),wd.GetArticleBody());
	}

	@Test
	public void testSetTargetid() {
		//선행조건
		File input = new File("C:/Users/Administrator/workspace/analyser/src/test/resource/inputartilcURL.txt");
		FileReader fileReader = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("C:/Users/Administrator/workspace/analyser/src/test/resource/outputarticleURL.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		wd = new WebDatatype();
		String company ="";
		
		try {
			fileReader = new FileReader(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fileReader);
		String targetURL = "";
		try {
			//기능수행
			while((targetURL = reader.readLine())!= null){
				for(JournalEnum u : JournalEnum.values()){
					if(targetURL.contains(u.getcompanyMainURL())){
						company += u.getjournal();
						fos.write(u.getjournal().getBytes());

					}
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		//검증방법
		String confirm ="";
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testConnectURL() throws IOException {
		//선행조건
		JournalEnum u = JournalEnum.Koreaherald;
		//기능수행
		String articleTime = u.gettimeaim();
		//검증방법
		assertEquals(u.gettimeaim(),articleTime);
		
	}

	@Test
	public void testTimeAdjust() {
		//선행조건
		String articleTime = "17.05.24 18:00";
		String match = "\\D";
		//기능수행
		if(!(articleTime.startsWith("20"))){
			articleTime = "20"+articleTime;
		}
		articleTime = articleTime.replaceAll(match, "");
		articleTime = articleTime.substring(0,8);
		System.out.println(articleTime);
		//검증방법
		assertEquals("20170524",articleTime);
	}

	@Test
	public void testTitleCrawling() throws IOException {
		//선행조건
		this.wd = new WebDatatype();
		Document doc = Jsoup.connect("http://news.jtbc.joins.com/html/147/NB11407147.html").get();
				//기능수행
		Elements l = doc.select("h3[id=jtbcBody]");
		this.wd.SetArticleTitle(l.text());
		//검증방법
		assertNotNull(l);
		assertEquals(l.text(),wd.GetArticleTitle());
		// TODO Auto-generated method stub
	}

	@Test
	public void testTimeConfirm() {
		//선행조건
		String time = "20170124";
		//기능수행
		Calendar cal = Calendar.getInstance();
		cal.add(cal.YEAR,-4);
		Date boundarytime = new Date(cal.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
		java.util.Date articletime = null;
		try {
			articletime = sdf.parse(time);
		} catch (ParseException e) {
				
			
		}
		int compare = articletime.compareTo(boundarytime);
		//검증방법
		assertEquals(1,compare);
	}
			
}


