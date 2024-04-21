package com.example.security.SecurityConfig;

import com.example.security.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//authenticated
private final MyUserDetailsService myUserDetialsService;
@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetialsService);
       daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());//never save password without decrypt

       return daoAuthenticationProvider;
        //
    }

@Bean//Authorization
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{

//        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()//delete/{admin}/{username}   /get-all/{admin}
//                .authenticationProvider(daoAuthenticationProvider()).authorizeHttpRequests().
//                requestMatchers("/api/v1/auth/register").permitAll()
//                .requestMatchers("/api/v1/auth/delete/","/get-all/{admin}").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and().logout().logoutUrl("api/v1/auth/logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).and().
//                httpBasic();
//---------------------------


    httpSecurity.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .authorizeRequests()
            .requestMatchers("/api/v1/auth/register").permitAll()
            .requestMatchers("/api/v1/auth/delete/**", "/get-all/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .and()
            .httpBasic();
//---------------------
        // .requestMatchers("/api/v1/auth/**").permitAll();////
        // .requestMatchers("/api/v1/auth/**").permitAll();//
        // .requestMatchers("/api/v1/auth/**").permitAll();//
        // .requestMatchers("/api/v1/auth/**").permitAll();//
//can do requestMatchers 60 lines cover alls apis in every controllers classes


        return httpSecurity.build(); }

//** all API (api/v1/auth) in this page have same authorize or one by one between /
// or HttpMethod.GET

    //1- config DaoAuthenticationProvider
    //2- implement myUserDetialsService
    //
}
