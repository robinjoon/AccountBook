package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import dao.AuthDao;

public class AuthIntercepter implements HandlerInterceptor{

	@Autowired
	private AuthDao dao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("AuthToken");
		return dao.checkToken(token);
	}

}
