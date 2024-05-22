package tn.esprit.gestionhospitalierebackend.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.esprit.gestionhospitalierebackend.Services.implementation.UserDetailsServiceImpl;
import tn.esprit.gestionhospitalierebackend.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

           private final JwtAuthenticationFilter jwtAuthenticationFilter;

           private final UserDetailsServiceImpl userDetailsServiceImp;

           private final CustomAccessDeniedHandler accessDeniedHandler;
           private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          UserDetailsServiceImpl userDetailsServiceImp,
                          CustomAccessDeniedHandler accessDeniedHandler,
                          CustomLogoutHandler logoutHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutHandler = logoutHandler;
    }


    @Bean
             public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//
                    return http
                           .csrf(AbstractHttpConfigurer::disable)
                           .authorizeHttpRequests(
                                   req->req.requestMatchers("auth/login/**","auth/register/**","auth/refresh_token/**")
                                  .permitAll()
                                  //.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                  .anyRequest().authenticated())
                                  .userDetailsService(userDetailsServiceImp)
                                   .exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler)
                                  .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                                  //.authenticationProvider(authenticationProvider())
                                  .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                            .logout(l->l.logoutUrl("/logout")
                                    .addLogoutHandler(logoutHandler)
                                    .logoutSuccessHandler(
                                            ((request, response, authentication) -> SecurityContextHolder.clearContext()
                                            ))
                            )
                                  .build();

                }


         //pour le hachage des passwords
             @Bean
             public PasswordEncoder passwordEncoder(){
                     return new BCryptPasswordEncoder();
                    }

            /* @Bean
             public AuthenticationProvider authenticationProvider(){
                 DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
                 authenticationProvider.setUserDetailsService(userDetailsService());
                 authenticationProvider.setPasswordEncoder(passwordEncoder());
                return authenticationProvider;
                 }*/

             @Bean
            public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
               return config.getAuthenticationManager();
                }


        //pour débloquer cors auprés de spring security
        /*@Bean
        CorsConfigurationSource corsConfigurationSource(){
         CorsConfiguration corsConfiguration=new CorsConfiguration();
         corsConfiguration.addAllowedOrigin("*");
         corsConfiguration.addAllowedMethod("*");
         corsConfiguration.addAllowedHeader("*");
         corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
         UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**",corsConfiguration);
            return source;
        }*/





        }






