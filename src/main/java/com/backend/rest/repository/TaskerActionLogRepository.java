package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.TaskerActionLog;

@Repository
public interface TaskerActionLogRepository extends JpaRepository<TaskerActionLog, Long>{

}
