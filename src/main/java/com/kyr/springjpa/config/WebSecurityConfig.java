package com.kyr.springjpa.config;


import com.kyr.springjpa.model.bean.Auth;
import com.kyr.springjpa.service.MemberService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Setter(onMethod_ = @Autowired)
    private MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/user/contact").hasRole(Auth.ADMIN.name())
                //.antMatchers("/prod/contact").hasRole(Auth.OPERATOR.name())
                //.antMatchers(HttpMethod.POST,"/api/**").permitAll()
                .antMatchers("/member/contact", "/member/signin", "/index").permitAll()
                //.antMatchers("/**").authenticated()
            .and()
                .formLogin()
                .loginPage("/member/signin")
                .defaultSuccessUrl("/index")
                .failureForwardUrl("/member/signin")
                .usernameParameter("username")
            .and()
                .logout()
                .logoutUrl("/index")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**", "/css/**", "/data/**", "/img/**", "/js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
