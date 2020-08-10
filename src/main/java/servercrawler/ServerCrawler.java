
package servercrawler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import communicationServer.CheckList;
import kr.ac.cbnu.ubigame.analyser.common.Utils;
import kr.ac.cbnu.ubigame.analyser.webdata.WebDataManager;

/**
 * 검색할 양을 확인한 이 후 보내야되는 checkList형을 생산
 * @author user
 *
 */
public class ServerCrawler{
	private ArrayList<CheckList> sendcl;
	private String searchword;
	private String mainurl;
	private int pagecount;
	private WebDataManager webDataManager;
	private ArrayList<String> startdate;
	private ArrayList<String> enddate;
	private String user;
	
	public ServerCrawler(ArrayList<CheckList> sendcl, int step){
		this.sendcl = sendcl;
		this.searchword = null;
		mainurl= null;
		pagecount = 0;
		this.webDataManager = new WebDataManager();
		this.startdate = new ArrayList<String>();
		this.enddate = new ArrayList<String>();
		this.user = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36";
	}
	
	/**
	 * 검색 회사 입력함수
	 * @return 입력된 검핵회사
	 * @throws IOException
	 */
	
	public void searchwordInput() throws IOException{ 
		this.searchword = null;
		while(searchword == null){
			System.out.println("검색 회사를 입력하세요");
			Scanner scan = new Scanner(System.in);
			this.searchword = scan.nextLine();
			scan.close();
		}
	}
	
	/**
	 * ServerCrawler의 Main함수
	 * Target회사를 입력 받고 입력받은 값을 UTF-8형식으로 인코딩한다
	 * 이후 검색 결과가 있는지 없는 지 판단한다
	 * 검색결과가 있다면 CheckList형을 만들고 없다면 다시 입력을 받게 한다
	 * @throws IOException
	 */
	public void searchStart() throws IOException{/*Search함수의 메인*/
		do{
			searchwordInput();
			this.mainurl = Utils.UTFEncording(this.searchword);
		}while(wrongSearch(this.mainurl));
		this.Looppage();
		System.out.println("sendClSize : " + this.sendcl.size());
	}

	/**
	 * 몇 페이지 까지 존재하는지 검사하는 메소드
	 * @return 
	 * @throws IOException
	 */
	private int pageCalculate() throws IOException {
		String page = this.mainurl;
		Document dom = Jsoup.connect(page).userAgent(this.user).get();
		Elements div = dom.select("div[class=title_desc all_my] > span");
		String entirepage = div.text();
		if(!(entirepage.isEmpty())){
			int cut = entirepage.indexOf("/");
			entirepage = entirepage.substring(cut, entirepage.length());
			entirepage = entirepage.replaceAll("[^0-9]", "");
			this.pagecount = Integer.parseInt(entirepage)/10;
			if(this.pagecount >= 400) this.pagecount = 400;
			return 0;
		}
		return 0;
	}
	

