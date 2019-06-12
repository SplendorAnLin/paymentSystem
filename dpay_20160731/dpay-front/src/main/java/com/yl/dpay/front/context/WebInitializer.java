package com.yl.dpay.front.context;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Web应用初始化
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class WebInitializer implements WebApplicationInitializer {
	private static final Logger logger = LoggerFactory.getLogger(WebInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// 创建Spring上下文
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringRootConfig.class);
		ConfigurableEnvironment environment = rootContext.getEnvironment();
		try {
			environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:/environment.properties"));
			environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:/serverHost.properties"));
		} catch (IOException e) {
			logger.error("", e);
		}
		// 管理Spring上下文的生命周期
		servletContext.addListener(new ContextLoaderListener(rootContext));

		/*
		 * // 字符编码过滤器
		 * FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		 * characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
		 * characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		 * characterEncodingFilter.setInitParameter("forceEncoding", "true");
		 */

		// 该过滤器用于实现单点登出功能，可选配置。
		/*
		 * FilterRegistration.Dynamic casSingleSignOutFiltert = servletContext.addFilter("CAS Single Sign Out Filter", new SingleSignOutFilter());
		 * casSingleSignOutFiltert.addMappingForUrlPatterns(null, false, "/*");
		 * // 该过滤器负责用户的认证工作，必须启用它
		 * FilterRegistration.Dynamic casAuthenticationFilter = servletContext.addFilter("CAS Authentication Filter", new AuthenticationFilter());
		 * Map<String, String> casAuthenticationFilterParams = new HashMap<String, String>();
		 * casAuthenticationFilterParams.put("casServerLoginUrl", environment.getProperty("casServerLoginUrl"));
		 * casAuthenticationFilterParams.put("serverName", environment.getProperty("serverName"));
		 * casAuthenticationFilter.setInitParameters(casAuthenticationFilterParams);
		 * casAuthenticationFilter.addMappingForUrlPatterns(null, false, "*.action");
		 * // 该过滤器负责对Ticket的校验工作，必须启用它
		 * FilterRegistration.Dynamic casValidationFilter = servletContext.addFilter("CAS Validation Filter", new Cas20ProxyReceivingTicketValidationFilter());
		 * Map<String, String> casValidationFilterParams = new HashMap<String, String>();
		 * casValidationFilterParams.put("casServerUrlPrefix", environment.getProperty("casServerUrlPrefix"));
		 * casValidationFilterParams.put("serverName", environment.getProperty("serverName"));
		 * casValidationFilter.setInitParameters(casValidationFilterParams);
		 * casValidationFilter.addMappingForUrlPatterns(null, false, "*.action");
		 * //
		 * FilterRegistration.Dynamic casWrapperFilter = servletContext.addFilter("CAS HttpServletRequest Wrapper Filter", new HttpServletRequestWrapperFilter());
		 * casWrapperFilter.addMappingForUrlPatterns(null, false, "*.action");
		 * // 该过滤器使得开发者可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。 比如AssertionHolder.getAssertion().getPrincipal().getName()
		 * FilterRegistration.Dynamic casAssertionFilter = servletContext.addFilter("CAS Assertion Thread Local Filter", new AssertionThreadLocalFilter());
		 * casAssertionFilter.addMappingForUrlPatterns(null, false, "*.action");
		 */

		// 创建SpringMVC上下文
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringMVCConfig.class);
		// 注册SpringMVC分发器
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
