package id.sendistudio.spring.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import id.sendistudio.spring.base.app.configs.properties.AppProperties;
import id.sendistudio.spring.base.app.configs.properties.DatabaseProperties;
import id.sendistudio.spring.base.app.configs.properties.ServerProperties;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties({ AppProperties.class, DatabaseProperties.class, ServerProperties.class })
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
