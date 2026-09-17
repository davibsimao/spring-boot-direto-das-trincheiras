package academy.devdojo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties connectionProperties;

    @Bean
//    @Profile("mysql")
    @Primary
    public Connection connectionMySql() {
        return new Connection(connectionProperties.url(), connectionProperties.username(), connectionProperties.password());
    }

    @Bean()
    @Profile("mongo")
    public Connection connectionMongo() {
        return new Connection(connectionProperties.url(),
                connectionProperties.username(),
                connectionProperties.password());
    }
}
