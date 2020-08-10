package kr.ac.cbnu.ubigame.analyser.webdata;

import java.sql.Date;
import java.util.List;

import kr.ac.cbnu.ubigame.analyser.common.datatype.CompanyDatatype;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;
import kr.ac.cbnu.ubigame.analyser.common.webdata.AbsWebDataManager;
import kr.ac.cbnu.ubigame.analyser.common.webdata.IWebDataDao;

public class WebDataManager extends AbsWebDataManager {
	public WebDataManager(IWebDataDao webDataDao) {
		this.webDataDao = webDataDao;
	}
	
	public WebDataManager(){
		this.webDataDao = new WebDataDao();
	}
	
	@Override
	public void AddData(WebDatatype data) throws Exception{
		webDataDao.add(data);
	}
	
	@Override
	public int GetId() {
		return webDataDao.GetId();
	}
	
	public Date Getdate(String targetcompany){
		return webDataDao.Getdate(targetcompany);
		
	}

	public List<CompanyDatatype> getCompanyDatatype() {
		// TODO Auto-generated method stub
		return this.webDataDao.getCompanyDatatype();
	}
	
}
