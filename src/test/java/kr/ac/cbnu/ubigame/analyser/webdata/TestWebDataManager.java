package kr.ac.cbnu.ubigame.analyser.webdata;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

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
import kr.ac.cbnu.ubigame.analyser.common.webdata.IWebDataDao;

public class TestWebDataManager {
	private final static String driver = "com.mysql.jdbc.Driver";
	private final static String protocol = "jdbc:mysql://210.115.170.130:3306/";
	private final static String dbName = "analyser";
	private static IDatabaseTester databaseTester;
	private static IDataSet dataSet;
	private IWebDataDao webdatadao= new WebDataDao();
	private WebDatatype webdatatype = new WebDatatype();
	private WebDataManager webdatamanager = new WebDataManager();
	
	
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
	public void Testwebdatamanager() throws SQLException, Exception{
		//선행조건
		Calendar cal = Calendar.getInstance();
		cal.set(2017,Calendar.JANUARY,01);
		Date date = new Date(cal.getTimeInMillis());
		webdatatype.Setid(1);
		webdatatype.SetJournal("KBS");
		webdatatype.SetArticleTitle("testtitle");
		webdatatype.SetArticleBody("testbody");
		webdatatype.SetTargetCompany("samsung");
		webdatatype.SetDate(date);
		webdatatype.SettargetUrl("123.com");
		webdatatype.SetJournalId("123");
		//기능수행
		webdatamanager.AddData(webdatatype);
		
		//검증
		IDataSet currentDBdataSet = databaseTester.getConnection().createDataSet(new String[]{"article"});
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resource/kr/ac/cbnu/ubigame/analyser/webdata/insert_WebDatatype.xml"));
		Assertion.assertEquals(expectedDataSet, currentDBdataSet);
	}
	
	@Test
	public void TestgetId(){
		//선행조건
		int testgetId=-1;
		
		//기능수행
		testgetId=webdatamanager.GetId();
		
		//검증
		assertEquals(1,testgetId);
	}
}
