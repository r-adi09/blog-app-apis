package com.springexample.blog.config;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.springexample.blog.security.CustomUserDetailService;
import com.springexample.blog.security.JwtAuthenticationEntryPoint;
import com.springexample.blog.security.JwtAuthenticationFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableWebMvc
//@EnableAutoConfiguration( exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	
	@Lazy
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Lazy
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/users/", "/api/v1/auth/login")
				.permitAll()
				.requestMatchers("/v3/api-docs")
				.permitAll()
				.anyRequest()
				.authenticated());
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		      
		
        http.addFilterBefore(jwtAuthenticationFilter,
        		UsernamePasswordAuthenticationFilter.class);
        DefaultSecurityFilterChain defaultSFC = http.build();
        
        return defaultSFC;
		
		
		
//		http.csrf(AbstractHttpConfigurer::disable)
//
//        .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/login")
//
//                .permitAll()
//
//                .anyRequest().authenticated())
//
//        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//		http.authenticationProvider(daoAuthenticationProvider());
//
//        return http.build();
		
		
//		http.
//		csrf()
//		.disable()
//		.authorizeHttpRequests()
//		.requestMatchers("/api/v1/auth/login")
//		.permitAll()
//		.requestMatchers(HttpMethod.GET)
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.exceptionHandling()
//		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		http.authenticationProvider(daoAuthenticationProvider());
//		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
//		
//		return defaultSecurityFilterChain;
		
	}
	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
//	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
		
	}
	
	


}
