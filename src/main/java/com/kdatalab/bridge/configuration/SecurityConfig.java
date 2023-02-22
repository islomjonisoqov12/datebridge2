package com.kdatalab.bridge.configuration;

import com.kdatalab.bridge.auth.CustomOAuth2Provider;
import com.kdatalab.bridge.auth.CustomOAuth2UserService;
import com.kdatalab.bridge.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 생성
 * @author Enclouds
 * @since 2020.12.11
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public final static String[] WHITE_LIST = {
            "/oauth2/**",
            "/user/login",
//            "/user/leave-membership",
            "/user/logout",
            "/denied"
    };
    public final static String[] HAS_ANY_AUTHENTICATION = {
            "/user/check-password/{inputPassword}",
            "/user/check-password",
            "/user/info-edit",
            "/user/info-edit"

    };

    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(@Lazy UserService userService, CustomOAuth2UserService customOAuth2UserService) {
        this.userService = userService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/assets/**", "/js/**");
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public MyLoginSuccessHandler myLoginSuccessHandler(){
        return new MyLoginSuccessHandler("/");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        httpSecurity.addFilterBefore(filter, CsrfFilter.class);

        httpSecurity.sessionManagement()
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/duplicated-login")
                .sessionRegistry(sessionRegistry());

        httpSecurity.authorizeRequests()
                //페이지권한
                .antMatchers("/admin/project-registration/**").hasRole("ADMIN")
                .antMatchers("/sub/**").hasRole("USER")
                .antMatchers(HAS_ANY_AUTHENTICATION).fullyAuthenticated()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll()
             .and()
                .formLogin()
                .successHandler(myLoginSuccessHandler())
                .loginPage("/user/login")
                .permitAll()
             .and()
                .logout()
                .logoutUrl("/user/logout")
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
//             .and()
//                .exceptionHandling().accessDeniedPage("/denied")
             .and()
                .oauth2Login()
                .successHandler(myLoginSuccessHandler())
                .userInfoEndpoint().userService(customOAuth2UserService)
        ;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
            @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret) {
        List<ClientRegistration> registrations = new ArrayList<>();

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder((passwordEncoder()));
    }

}
