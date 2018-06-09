package cn.com.dplus.report.application;

import cn.com.dplus.project.application.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
public class Application extends BaseApplication{


	/**
	 * 
	 * @方法功能： TODO 项目启动的总控开关
	 * @方法名称： main
	 * @编写时间： 上午10:48:35
	 * @author ： 黄先国
	 * @方法参数： @param args
	 * @返回值 : void
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
