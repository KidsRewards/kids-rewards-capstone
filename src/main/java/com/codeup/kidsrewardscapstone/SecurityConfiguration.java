package com.codeup.kidsrewardscapstone;

import com.codeup.kidsrewardscapstone.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader){
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    Added this for the images
    @Override
    public void configure(WebSecurity web)throws Exception {
        web.ignoring().antMatchers("/static/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(usersLoader) //how to find admins by their username
                .passwordEncoder(passwordEncoder()); //how to encode and verify passwords
    }


    //Need to make changes to below method to enable proper file path configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                // anyone can see the home and the posts pages
                .antMatchers("/",  "/tasks", "/wishitem", "/rewards/{id: [\\d+]}", "/users/{id: [\\d+]}").permitAll()
                /* Pages that require authentication */
                .and()
                .authorizeRequests()
                .antMatchers(

                        "users/index", // only authenticated users can create posts
                        "/task/{id}/edit"// only authenticated users can edit tasks
                )
                .authenticated()
        ;
    }
}
