package id.sendistudio.spring.base.data.views;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginView {
    private Optional<String> token;
    private Optional<Long> expiredAt;
}
