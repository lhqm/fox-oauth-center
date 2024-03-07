package com.pj;

import com.pj.util.AesUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动：Sa-OAuth2 Server端 
 * @author click33 
 */
@SpringBootApplication
@MapperScan("com.pj.mapper")
public class SaOAuth2ServerApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SaOAuth2ServerApplication.class, args);
		System.out.println("\nSa-Token-OAuth Server端启动成功");
	}
	
}
