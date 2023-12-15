package gov.track.doc.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        try{
            http
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/login?expired")
                    .and()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/admin").hasAnyAuthority("ADMIN")
                    .antMatchers("/application").hasAnyAuthority("ADMIN","CLIENT")
                    .antMatchers("/").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error")
                    .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .usernameParameter("email")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                    .logout().permitAll()
                    .and()
                    .rememberMe()
                    .key("123456789_abcdefghijkLMnopQRstuvWxZY")
                    .tokenValiditySeconds(14 * 24 * 60 *60)
            ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
    }
    //    @Bean
//    public UserDetailsService userDetailsService(){
//        return new CustomerUserDetailsService();
//    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserCustomDetailsService();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
