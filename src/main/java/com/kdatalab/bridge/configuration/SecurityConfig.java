package com.kdatalab.bridge.configuration;

import com.kdatalab.bridge.auth.CustomOAuth2UserService;
import com.kdatalab.bridge.security.jwt.JWTFilter;
import com.kdatalab.bridge.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 생성
 * @author Enclouds
 * @since 2020.12.11
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{


    private final JWTFilter jwtFilter;
    public final static String[] WHITE_LIST = {
            "/oauth2/**",
            "/login",
            "/join/sign",
//            "/user/leave-membership",
            "/user/logout",
            "/denied",
    };
    public final static String[] HAS_USER = {
        "/mypage/**",
        "/user/**"
    };

    public final static String[] HAS_ANY_AUTHENTICATION = {
            "/sub/**"
    };

    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(@Lazy JWTFilter jwtFilter, @Lazy UserService userService, CustomOAuth2UserService customOAuth2UserService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean public DaoAuthenticationProvider authenticationProvider(UserService userService) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }

//    @Bean
//    public SessionRegistry sessionRegistry(){
//        return new SessionRegistryImpl();
//    }

//    @Bean
//    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/admin/project-registration/**").hasRole("ADMIN")
                    .antMatchers(WHITE_LIST).permitAll()
                    .antMatchers(HAS_USER).hasRole("USER")
                    .antMatchers(HAS_ANY_AUTHENTICATION).fullyAuthenticated()
                    .antMatchers("/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        httpSecurity.addFilterBefore(filter, CsrfFilter.class);

//        httpSecurity.sessionManagement()
//                .maximumSessions(2)
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry());

//        httpSecurity.authorizeRequests()
                //페이지권한
//                .antMatchers("/admin/project-registration/**").hasRole("ADMIN")
//                .antMatchers(WHITE_LIST).permitAll()
//                .antMatchers(HAS_USER).hasRole("USER")
//                .antMatchers(HAS_ANY_AUTHENTICATION).fullyAuthenticated()
//                .antMatchers("/**").permitAll()
//                .anyRequest().permitAll()
//             .and()
//                .logout()
//                .logoutUrl("/user/logout")
//                .deleteCookies("JSESSIONID")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//             .and()
//                .oauth2Login()
//                .userInfoEndpoint().userService(customOAuth2UserService)
//        ;
//    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(
//            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
//            @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret) {
//        List<ClientRegistration> registrations = new ArrayList<>();
//
//        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
//                .clientId(kakaoClientId)
//                .clientSecret(kakaoClientSecret)
//                .jwkSetUri("temp")
//                .build());
//
//        return new InMemoryClientRegistrationRepository(registrations);
//    }
//    @Bean
//    protected DaoAuthenticationConfigurer configure(AuthenticationManagerBuilder auth) throws Exception {
//        return auth.userDetailsService(userService).passwordEncoder((passwordEncoder()));
//    }

}
