package com.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class ApplicationSecurity {
     
    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;
     
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }
     
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable()
        	.authorizeHttpRequests()
        		.requestMatchers("/api/auth/signin", "/api/auth/signup").permitAll()
        		.requestMatchers("/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**").permitAll()
        		.anyRequest().authenticated()
        		.and()
        		.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
        		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    	 http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    	 
        return http.build();
    }  
}