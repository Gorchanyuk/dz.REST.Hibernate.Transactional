package hibernate.transactional.service.hibernate;

import hibernate.transactional.DTO.*;
import hibernate.transactional.controllers.EmployeeController;
import hibernate.transactional.models.Employee;
import hibernate.transactional.models.Organization;
import hibernate.transactional.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final EmployeeService employeeService;
    private final EmployeeController employeeController;
    private final ModelMapper modelMapper;


    //Создаем конвертер для приведения entity k DTO
    private OrganizationDTO convertToOrganizationDTO(Organization organization) {
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    //Создаем конвертер для приведения DTO k entity
    private Organization convertToOrganization(OrganizationDTO organizationDTO) {
        return modelMapper.map(organizationDTO, Organization.class);
    }

    //Вывод всех организаций
    public List<OrganizationDTO> index() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(this::convertToOrganizationDTO).collect(Collectors.toList());
    }

    //Вывод организации по имени
    public OrganizationDTO showByName(String name) {
        Organization organization = organizationRepository
                .getByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    //Вывод организации по id(возвращает OrganizationDTO)
    public OrganizationDTO show(long id) {
        Organization organization = getOrganizationById(id);
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    //Вывод организации по id(возвращает Organization, используется для методов этого класса)
    private Organization getOrganizationById(long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //Сохраняет новую организацию в БД
    public Organization save(OrganizationDTO organizationDTO) {
        return organizationRepository.save(modelMapper.map(organizationDTO, Organization.class));
    }

    //Сохраняет компанию вместе с сотрудниками
    @Transactional
    public void saveOrganizationAndEmployees(OrganizationWithEmployeeDTO request) {
        Organization organization = save(modelMapper.map(request, OrganizationDTO.class));
        for (EmployeeDTO employeeForOrgDTO : request.getEmployees()) {
            EmployeeDTO employeeDTO = modelMapper.map(employeeForOrgDTO, EmployeeDTO.class);
            employeeDTO.setOrganization(modelMapper.map(organization, OrganizationIdDTO.class));
            employeeService.save(employeeDTO);
        }

    }

    //Изменяет организацию в БД по заданному id
    public void update(long id, OrganizationDTO organizationDTO) {
        Organization organization = modelMapper.map(organizationDTO, Organization.class);
        organization.setId(id);
        organizationRepository.save(organization);
    }

    //Удаляет организацию из БД по заданному id
    public void delete(long id) {
        organizationRepository.deleteById(id);
    }


}
