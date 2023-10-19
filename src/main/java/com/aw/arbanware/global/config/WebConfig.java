package com.aw.arbanware.global.config;

import com.aw.arbanware.global.config.security.SecurityArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private final SecurityArgumentResolver securityArgumentResolver;

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(securityArgumentResolver);
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:///C:/arbanWare/upload//")
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
	}
}
