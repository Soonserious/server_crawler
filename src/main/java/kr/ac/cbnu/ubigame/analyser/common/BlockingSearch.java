package kr.ac.cbnu.ubigame.analyser.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BlockingSearch {
	/*BlockingSearch의 main격*/
	public static void journalInvestigate() {
		BufferedReader bufferedReader  = null;
		try {
		  bufferedReader = new BufferedReader(new FileReader("src/main/resource/blocking.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("src/main/resource/BlockingSearch.txt",true));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String str = "";
		try {
			while((str = bufferedReader.readLine()) != null){
				System.out.println("1번째 str : " + str);
				bufferedWriter.write(str);
				bufferedWriter.flush();
				bufferedWriter.newLine();
				str = bufferedReader.readLine();
				System.out.println("2번째 str : " + str);
				Document doc = Jsoup.connect(str).get();
				str = bufferedReader.readLine();
				System.out.println("3번째 str" + str);
				titleidIvestigate(doc,str);
				str = bufferedReader.readLine();
				System.out.println("4번째 str : " + str);
				bodyInvestigate(doc,str);
				str = bufferedReader.readLine();
				System.out.println("5번째 str : "+ str);
				DateInvestigate(doc,str);
				bufferedWriter.newLine();
				str = bufferedReader.readLine();
				System.out.println("6번째 str : " + str);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/*날짜를 포함하고 있는 element밒 class,id 반환*/
	public static void DateInvestigate(Document doc, String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String date = str.substring(6, str.length());
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("src/main/resource/BlockingSearch.txt",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			bufferedWriter.write("날짜태그");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String regex = "^*."+year +"." + month +"." + date + "*$";
		System.out.println(regex);
		int i = 0;
		Calendar cal = Calendar.getInstance();
		int year1 = cal.YEAR;
		while(i<7){
			year1 -= i;
			Elements l = doc.getElementsContainingOwnText(String.valueOf(year1));
			for(Element temp : l){
				String container = temp.text();
				container = container.replaceAll("[^0-9]","");
				System.out.println(container);
				if(container.contains(str)){
					String write = "태그이름 : " + temp.tagName() +"[ class=" + temp.className() + " id = " + temp.id() + "] 내용 : " + temp.text();
					try {
						bufferedWriter.write(write);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						bufferedWriter.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			i++;
		}
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/*body를 포함하는 element및 class,id반환*/
	public static void bodyInvestigate(Document doc, String str) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("src/main/resource/BlockingSearch.txt",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("bodyInvestigate");
		BufferedWriter bufferedWriter = null;
		bufferedWriter = new BufferedWriter(fileWriter);
		
		Elements l = doc.getElementsContainingOwnText(str);
		System.out.println(" 탐색할 l 의 개수 : " + l.size() + " str : " + str);
		for(Element temp : l){
			
			Element par = temp.parent();
			Element grand = par.parent();
			String child = temp.tagName() +"["  + " class="+ temp.className() + " id = " + temp.id()+ "] : "+ temp.text();
			String parent =   temp.parent().tagName() +"[ class = " + temp.parent().className()+ " id =" + temp.parent().id() + "] : " + temp.parent().text();
			String grandParent = par.tagName() + "[ class = " + par.className() + " id =" + par.id() + "] : " + par.text();
			if(temp.className().isEmpty()&&temp.id().isEmpty()){
				
			}
			else{
				try {
					bufferedWriter.write(child);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bufferedWriter.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					bufferedWriter.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(par.className().isEmpty()&&par.id().isEmpty()){
				
			}
			else{
				try {
					bufferedWriter.write(parent);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bufferedWriter.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					bufferedWriter.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(grand.className().isEmpty()&&grand.id().isEmpty()){
				
			}
			else{
				try {
					bufferedWriter.write(grandParent);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bufferedWriter.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					bufferedWriter.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
	
			try {
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		}
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	
	/*title을 포함하고 있는 element및 class, id 반환*/
	public static void titleidIvestigate(Document doc, String str) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("src/main/resource/BlockingSearch.txt",true);
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		BufferedWriter bufferedWriter = null;
		bufferedWriter = new BufferedWriter(fileWriter);
		try {
			bufferedWriter.newLine();
		} catch (IOException e2) {
	
			e2.printStackTrace();
		}
		try {
			bufferedWriter.write("title부분");
			bufferedWriter.flush();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		try {
			bufferedWriter.newLine();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		Elements l = doc.getElementsContainingOwnText(str);
		for(Element temp : l){
			String titleid = "title tag : " + temp.tagName() + "[id = " +temp.id() + "class =  " + temp.className() + "] titletext : "+ temp.text();
			System.out.println(titleid);
			try {
				bufferedWriter.write(titleid);
				bufferedWriter.flush();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			try {
				bufferedWriter.newLine();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			try {
				bufferedWriter.newLine();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}
		
		
		try {
			fileWriter.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}


	}
