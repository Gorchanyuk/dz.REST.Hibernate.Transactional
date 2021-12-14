package hibernate.transactional.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String url;
}
