package com.aw.arbanware.global.config;

import com.aw.arbanware.domain.common.attachfile.service.AttachFileService;
import com.aw.arbanware.global.config.security.SecurityArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
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
		String UPLOAD_FOLDER = AttachFileService.getUploadFolder();
		final String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			UPLOAD_FOLDER = "///" + UPLOAD_FOLDER;
		}
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:" + UPLOAD_FOLDER)
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
	}
}
