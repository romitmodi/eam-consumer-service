package hello.repository;

import hello.dto.ERoleEmployee;
import hello.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
    Optional<EmployeeRole> findByName(ERoleEmployee name);
}
