package id.sendistudio.spring.base.data.requests;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationContactRequest {
    @Nullable
    private String name;

    @Nullable
    private String email;

    @Nullable
    private String phone;

    @Nullable
    private Integer page;

    @Nullable
    private Integer size;
}
