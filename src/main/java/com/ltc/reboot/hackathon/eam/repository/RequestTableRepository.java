package com.ltc.reboot.hackathon.eam.repository;

import com.ltc.reboot.hackathon.eam.entity.RequestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestTableRepository extends JpaRepository<RequestTable, Long> {

    @Modifying
    @Query("UPDATE RequestTable s SET s.status = :status WHERE s.JiraId = :id")
    void updateStudentName(@Param("id") String id, @Param("status") String status);
}
