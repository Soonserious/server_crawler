package kr.ac.cbnu.ubigame.analyser.common.datatype;

public class CompanyDatatype {
	private String targetURL;
	private String press;
	private String articleDate;
	private String articleTitle;
	private String articleBody;

	public CompanyDatatype(){
		this.targetURL = null;
		this.press = null;
		this.articleDate = null;
		this.articleTitle = null;
		this.articleBody = null;
	}
	public String getPress(){
		return this.press;
	}
	
	public String gettargeturl(){
		return this.targetURL;
	}
	
	public String getArticleBody(){
		return this.articleBody;
	}
}
