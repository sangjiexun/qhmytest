package com.fh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.User;
import com.fh.util.Const;
//import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
/**
 * 
* 类名称：登录过滤，权限验证
* 类描述： 
* @author zhoudibo 2015
* 作者单位： 
* 联系方式：
* 创建时间：2015年11月2日
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user!=null){
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path); //访问权限校验
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return b;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
			}
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (Const.ISDEBUG) {
			String path = request.getServletPath();
			if (handler instanceof HandlerMethod) {
				HandlerMethod method = (HandlerMethod)handler;
				
				String methodName = method.getMethod().getName();//拦截的方法名
				String className = method.getBean().getClass().getName();
				MethodParameter methodParameter = method.getReturnType();
				
				
				
				System.out.println("------------------------------------");
				System.out.println("请求路径:"+path);
				System.out.println("请求类名:"+className);
				System.out.println("请求方法名称:"+methodName);
//				System.out.println("请求时间:"+DateUtil.getTime());
				
				if (modelAndView != null) {
					View view = modelAndView.getView();
					String viewName = modelAndView.getViewName();
					System.out.println("返回页面:" + viewName);
				} else {
					System.out.println("返回页面:无");
				}
				System.out.println("END------------------------------------");
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
}
