package com.ApiECommerce.apiec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ApiECommerce.apiec.DAO.IFileDao;

import jakarta.annotation.Resource;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableWebMvc
@EnableJpaRepositories
public class ApiECommerceApplication /*implements CommandLineRunner*/{

	/*@Resource
	IFileDao fileDao;
	*/
	public static void main(String[] args) {
		SpringApplication.run(ApiECommerceApplication.class, args);
	}
	
	/*@Override
	public void run(String... arg) throws Exception{
		fileDao.init();
	}*/

}
