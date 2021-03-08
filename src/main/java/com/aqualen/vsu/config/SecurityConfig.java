package com.aqualen.vsu.config;

import com.aqualen.vsu.config.jwt.JwtFilter;
import com.aqualen.vsu.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and().csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
