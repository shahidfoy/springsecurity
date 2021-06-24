package com.shahidfoy.springsecurity.config;

import com.shahidfoy.springsecurity.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				 .and()
		    .cors().configurationSource(new CorsConfigurationSource() {
			 @Override
			 public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				 CorsConfiguration config = new CorsConfiguration();
				 config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				 config.setAllowedMethods(Collections.singletonList("*"));
				 config.setAllowCredentials(true);
				 config.setAllowedHeaders(Collections.singletonList("*"));
				 config.setExposedHeaders(Arrays.asList("Authorization"));
				 config.setMaxAge(3600L);
				 return config;
			 }
		 	})
		    	.and()
		    .csrf()
		    .disable()
//			.ignoringAntMatchers("/contact")
//		    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		    .and()
		    .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		    .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		    .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
		    .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		    .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
		    .authorizeRequests()
			.antMatchers("/myAccount").hasRole("USER")
			.antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
			.antMatchers("/myLoans").hasRole("ROOT")
			.antMatchers("/myCards").authenticated()
			.antMatchers("/notices").permitAll()
			.antMatchers("/contact").permitAll()
//			.and().formLogin()
			.and().httpBasic();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.inMemoryAuthentication()
//				.withUser("admin").password("12345").authorities("admin")
//				.and().withUser("user").password("12345").authorities("read")
//				.and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails user = User.withUsername("admin").password("12345").authorities("admin").build();
//		UserDetails user1 = User.withUsername("user").password("12345").authorities("read").build();
//		userDetailsService.createUser(user);
//		userDetailsService.createUser(user1);
//		auth.userDetailsService(userDetailsService);
//	}

//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
