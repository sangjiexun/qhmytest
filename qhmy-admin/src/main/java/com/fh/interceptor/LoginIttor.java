package com.fh.interceptor;



import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.User;
import com.fh.interceptor.commonabs.SysLogAbsTract;
import com.fh.service.system.oplog.OpLogManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.keman.zhgd.common.util.DateUtil;




/**
 * 
 * 类名称：登录过滤，权限验证 类描述：
 * 
 * @author zhoudibo 2015 作者单位： 联系方式： 创建时间：2015年11月2日
 * @version 1.6
 */
public class LoginIttor extends HandlerInterceptorAdapter {
	long beginTime;
	long endTime;
	@Resource(name = "oplogService")
	private OpLogManager oplogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (Const.ISDEBUG) {
			String path = request.getServletPath();
			if (handler instanceof HandlerMethod) {
				Session session = Jurisdiction.getSession();
				HandlerMethod method = (HandlerMethod) handler;
				String methodName = method.getMethod().getName();// 拦截的方法名
				String className = method.getBean().getClass().getName();
				MethodParameter methodParameter = method.getReturnType();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				
				String OperType = "";
				SysLogAbsTract.createSonClass();
				Map<String, Object> name = SysLogAbsTract.rongqi;//需要记录日志的url

				String viewName = "";
				if (modelAndView != null) {
					View view = modelAndView.getView();
					viewName = modelAndView.getViewName();
				}
				
				String IPADDRESS = "";
				if (request.getHeader("x-forwarded-for") == null) {
					IPADDRESS = request.getRemoteAddr();
				} else {
					IPADDRESS = request.getHeader("x-forwarded-for");
				}
				if (name.containsKey((path + ":" + methodName + ":" + viewName).trim())) {
					OperType = (String) name.get(path + ":" + methodName + ":" + viewName);
					if (user != null) {
						PageData pd = new PageData();
						pd.put("URL", path);
						pd.put("OPTYPE", OperType);
						pd.put("CJSJ", DateUtil.nowDate());
						pd.put("CZR", user.getUSER_ID());
						pd.put("CZRMC", user.getUSERNAME());
						pd.put("XGSJ", DateUtil.nowDate());
						pd.put("CLASSNAME", className);
						pd.put("FNNAME", methodName);
						pd.put("ID", UuidUtil.get32UUID());
						pd.put("IPADDRESS", IPADDRESS);
						oplogService.save(pd);
					}
				}
			}
			super.postHandle(request, response, handler, modelAndView);
		}
	}

	/**
	 * 执行完控制器后调用，即离开时
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception modelAndView) throws Exception {

		endTime = System.currentTimeMillis();
		super.afterCompletion(request, response, handler, modelAndView);
	}

}
