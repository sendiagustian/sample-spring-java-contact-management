package id.sendistudio.spring.base.data.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String username;
}
