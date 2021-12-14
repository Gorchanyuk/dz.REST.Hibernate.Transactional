package hibernate.transactional.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_employee")
    @SequenceGenerator(name = "my_gen_employee", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_gen")
    private long id;
    private String name;
    private String surname;
    private int age;
    private String role;
    private BigDecimal salary;
    @ManyToOne
    private Organization organization;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                ", organization=" + organization.getName() +
                '}';
    }
}
