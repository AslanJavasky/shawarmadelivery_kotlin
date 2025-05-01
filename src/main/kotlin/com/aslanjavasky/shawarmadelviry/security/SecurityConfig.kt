package com.aslanjavasky.shawarmadelviry.security

import com.aslanjavasky.shawarmadelviry.security.entity.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("Aslan@com")
            .password(passwordEncoder().encode("123456"))
            .roles("USER")
            .build()

        val test = User.withUsername("tester")
            .password(passwordEncoder().encode("1234"))
            .roles(Role.ADMIN.name)
            .build()

        return InMemoryUserDetailsManager(user,test)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { auth ->
            auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin").hasRole(Role.ADMIN.name)
                .anyRequest().authenticated()
        }
//            .formLogin { form ->
//                form
//                    .loginPage("/auth/login")
//                    .loginProcessingUrl("/auth/login")
//                    .defaultSuccessUrl("/menu",true )
//                    .failureUrl("/auth/login?error")
//                    .permitAll()
//            }
//            .logout { logout ->
//                logout
//                    .logoutUrl("/auth/logout")
//                    .logoutSuccessUrl("/auth/login?logout")
//                    .permitAll()
//            }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }
}