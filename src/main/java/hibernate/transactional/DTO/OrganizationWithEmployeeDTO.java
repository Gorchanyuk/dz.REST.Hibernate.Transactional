package hibernate.transactional.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithEmployeeDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String url;
    @Valid
    private List<EmployeeDTO> employees;
}
