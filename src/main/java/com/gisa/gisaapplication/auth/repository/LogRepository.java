package com.gisa.gisaapplication.auth.repository;
import com.gisa.gisaapplication.auth.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}