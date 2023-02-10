package com.kdatalab.bridge.configuration;

import com.kdatalab.bridge.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * LoggerInteceptor Bean에 등록.
 * @author yjy
 *
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
				.excludePathPatterns("/assets/**");
				
	}
	
//	@Bean
//	public Filter characterEncodingFilter() {
//
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		return characterEncodingFilter;
//
//	}
//
//	public HttpMessageConverter<String> responseBodyConverter(){
//		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//
//	}
	
}
