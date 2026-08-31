package academy.devdojo.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean
    public Connection connectionMySql() {
        return new Connection("localhost", "devdojo", "goku");
    }
    @Bean(name = "connectionMongoDB")
    public Connection connectionMongo() {
        return new Connection("localhost", "devdojoMongo", "goku");
    }

}
