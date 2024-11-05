package id.sendistudio.spring.base.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
        @Bean
        OpenAPI customOpenAPI() {

                // Server devServer = new Server();
                // devServer.setUrl(devUrl);
                // devServer.setDescription("Server URL in Development environment");

                // Server prodServer = new Server();
                // prodServer.setUrl(prodUrl);
                // prodServer.setDescription("Server URL in Production environment");

                Contact contact = new Contact();
                contact.setEmail("sendiagustian@sendistudio.id");
                contact.setName("Sendi Agustian");

                License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

                Info info = new Info()
                                .title("Base Spring Boot API")
                                .version("v1.0.0")
                                .contact(contact)
                                .description("API documentation for API Base Spring Boot")
                                .license(mitLicense);

                return new OpenAPI().info(info);
        }
}
