package cn.com.dplus.report.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.entity.others.TokenData;
import cn.com.dplus.report.service.inter.v1.IServiceApi;

/**
 * @作用:
 * @所在包: cn.com.dplus.report.aspect
 * @author: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/11
 * @公司: 广州讯动网络科技有限公司
 */
//@Aspect
//@Component
public class ReportControllerAspect {

    // 定义切点Pointcut
//    @Pointcut("execution(* cn.com.dplus.report.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void excudeService() {}

//    @Autowired
    private IServiceApi iServiceApi;

    private final static String RQBody = "RQBody";

//    @Around("excudeService()")
    public ResponseEntity doAround(ProceedingJoinPoint pjp) throws Throwable{
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  ((ServletRequestAttributes) ra).getRequest();
        String token = request.getParameter("token");
        TokenData tokenData = iServiceApi.getToken(token);
        if(tokenData!=null){
            request.getParameterMap().put("userId",new String[]{tokenData.getUserId()});
            // result的值就是被拦截方法的返回值
            ResponseEntity result = (ResponseEntity) pjp.proceed();
            return  result;
        }else {
            return  new ResponseEntity(Code.TOKEN_INVALID,Code.TOKEN_INVALID_MSG);
        }
    }



}
