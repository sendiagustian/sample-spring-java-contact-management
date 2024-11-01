package id.sendistudio.spring.base.constants;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ExcludeEndpoint {

    private List<String> excludes = new ArrayList<>();

    public ExcludeEndpoint() {
        excludes.add("/v3/**");
        excludes.add("/swagger-ui/**");
        excludes.add("/v3/api-docs/**");
        excludes.add("/swagger-ui/**");
        excludes.add("/v3/api-docs");
        excludes.add("/swagger-ui.html");
        excludes.add("/favicon.ico");
        excludes.add("/swagger-resources");
        excludes.add("/configuration/security");
        excludes.add("/swagger-resources");
        excludes.add("/swagger-ui");
        excludes.add("/api/v1/auth/login");
        excludes.add("/api/v1/auth/register");
        excludes.add("/api/v1/auth/logout");
    }

}
