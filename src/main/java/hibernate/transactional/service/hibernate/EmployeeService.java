package hibernate.transactional.service.hibernate;

import hibernate.transactional.DTO.EmployeeDTO;
import hibernate.transactional.repository.EmployeeRepository;
import hibernate.transactional.models.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService{

    private final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;


    public List<EmployeeDTO> index (){
        List<Employee> employees =  employeeRepository.findAll();
        return  employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public List<EmployeeDTO> showBetween(int start, int stop){
        List<Employee> employees = employeeRepository.findAllByAgeBetween(start, stop);
        return  employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO show(long id) {
        Employee employee = getEmployeeById(id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }
    //метод для других методов этого класса
    private Employee getEmployeeById(long id){
        return employeeRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(@Valid EmployeeDTO employeeDTO){
//        if(true) throw new RuntimeException("asfsdf");
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));

    }

    public void update(long id, EmployeeDTO employeeDTO) {
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

}
