package znu.visum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import znu.visum.business.accounts.services.impl.AccountServiceImpl;
import znu.visum.security.jwt.JWTAuthenticationFilter;
import znu.visum.security.jwt.JWTAuthorizationFilter;
import znu.visum.security.jwt.SecretInitializer;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccountServiceImpl accountServiceImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretInitializer secretInitializer;

    @Autowired
    public WebSecurityConfig(AccountServiceImpl accountServiceImpl,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             SecretInitializer secretInitializer) {
        this.accountServiceImpl = accountServiceImpl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretInitializer = secretInitializer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().and().
                authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/accounts/sign-in").permitAll()
                .antMatchers(HttpMethod.POST, "/api/accounts/sign-up").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(this.secretInitializer.getJwtSecretKey(), authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(this.secretInitializer.getJwtSecretKey(), authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountServiceImpl).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin(secretInitializer.getAllowedOrigins());
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("PATCH");
        corsConfiguration.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
