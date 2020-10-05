package com.aqualen.vsu.config;

import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/login", "/logout")
                    .permitAll()
                .antMatchers("/admin/**")
                    .hasRole(UserRole.Administrator.name())
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
        ;

        // Config for Login Form
        http
                .authorizeRequests()
                .and()
                .formLogin()//
                    // Submit URL of login page.
                    .loginProcessingUrl("/j_spring_security_check") // Submit URL
                    .loginPage("/login")//
                    .defaultSuccessUrl("/")//
                    .failureUrl("/login?error=true")//
                    .usernameParameter("username")//
                    .passwordParameter("password")
                // Config for Logout Page
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/webjars/**", "/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html/**", "/swagger-ui.html#/**");
    }

}
