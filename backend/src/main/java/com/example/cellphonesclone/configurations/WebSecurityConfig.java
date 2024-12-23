package com.example.cellphonesclone.configurations;

import com.example.cellphonesclone.filters.JwtTokenFilter;
import com.example.cellphonesclone.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
//public class WebSecurityConfig {
//    private final JwtTokenFilter jwtTokenFilter;
//    @Value("${api.prefix}")
//    private String apiPrefix;
//    @Bean
//    //Pair.of(String.format("%s/products", apiPrefix), "GET"),
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
//        http
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(requests -> {
//                    requests
//                            .requestMatchers(
//                                    String.format("%s/users/register", apiPrefix),
//                                    String.format("%s/users/login", apiPrefix)
//                            )
//                            .permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/roles**", apiPrefix)).permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/brands**", apiPrefix)).permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/brands/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(POST,
//                                    String.format("%s/brands/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(PUT,
//                                    String.format("%s/brands/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(DELETE,
//                                    String.format("%s/brands/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/products**", apiPrefix)).permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(GET,
//                                    String.format("%s/products/images/*", apiPrefix)).permitAll()
//
//                            .requestMatchers(POST,
//                                    String.format("%s/products**", apiPrefix)).permitAll()
//
//                            .requestMatchers(PUT,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(DELETE,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//
//
//                            .requestMatchers(POST,
//                                    String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER)
//
//                            .requestMatchers(GET,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(PUT,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(DELETE,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(POST,
//                                    String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.USER)
//
//                            .requestMatchers(GET,
//                                    String.format("%s/order_details/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(PUT,
//                                    String.format("%s/order_details/**", apiPrefix)).permitAll()
//
//                            .requestMatchers(DELETE,
//                                    String.format("%s/order_details/**", apiPrefix)).permitAll()
//
//
//                            .anyRequest().authenticated();
//                    //.anyRequest().permitAll();
//
//                })
//                .csrf(AbstractHttpConfigurer::disable);
//        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                CorsConfiguration configuration = new CorsConfiguration();
//                configuration.setAllowedOrigins(List.of("*"));
//                //configuration.setAllowedOrigins(List.of("http://localhost:4200"));
//                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//                configuration.setExposedHeaders(List.of("x-auth-token"));
//                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                source.registerCorsConfiguration("/**", configuration);
//                httpSecurityCorsConfigurer.configurationSource(source);
//            }
//        });
//
//        return http.build();
//    }
//}
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(requests -> {
                    requests.anyRequest().permitAll();
                });

        return http.build();
    }
}