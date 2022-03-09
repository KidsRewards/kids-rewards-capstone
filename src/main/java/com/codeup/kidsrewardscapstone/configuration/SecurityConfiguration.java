//package com.codeup.kidsrewardscapstone.Configuration;
//
//import com.codeup.kidsrewardscapstone.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserServiceImpl oAuthService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll().antMatchers("/console/**")
//                .permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll().antMatchers("/users/**")
//                .permitAll().anyRequest()
//                .authenticated().and().oauth2Login().loginPage("/")
//                .userInfoEndpoint().userService(oAuth2UserService).and()
//                .successHandler(new AuthenticationSuccessHandler() {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//             OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//                System.out.println(oAuth2User);
//            oAuth2UserService.processOAuthPostLogin(oAuth2User.getAttributes("sub"));
//                response.sendRedirect("http://localhost:3030/");
//            }
//        })
//        .and().logout().logoutUrl("http://local:3030/");
//    }
//        @Bean
//        CorsConfigurationSource corsConfigurationSource() {
//            CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOrigins(List.of("http://localhost:3030"));
//            configuration.setAllowedHeaders("*");
//            configuration.setAllowedMethods("*");
//            configuration.setAllowCredentials(true);
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", configuration);
//                return source;
//        }
//}