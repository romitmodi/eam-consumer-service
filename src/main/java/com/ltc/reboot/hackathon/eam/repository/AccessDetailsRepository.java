package com.ltc.reboot.hackathon.eam.repository;

import com.ltc.reboot.hackathon.eam.entity.AccessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessDetailsRepository extends JpaRepository<AccessDetails, Long> {

    @Query("select u from AccessDetails u where u.role.id =:#{#roleId}")
    List<AccessDetails> findByRoleId(@Param("roleId") Integer roleId);
}
