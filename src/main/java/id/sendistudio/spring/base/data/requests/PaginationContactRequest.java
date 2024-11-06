package id.sendistudio.spring.base.data.requests;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationContactRequest {
    private Optional<String> name;
    private Optional<String> email;
    private Optional<String> phone;

    private Integer page;
    private Integer size;
}
