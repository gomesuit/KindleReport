package kindlereport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(InterceptorConfiguration.class);
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PageNameInterceptor())
			.excludePathPatterns("/css/**", "/img/**", "/js/**", "/echo")
			.addPathPatterns("/**");
	}
	
	private class PageNameInterceptor implements HandlerInterceptor{

		@Override
		public void afterCompletion(HttpServletRequest arg0,
				HttpServletResponse arg1, Object arg2, Exception arg3)
				throws Exception {
		}

		@Override
		public void postHandle(HttpServletRequest request,
				HttpServletResponse response, Object obj, ModelAndView mav)
				throws Exception {
			
			logger.debug("request.getRequestURI() = {}", request.getRequestURI());
			request.setAttribute("LIST_PAGE_URL", "list");
			request.setAttribute("DATELIST_PAGE_URL", "dateList");
			request.setAttribute("FRONT_PAGE_URL", "/");
		}

		@Override
		public boolean preHandle(HttpServletRequest arg0,
				HttpServletResponse arg1, Object arg2) throws Exception {
			return true;
		}
		
	}
}
