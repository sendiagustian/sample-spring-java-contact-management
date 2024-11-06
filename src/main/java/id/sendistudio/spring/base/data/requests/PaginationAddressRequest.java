package id.sendistudio.spring.base.data.requests;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationAddressRequest {
    private Optional<String> country;
    private Optional<String> province;
    private Optional<String> city;
    private Optional<String> street;

    private Integer page;
    private Integer size;
}
