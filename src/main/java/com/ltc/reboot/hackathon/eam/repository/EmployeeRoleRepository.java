package com.ltc.reboot.hackathon.eam.repository;

import com.ltc.reboot.hackathon.eam.dto.ERoleEmployee;
import com.ltc.reboot.hackathon.eam.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
    Optional<EmployeeRole> findByName(ERoleEmployee name);
}
