package com.doni.genbe.repository;

import com.doni.genbe.model.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchedulerRepository extends JpaRepository<Scheduler, String> {
    @Query(value = "select is_running from t_scheduler where schid = :id", nativeQuery = true)
    Boolean findValueById(String id);
}