	/**
	 * 잘못된 검색어를 입력했는지 확인하는 함수
	 * @param PageURL
	 * @return
	 * @throws IOException
	 */
	private boolean wrongSearch(String PageURL) throws IOException {
		Document doc = Jsoup.connect(PageURL).userAgent(this.user).get();
		Elements E = doc.select("title");
		String title = E.text();
		if(title.startsWith("검색 결과가 없습니다 : 네이버 뉴스")){
			System.out.println("검색어를 다시 입력하세요");
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Client측으로 전송해야 하는 페이지의 수만큼 반복문을 사용해서 Makesendcl에 접근한다
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	//"&ds="+ this.startdate[i] + "&de="+ this.enddate[i] +
	
	private void setDate() throws ParseException {
		// TODO Auto-generated method stub
//		this.startdate.add("20100101");
//		this.enddate.add("20100630");
//		this.startdate.add("20100701");
//		this.enddate.add("20101231");
//		this.startdate.add("20110101");
//		this.enddate.add("20110630");
//		this.startdate.add("20110701");
//		this.enddate.add("20111231");
//		this.startdate.add("20120101");
//		this.enddate.add("20120630");
//		this.startdate.add("20120701");
//		this.enddate.add("20121231");
//		this.startdate.add("20130101");
//		this.enddate.add("20130630");
//		this.startdate.add("20130701");
//		this.enddate.add("20131231");
//		this.startdate.add("20140101");
//		this.enddate.add("20140630");
//		this.startdate.add("20140701");
//		this.enddate.add("20141231");
//		this.startdate.add("20150101");
//		this.enddate.add("20150630");
//		this.startdate.add("20150701");
//		this.enddate.add("20151231");
//		this.startdate.add("20160101");
//		this.enddate.add("20160630");
//		this.startdate.add("20160701");
//		this.enddate.add("20161231");
//		this.startdate.add("20170101");
//		this.enddate.add("20170630");
//		this.startdate.add("20170701");
//		this.enddate.add("20171110");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		String stringDate = "20171223";
		Calendar calendar = Calendar.getInstance();
		for(int i = 1; i<61; i++){
			date = dateFormat.parse(stringDate);
			calendar.setTime(date);
			calendar.add(Calendar.DATE,-1);
			stringDate = dateFormat.format(calendar.getTime());
			this.enddate.add(stringDate);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, -1);
			stringDate = dateFormat.format(calendar.getTime());
			this.startdate.add(stringDate);
		}
		
	}
	
	private void calculateDate(String strFinalDate, String strStartDate) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date endDate = sdf.parse(strStartDate);
		this.enddate.add(sdf.format(endDate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DATE, -7);
		Date startDate = calendar.getTime();
		this.startdate.add(sdf.format(startDate));
		Date finalDate = sdf.parse(strFinalDate);
		int dateFlag = startDate.compareTo(finalDate);
		while(dateFlag > 0){
			calendar.add(Calendar.DATE, -1);
			endDate = calendar.getTime();
			calendar.add(Calendar.DATE, -7);
			startDate = calendar.getTime();
			this.enddate.add(sdf.format(endDate));
			this.startdate.add(sdf.format(startDate));
			dateFlag = startDate.compareTo(finalDate);
		}
	}

	private void Looppage() throws IOException{
		try {
			String strStartDate = "20151231";
			String strFinalDate = "20150101";
			this.calculateDate(strFinalDate, strStartDate);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			Date startDate = sdf.parse(strStartDate);
//			Calendar calendar = Calendar.getInstance();
//			while(startDate.compareTo(sdf.parse("20161231")) > 0){
//				strStartDate = sdf.format(startDate);
//				calendar.setTime(startDate);
//				calendar.add(Calendar.YEAR, 1);
//				calendar.add(Calendar.DATE, -1);
//				Date endDate = calendar.getTime();
//				String strEndDate = sdf.format(endDate);
//				System.out.println(strStartDate + " " + strEndDate);
//				this.calculateDate(strStartDate, strEndDate);
				for(int i = 0 ; i < this.startdate.size(); i++){
					if(this.mainurl.contains("nso=so%3Ar%2Cp%3Afr")){
						this.mainurl = this.mainurl.substring(0, this.mainurl.indexOf("&nso=so%3Ar%2Cp%3Afr"));
					}
					this.mainurl += "&nso=so%3Ar%2Cp%3Afrom" + this.startdate.get(i)+ "to"+ this.enddate.get(i) + "%2Ca%3Aall&sort=2" ;
					pageCalculate();
					for(int j = 0; j < this.pagecount+1; j++){
						int id = j*10+1;
						String page = this.mainurl +"&start=" + String.valueOf(id);
						sendcl.add(Makesendcl(page));
					}	
//				}
//				this.startdate.clear();
//				this.enddate.clear();
//				calendar.setTime(startDate);
//				calendar.add(Calendar.YEAR, -1);
//				startDate = calendar.getTime();
//			}
				}
					
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 보내야 하는 CheckList를 생산한다
	 * @param page
	 * @return
	 */
	private CheckList Makesendcl(String page) {
		CheckList cl = new CheckList(this.searchword,page,1);
		return cl;
	}

	/*public void researchDBData() {
		// TODO Auto-generated method stub
		this.tempURL = this.webDataManager.getCompanyDatatype();
		int maxNum = 0;
		for(int count = 0; count < this.tempURL.size(); count++){
			if(this.tempURL.get(count).getArticleBody() != null){
				maxNum = count;
			}
		}
		
		System.out.println(maxNum);
		for(int count  = maxNum+1; count < this.tempURL.size(); count++){
			CompanyDatatype temp = this.tempURL.get(count);
			CheckList send = new CheckList(temp.gettargeturl(),temp.getPress());
			this.sendcl.add(send);
		}
	}*/
	
	/*
	private void dateIterator() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date date = null;
		for(int i = 0; i < 9; i++){
			date = new Date(cal.getTimeInMillis());
			this.enddate[i] = sdf.format(date);
			cal.add(Calendar.YEAR, -1);
			cal.add(Calendar.DATE, -1);
			date = new Date(cal.getTimeInMillis());
			this.startdate[i] = sdf.format(date);
		}
		
	}*/
	
	/*
	private void Looppage() throws IOException{
			if(this.mainurl.contains("nso=so%3Ar%2Cp%3Afr")){
				this.mainurl = this.mainurl.substring(0, this.mainurl.indexOf("&nso=so%3Ar%2Cp%3Afr")-1);
			}
			this.mainurl += "&nso=so%3Ar%2Cp%3Afrom" + this.startdate[i]+ "to"+ this.enddate[0] + "%2Ca%3Aall" ;
			pageCalculate();
			for(int j = 1; j < this.pagecount; j++){
				int id = j*10+1;
				String page = this.mainurl +"&start=" + String.valueOf(id);
				sendcl.add(Makesendcl(page));
			}
	}
	*/
	
	/*
	public String dateCalculate() {
		String enddate;
		String startdate; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTimeInMillis());
		enddate = sdf.format(date);
		cal.add(cal.YEAR, -6);
		date = new Date(cal.getTimeInMillis());
		startdate = sdf.format(date);
		String datequery = "nso=so%3Ar%2Cp%3Afrom" + startdate+ "to"+ enddate + "%2Ca%3Aall";
		return datequery;
	}

	public Date Findstartdate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -6);
		Date date = new Date(cal.getTimeInMillis());
		if(webDataManager.Getdate(this.targetcompany) != null){
			date = webDataManager.Getdate(this.targetcompany);
		}
		return date;
	}*/
	
}
