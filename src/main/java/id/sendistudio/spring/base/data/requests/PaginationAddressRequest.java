package id.sendistudio.spring.base.data.requests;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationAddressRequest {
    @Nullable
    private String country;

    @Nullable
    private String province;

    @Nullable
    private String city;

    @Nullable
    private String street;

    private Integer page;
    private Integer size;
}
