package app.web.scout;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.web.scout.filters.JsogRequestFilter;

@SpringBootApplication
@EnableAsync
public class Application {
	
	@Autowired private JsogRequestFilter jsogRequestFilter;

	/*
	 * Utilizando este modo, se debe asegurar que las claves tengan el prefijo del Password encoder a usar, Ej:
	 * MD5: {MD5}0192023a7bbd73250516f069df18b500
	 * BCrypt: {bcrypt}$2a$10$GkgFCIqUKxCHXdNBqaqcm.Q680G1hAfsBfgAJSphuhC03YWXpi1pi
	 * 
	 * https://dzone.com/articles/password-encoder-migration-with-spring-security-5
	 * 
	 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

	
	/**
	 * Fixes empty attribute and unknown properties JSON error.
	 * @param args
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    MappingJackson2HttpMessageConverter converter =  new MappingJackson2HttpMessageConverter(mapper);
	    return converter;
	}
	
	/**
	 * Adds application filters.
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<JsogRequestFilter> filterRegistrationBean() {
		FilterRegistrationBean<JsogRequestFilter> registration = new FilterRegistrationBean<JsogRequestFilter>();
	    registration.setFilter(jsogRequestFilter);
	    registration.addUrlPatterns("/**");
	    registration.setOrder(1);
	    return registration;
	}
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}
	
}
