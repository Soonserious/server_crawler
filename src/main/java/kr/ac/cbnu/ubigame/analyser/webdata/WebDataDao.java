package kr.ac.cbnu.ubigame.analyser.webdata;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.ac.cbnu.ubigame.analyser.common.datatype.CompanyDatatype;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;
import kr.ac.cbnu.ubigame.analyser.common.mybatis.Mybatis;
import kr.ac.cbnu.ubigame.analyser.common.webdata.IWebDataDao;

public class WebDataDao implements IWebDataDao {
	private SqlSessionFactory sqlSessionFactory;
	
	public WebDataDao(){
		this.sqlSessionFactory = Mybatis.getSqlSessionFactory();
	}
	
	public void add(WebDatatype webDatatype)  throws Exception {
		SqlSession session = this.sqlSessionFactory.openSession(true);
		
		try {
			session.insert("Article.InsertArticle",webDatatype);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
	public int GetId(){
		SqlSession session = this.sqlSessionFactory.openSession(true);
		int result=0;
		try {
			result=session.selectOne("Article.getId");
		}catch(NullPointerException e){
			result = 0;
		}finally {
			session.close();
		}
		return result;
	}
	public Date Getdate(String targetcompany){
		SqlSession session = this.sqlSessionFactory.openSession(true);
		Date date = null;
		try{
			date = session.selectOne("Getdate",targetcompany);
		}catch(NullPointerException e){
			date = null;
		}finally{
			session.close();
		}
		return date;
	}

	@Override
	public List<CompanyDatatype> getCompanyDatatype() {
		// TODO Auto-generated method stub
		SqlSession session =  this.sqlSessionFactory.openSession(true);
		List<CompanyDatatype> returndata = null;
		returndata =  session.selectList("NonPress.selectURL");
		return returndata;
	}
}
