package hibernate.transactional.repository;

import hibernate.transactional.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

//    @Query("SELECT o FROM Organization o WHERE o.name = :name")
    @Query("FROM Organization WHERE name = :name")
    Optional<Organization> getByName(@Param("name") String name);
}
