package kr.ac.cbnu.ubigame.analyser.common.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {
	private final static String RESOURCE = "mybatis.xml";
	private static SqlSessionFactory sqlSessionFactory = null;
	
	public static SqlSessionFactory getSqlSessionFactory() {
		if(sqlSessionFactory == null) {
			Reader reader;
			try {
				reader = Resources.getResourceAsReader(RESOURCE);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			} catch (IOException e) {
				
			}
		}
		return sqlSessionFactory;
	}
}
