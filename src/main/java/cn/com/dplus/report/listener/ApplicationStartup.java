package cn.com.dplus.report.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.com.dplus.mongodb.MongoDB;
import cn.com.dplus.project.utils.LogUtil;
import cn.com.dplus.project.utils.ThreadPoolExecutors;

/**
 * 
 *  @类功能:	TODO	监听项目启动完成的监听器
 *	@文件名:	ApplicationStartup.java
 * 	@所在包:	cn.com.dplus.d.listener
 *	@author:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年7月19日下午2:42:48
 *	@公_司:		广州讯动网络科技有限公司
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	
	
    // 标识系统是否启动
    private static boolean startFlag = false;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			if(!startFlag){
				
				Assert.isTrue(ThreadPoolExecutors.init(), "线程池初始化失败");
				
				Assert.isTrue(MongoDB.init(),"mongo 初始化失败");
				
				// 初始化redis的连接服务
//				Assert.isTrue(Redis.init(),"redis 初始化失败");
				// 启动http请求记录  */
				//ThreadPoolExecutors.execute(new HttpRecorder());
					
				//更改标识，防止二次启动
				startFlag = true;
			}
			
		} catch (Exception e) {
			LogUtil.Error(e.getMessage());
			System.err.println("####################################################");
			System.err.println("##################    启动失败     ####################");
			System.err.println("####################################################");
			System.exit(1);
		}
	}
}
