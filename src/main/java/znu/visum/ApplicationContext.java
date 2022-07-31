package znu.visum;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan
@EnableScheduling
public class ApplicationContext {
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @ConditionalOnProperty(name = "visum.flyway.repair-on-migrate", havingValue = "false")
  public FlywayMigrationStrategy standardStrategy() {
    return flyway -> {
      flyway.baseline();
      flyway.migrate();
    };
  }

  @Bean
  @ConditionalOnProperty(name = "visum.flyway.repair-on-migrate", havingValue = "true")
  public FlywayMigrationStrategy repairStrategy() {
    return flyway -> {
      flyway.baseline();
      flyway.repair();
      flyway.migrate();
    };
  }
}
