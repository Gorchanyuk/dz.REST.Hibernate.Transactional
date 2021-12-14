package hibernate.transactional.controllers;

import hibernate.transactional.DTO.OrganizationDTO;
import hibernate.transactional.DTO.OrganizationWithEmployeeDTO;
import hibernate.transactional.service.hibernate.OrganizationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService, OrganizationService organizationServiceHibernate) {
        this.organizationService = organizationService;
    }

    @GetMapping()
    public List<OrganizationDTO> index() {
        return organizationService.index();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id) {
        return organizationService.show(id).toString();
    }


    @GetMapping("/name/{name}")
    public OrganizationDTO showByName(@PathVariable("name") String name){
        return organizationService.showByName(name);
    }

    @PostMapping()
    public void create(@Valid @RequestBody OrganizationDTO request) {
        organizationService.save(request);
    }

    @PostMapping("/create_organization_with_employees")
    public void createWithEmloyees(@Valid @RequestBody OrganizationWithEmployeeDTO request){
        organizationService.saveOrganizationAndEmployees(request);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") long id,
                       @Valid @RequestBody OrganizationDTO request) {
        organizationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        organizationService.delete(id);
    }
}
