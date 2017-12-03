package uk.co.bartcode.service.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "**").fullyAuthenticated()
                .antMatchers(HttpMethod.DELETE, "**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "**").fullyAuthenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
    }

}