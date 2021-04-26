package com.doni.genbe.repository;

import com.doni.genbe.model.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    @Query(value = "select * from t_setting where key = :key", nativeQuery = true)
    Setting findValue (String key);
}
