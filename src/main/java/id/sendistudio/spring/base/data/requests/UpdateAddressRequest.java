package id.sendistudio.spring.base.data.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {
    
    @Max(200)
    private String street;
    
    @Max(100)
    private String city;
    
    @Max(100)
    private String province;
    
    @NotBlank
    @Max(100)
    private String country;
    
    @Max(10)
    private String postalCode;
}
