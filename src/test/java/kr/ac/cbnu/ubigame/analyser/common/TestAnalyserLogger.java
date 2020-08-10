package kr.ac.cbnu.ubigame.analyser.common;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnalyserLogger {
	private Logger logger = Logger.getLogger(TestAnalyserLogger.class);
	private static String loggerPath = "D:/log.txt";
	
	@BeforeClass
	public static void init() {
		// 로그 파일 생성
		try {
			AnalyserLogger.setLoggerConf(loggerPath, "100MB", Level.ALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void _1로그테스트() {
		// 파일이 있다면 삭제
		File delFile = new File(loggerPath);
		delFile.delete();
		
		// 로그 출력
		logger.info("test");
		
		//로그 파일 생성되었는지 확인
		File file = new File(loggerPath);
		assertTrue(file.exists());
		assertNotEquals(0, file.length());
	}
	
	@Test
	public void _2로그테스트() {
		// 로그 출력
		logger.info("hello world");
		
		// 라인수 세기
		File file = new File(loggerPath);
		int lineCnt = 0;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			while(in.readLine() != null) {
				lineCnt++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 라인이 늘어나는가?
		assertTrue(lineCnt > 1);
	}
}
