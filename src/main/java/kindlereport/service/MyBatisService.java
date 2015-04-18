package kindlereport.service;

import java.util.List;

import kindlereport.dao.KindleMapper;
import kindlereport.model.Kindle;

import org.springframework.beans.factory.annotation.Autowired;

public class MyBatisService {
	
	/**
	 * actorテーブルにアクセスするDMLを仲介するマッパー.
	 * Mybatis-Springによりマッパー・インターフェースが自動で探知され、
	 * アノテーションとマッパーXMLで指定されたクエリを実行するマッパー実装が自動生成される。
	 * 自動生成されたマッパー実装はSpringのもとで管理され{@link Autowired}指定されたフィールドに自動設定される。
	 */
	@Autowired
	private KindleMapper kindleMapper;
	
	public List<Kindle> getKindleList() {
		return kindleMapper.selectKindleList();
	}

}
