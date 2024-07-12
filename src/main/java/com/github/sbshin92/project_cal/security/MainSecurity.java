package com.github.sbshin92.project_cal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MainSecurity {
	
	  private final UserDetailsService userDetailsService;

	    public MainSecurity(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

	    @Bean 
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }


	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http
         .authorizeHttpRequests(authorize -> authorize
             .requestMatchers("/css/**", "/js/**","/**","/login/","login/verify-token","/error/**").permitAll()
             .anyRequest().authenticated()
         )
         .formLogin(formLogin -> formLogin
             .loginPage("/login")
             .defaultSuccessUrl("/calendar", true)
             .failureUrl("/error/404")
             .permitAll()
         )
         .logout(logout -> logout
             .logoutSuccessUrl("/login")
             .permitAll()
         )
         
         .userDetailsService(userDetailsService)
         .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
         )
         .csrf(csrf -> csrf
                 .ignoringRequestMatchers("/login","/verify-token") // CSRF 보호에서 제외할 경로
             );

     return http.build();
 }
}