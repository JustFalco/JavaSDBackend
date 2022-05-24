package nl.bd.sdbackendopdracht.security.config;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static nl.bd.sdbackendopdracht.security.enums.RoleEnums.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //TODO weghalen?
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/registration/**", "/").permitAll()
                .antMatchers("/api/v*/student/**").hasAnyAuthority(STUDENT.name(), DEVELOPER.name())
                .antMatchers("/api/v*/teacher/**").hasAnyAuthority(TEACHER.name(), DEVELOPER.name())
                .antMatchers("/api/v*/administrator/**").hasAnyAuthority(ADMINISTRATOR.name(), DEVELOPER.name())
                .antMatchers("/dashboard/**").hasAnyAuthority(STUDENT.name(), DEVELOPER.name(), TEACHER.name(), ADMINISTRATOR.name())
                .antMatchers("/api/v*/**").hasAnyAuthority(STUDENT.name(), DEVELOPER.name(), TEACHER.name(), ADMINISTRATOR.name())
                .antMatchers("/css/**").hasAuthority(DEVELOPER.name())
                .antMatchers("/imgs/**").hasAuthority(DEVELOPER.name())
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}