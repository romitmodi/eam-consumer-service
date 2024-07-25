package hello.repository;

import hello.dto.ERoleEmployee;
import hello.entity.AccessDetails;
import hello.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
    Optional<EmployeeRole> findByName(ERoleEmployee name);
}
