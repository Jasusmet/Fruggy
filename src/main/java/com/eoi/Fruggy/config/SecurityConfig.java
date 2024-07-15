package com.eoi.Fruggy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true) // activa la seguridad basada en anotaciones a nivel de método, permitiendo el uso de anotaciones como @PreAuthorize, @Secured, y @RolesAllowed.
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // inicio de sesión personalizada
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );
        //cierre de sesión
        http.logout(logout -> logout
                .logoutUrl("/usuarios/logout")
                .logoutSuccessUrl("/")
        );
        // Autorización de Solicitudes
        http.authorizeHttpRequests()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/fonts/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                .anyRequest().authenticated()

                //Página de Acceso Denegado
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")

                //Desactivación de CSRF y CORS
                .and()
                .csrf().disable()
                .cors().disable()
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    //  para autenticar a los usuarios.
    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }


}
