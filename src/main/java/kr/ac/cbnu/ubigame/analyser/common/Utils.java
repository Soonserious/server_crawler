package kr.ac.cbnu.ubigame.analyser.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public class Utils {

	public void nullbodyConfirm(WebDatatype wd, Elements l, int articlenumber, Elements E, String company) {
		if(wd != null){
			if(wd.GetDate() != null){
			}
			else{
				String dataDate = DateSearch(E.select("span[class = time]").get(articlenumber).text());
				BlockCrawling(l.attr("data-source-name"),l.attr("data-url"),l.attr("data-title"),dataDate,company);
			}
		}
		else{
			String dataDate = DateSearch(E.select("span[class = time]").get(articlenumber).text());
			BlockCrawling(l.attr("data-source-name"),l.attr("data-url"),l.attr("data-title"),dataDate,company);
		}
	}
	
	/*입력받은 회사를 utf-8형식으로 변환해주는 함수*/
	public static String UTFEncording(String targetcompany) {
		   
	 	String str = ""; // 최종 utf-8로 변환된 string
		byte[] utfStrBuffer = null;
		try {
			utfStrBuffer = targetcompany.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // byte형으로 변환

		
		for(int i = 0; i<utfStrBuffer.length; i++){
			if(Integer.toHexString(utfStrBuffer[i]).length()>3){
				str = str+"%"+Integer.toHexString(utfStrBuffer[i]).substring(6,8);
			}
			else{
				str = str + "%"+Integer.toHexString(utfStrBuffer[i]);
			}
		}
		
		return "https://search.naver.com/search.naver?query=" + targetcompany + "&where=news&ie=utf8&sm=nws_hty";			
		
	}
	/*조사가 되었지만 크롤링이 안되는 url*/
	public static void BlockCrawling(String journal, String targetURL, String title, String dataDate, String company) {
		BufferedWriter file = null;
		try {
			file = new BufferedWriter(new FileWriter("src/main/resource/blocking.txt",true));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			file.write(journal);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			file.write(targetURL);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try{
			file.newLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		try {
			file.write(title);
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		try {
			file.write(company);
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		try{
			file.write(dataDate);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			file.newLine();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void journalNonExsist(String journal, String targetURL, String title, String dataDate, String company) {/*현재 enum형으로 만든 Journal이외의 Journal이 있을 경우 Journal이름을 text로 저장*/
		BufferedWriter file = null;
		try {
			file = new BufferedWriter(new FileWriter("src/main/resource/nonExsist.txt",true));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			file.write(journal);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			file.write(targetURL);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try{
			file.newLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		try {
			file.write(title);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try {
			file.write(company);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try{
			file.write(dataDate);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			file.newLine();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try {
			file.newLine();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}

	/*조사가 안되거나 조사가 막힌 url들의 날짜를 계산하는 부분*/
	public String DateSearch(String text) {
		String dataDate = "";
		if(text.contains("일전") || text.contains("시간전")||text.contains("분전")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			if(text.contains("일전")){
				int cut = text.indexOf("일");
				text = text.substring(0, cut);
				cal.add(cal.DATE, -(Integer.parseInt(text)));
				dataDate = sdf.format(new Date(cal.getTimeInMillis()));
				}
			else{
				if(text.contains("시간전")){
					int cut = text.indexOf("시");
					text = text.substring(0,cut);
					java.util.Date time = cal.getTime();
					cal.setTime(time);
					cal.add(cal.HOUR, -(Integer.parseInt(text)));
					dataDate = sdf.format(new Date(cal.getTimeInMillis()));
					dataDate = dataDate.substring(0,8);
				}
				else{
					int cut = text.indexOf("분");
					text = text.substring(0, cut);
					java.util.Date time = cal.getTime();
					cal.setTime(time);
					cal.add(cal.MINUTE, -Integer.parseInt(text));
					dataDate = sdf.format(new Date(cal.getTimeInMillis()));
					dataDate = dataDate.substring(0,8);
				}
			}
		}
		else{
			text = text.replaceAll("[^0-9]","");
			dataDate = text;
		}
		
		return dataDate;
	}

	
	
}
