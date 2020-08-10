package kr.ac.cbnu.ubigame.analyser.common.webdata;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.ac.cbnu.ubigame.analyser.common.datatype.CompanyDatatype;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;

public interface IWebDataDao {
	public void add(WebDatatype webDatatype) throws Exception;

	public int GetId();
	public Date Getdate(String targetcompany);

	public List<CompanyDatatype> getCompanyDatatype();
}
