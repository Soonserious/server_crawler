package kr.ac.cbnu.ubigame.analyser.common.webdata;

import java.sql.Date;
import java.util.Vector;

import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public abstract class AbsWebDataManager {
	protected IWebDataDao webDataDao;
	
	abstract public void AddData(WebDatatype webdatatype) throws Exception;
	abstract public int GetId();
	abstract public Date Getdate(String targetCompany);

}
