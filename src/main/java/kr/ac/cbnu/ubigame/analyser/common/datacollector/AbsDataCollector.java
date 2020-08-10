package kr.ac.cbnu.ubigame.analyser.common.datacollector;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import org.jsoup.nodes.Document;
import kr.ac.cbnu.ubigame.analyser.common.crawler.AbsCrawler;
import kr.ac.cbnu.ubigame.analyser.common.datatype.WebDatatype;
import kr.ac.cbnu.ubigame.analyser.common.webdata.AbsWebDataManager;

public abstract class AbsDataCollector {
	protected AbsWebDataManager webDataManager;
	WebDatatype data;
	abstract public void crawlingStart() throws IOException, Exception;
	abstract public void AddData() throws Exception;
	abstract public int GetId();
}
