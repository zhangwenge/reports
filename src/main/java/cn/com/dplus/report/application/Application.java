package cn.com.dplus.report.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.com.dplus.*")
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class,DataSourceAutoConfiguration.class})
public class Application {


	/**
	 * 
	 * @方法功能： TODO 项目启动的总控开关
	 * @方法名称： main
	 * @编写时间： 上午10:48:35
	 * @开发者 ： 黄先国
	 * @方法参数： @param args
	 * @返回值 : void
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
