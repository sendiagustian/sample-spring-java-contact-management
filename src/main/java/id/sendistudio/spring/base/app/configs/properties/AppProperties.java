package id.sendistudio.spring.base.app.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("application")
public class AppProperties {

    private String name;
    private String version;
    private Contact contact;

    @Getter
    @Setter
    public static class Contact {
        private String name;
        private String email;
    }
}
