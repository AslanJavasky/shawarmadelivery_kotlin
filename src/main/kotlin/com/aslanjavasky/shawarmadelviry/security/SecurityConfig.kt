package com.aslanjavasky.shawarmadelviry.security

import com.aslanjavasky.shawarmadelviry.security.entity.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { auth ->
            auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin").hasRole(Role.ADMIN.name)
                .anyRequest().authenticated()
        }
            .formLogin { form ->
                form
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .defaultSuccessUrl("/menu",true )
                    .failureUrl("/auth/login?error")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login?logout")
                    .permitAll()
            }
        return http.build()
    }
}