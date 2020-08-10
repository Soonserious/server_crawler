package kr.ac.cbnu.ubigame.analyser.common.crawler;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class seachEngineTest {

	@Test
	public void testUrlInsert() {
		//선행조건
		String company = "%BB%EF%BC%BA";
		String firstPage = "http://news.naver.com/main/search/search.nhn?query=" + company; //검색어만 적힌 검색페이지
		int page =1; // 페이지 숫자
		
		//기능수행
		String pageURL = firstPage + "&page="+ String.valueOf(page);
		
		//검증방법
		assertEquals("http://news.naver.com/main/search/search.nhn?query=%BB%EF%BC%BA&page=1",pageURL);
	
	}

	@Test
	public void testWrongSearch() throws IOException {
		//선행조건
			String PageURL = "http://news.naver.com/main/search/search.nhn?refresh=&so=rel.dsc&stPhoto=&stPaper=&stRelease=&ie=MS949&detail=0&rcsection=&query=gjlsjfls&sm=all.basic&pd=1&startDate=&endDate=";
			Document doc = Jsoup.connect(PageURL).get();
			Elements E = doc.select("title");
				
			String title = E.text();
			//기능수행
			System.out.println(E);
			System.out.println(title);
			//검증방법
			assertEquals("검색 결과가 없습니다 : 네이버 뉴스",title);
		
	}

	@Test
	public void testUrlCalculate() throws IOException {
		//선행조건
		String firstURL = "http://news.naver.com/main/search/search.nhn?query=%EC%82%BC%EC%84%B1&&ie=utf8";
		Document doc = Jsoup.connect(firstURL).get();
		String content = "div[id = search_div]";
		//기능수행
		Elements E  = doc.select(content);
		for(int articlenumber = 0; articlenumber<2; articlenumber++){ // 네이버 검색창에 기사를 접근하는 함수
					
			String real = "a[id = spiButton" +String.valueOf(articlenumber)+"]";
			Elements l = E.select(real);
			Element clstime = E.select("div[class = ct]>div[class = info]>span[class = time]").get(articlenumber);
			String time = clstime.text();
			//검증방법
			System.out.println(time);	
			assertNotNull(time);
		}
					
	}

	@Test
	public void testJournalNonExsist() {
		//선행조건
		File inputFile = new File("C:/Users/Administrator/workspace/analyser/src/main/resource/input.txt");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(inputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fileReader);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream("C:/Users/Administrator/workspace/analyser/src/test/resource/output.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = null;
		//기능수행
		try {
			while((line = reader.readLine()) != null){
				
			
			try {
				output.write(line.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(output);
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testJournalFind() {
		fail("Not yet implemented");
	}

	@Test
	public void testCrawlingStart() {
		fail("Not yet implemented");
	}
	
	@Test
	public void pageCalculate() throws IOException {
		String page = "http://news.naver.com/main/search/search.nhn?query=%BB%EF%BC%BA&page=1&startDate=1960-01-01&endDate=2017-01-25";
		Document dom = Jsoup.connect(page).get();
		Elements div = dom.select("span[class = result_num]");
		String entirepage = div.text();
		int cut = entirepage.indexOf("/");
		entirepage = entirepage.substring(cut, entirepage.length());
		entirepage = entirepage.replaceAll("[^0-9]", "");
		int pagesearch = Integer.parseInt(entirepage)/10; 
		
		System.out.println(pagesearch);
		
		assertEquals(607696,pagesearch);
	}
	
	@Test
	public void dateCalculate(){
		//선행조건
		String enddate;
		Object startdate; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//기능수행
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTimeInMillis());
		enddate = sdf.format(date);
		cal.add(cal.YEAR, -4);
		date = new Date(cal.getTimeInMillis());
		startdate = sdf.format(date);
		String datequery = "&startDate=" + startdate+ "&endDate="+ enddate;
		// TODO Auto-generated method stub
		System.out.println(datequery);
		//검증방법
		assertEquals("&startDate=2013-01-25&endDate=2017-01-25",datequery);
	}
	

	@Test
	public void testUTFEncording() {
		//선행조건
		String targetcompany = "삼성";
		String str = ""; // 최종 utf-8로 변환된 string
		//기능수행
		try{
			byte[] utfStrBuffer = targetcompany.getBytes("UTF-8"); // byte형으로 변환
		
			
			for(int i = 0; i<utfStrBuffer.length; i++){
				if(Integer.toHexString(utfStrBuffer[i]).length()>3){
					str = str+"%"+Integer.toHexString(utfStrBuffer[i]).substring(6,8);
				}
				else{
					str = str + "%"+Integer.toHexString(utfStrBuffer[i]);
				}
			}
		
		//검증방법
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println(str);	
			assertEquals("%ec%82%bc%ec%84%b1",str);
				
				
			}

	}

}
