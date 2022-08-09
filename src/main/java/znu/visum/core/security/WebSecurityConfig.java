package znu.visum.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import znu.visum.components.accounts.usecases.signup.domain.Signup;
import znu.visum.core.security.jwt.JwtAuthenticationFilter;
import znu.visum.core.security.jwt.JwtAuthorizationFilter;
import znu.visum.core.security.jwt.SecretInitializer;
import znu.visum.core.security.jwt.infrastructure.Auth0JwtService;
import znu.visum.core.security.jwt.infrastructure.RestAuthenticationEntryPoint;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final Signup accountService;
  private final PasswordEncoder passwordEncoder;

  private final SecretInitializer secretInitializer;

  private final AuthenticationEntryPoint entryPoint;

  @Autowired
  public WebSecurityConfig(
      Signup accountService,
      PasswordEncoder passwordEncoder,
      SecretInitializer secretInitializer,
      RestAuthenticationEntryPoint entryPoint) {
    this.accountService = accountService;
    this.passwordEncoder = passwordEncoder;
    this.secretInitializer = secretInitializer;
    this.entryPoint = entryPoint;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    var jwtService = new Auth0JwtService();

    http.csrf().disable();
    http.cors()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/accounts/sign-in", "/api/accounts")
        .permitAll()
        // .antMatchers(
        // HttpMethod.GET, "/v3/api-docs/*", "/v3/api-docs", "/swagger-ui.html",
        // "/swagger-ui/*")
        // .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(entryPoint)
        .and()
        .addFilter(
            new JwtAuthenticationFilter(
                this.secretInitializer.getJwtSecretKey(), authenticationManager(), jwtService))
        .addFilter(
            new JwtAuthorizationFilter(
                this.secretInitializer.getJwtSecretKey(), authenticationManager(), jwtService))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOrigin(this.secretInitializer.getAllowedOrigins());
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
