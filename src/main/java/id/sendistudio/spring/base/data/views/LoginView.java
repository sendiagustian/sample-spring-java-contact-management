package id.sendistudio.spring.base.data.views;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginView {
    @Nullable
    private String token;

    private Long expiredAt;
}
