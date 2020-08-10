package kr.ac.cbnu.ubigame.analyser.datacollector;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import kr.ac.cbnu.ubigame.analyser.common.datatype.Journal;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public class TestDataCollector {
	private final static String driver = "com.mysql.jdbc.Driver";
	private final static String protocol = "jdbc:mysql://210.115.170.130:3306/";
	private final static String dbName = "analyser";
	private static IDatabaseTester databaseTester;
	private static IDataSet dataSet;
	private DataCollector datacollector = new DataCollector();
	private List<WebDatatype> datalist = new Vector<WebDatatype>();
	
	@Before
	public void setUp() throws SQLException, Exception{
		
		databaseTester = new JdbcDatabaseTester(driver, protocol + dbName, "analyser", "e8-1423");
		try{
			dataSet = new FlatXmlDataSetBuilder().build(
					new File("src/test/resource/kr/ac/cbnu/ubigame/analyser/webdata/clear_table.xml"));
			DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
		} finally{
			databaseTester.getConnection().close();
		}
	}
	
	@Test
	public void crawlingStart() throws IOException{
		//선행조건
		WebDatatype temp = new  WebDatatype();
		WebDatatype temp2 = new WebDatatype();
				
		Calendar cal = Calendar.getInstance();
		cal.set(2017,Calendar.JANUARY,01);
		Date date = new Date(cal.getTimeInMillis());
		temp.Setid(1);
		temp.SetJournal("JTBC");
		temp.SetArticleTitle("testtitle");
		temp.SetArticleBody("testbody");
		temp.SetTargetCompany("samsung");
		temp.SetDate(date);
		temp.SettargetUrl("123.com");
		
		temp2.Setid(2);
		temp2.SetJournal("프레시안");
		temp2.SetArticleTitle("temp2 타이틀");
		temp2.SetArticleBody("test2 내용");
		temp2.SetTargetCompany("삼성");
		temp2.SetDate(date);
		temp2.SettargetUrl("123.com");

		datacollector.data=temp;
		int i = 0;
		//기능수행
		/*if(datacollector.datas.isEmpty()){
			i =1;
		}
		else{
			
		}*/
		//검증방법
		assertEquals(0,i);
	}
	
	@Test
	public void TestDataCollectorAddData() throws SQLException, Exception {
		//선행조건
		WebDatatype temp = new  WebDatatype();
		WebDatatype temp2 = new WebDatatype();
		
		Calendar cal = Calendar.getInstance();
		cal.set(2017,Calendar.JANUARY,01);
		Date date = new Date(cal.getTimeInMillis());
		temp.Setid(1);
		temp.SetJournal("JTBC");
		temp.SetArticleTitle("testtitle");
		temp.SetArticleBody("testbody");
		temp.SetTargetCompany("samsung123123");
		temp.SetDate(date);
		temp.SettargetUrl("123.com");

		
		temp2.Setid(2);
		temp2.SetJournal("프레시안");
		temp2.SetArticleTitle("temp2 타이틀");
		temp2.SetArticleBody("test2 내용");
		temp2.SetTargetCompany("삼성123");
		temp2.SetDate(date);
		temp2.SettargetUrl("123.com");
		//기능수행
		datacollector.data=temp;
		datacollector.AddData();
		datacollector.data=temp2;
		datacollector.AddData();
		
		//검증
		IDataSet currentDBdataSet = databaseTester.getConnection().createDataSet(new String[]{"article"});
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resource/kr/ac/cbnu/ubigame/analyser/webdata/insert_datas.xml"));
		Assertion.assertEquals(expectedDataSet, currentDBdataSet);
	}
	
	@Test
	public void TestDataCollectorGetId(){
		//선행조건
		int testgetid=-1;
		
		//기능수행
		testgetid=datacollector.GetId();
		
		//검증
		assertEquals(2,testgetid);
	}
}

