package com.krishimitra.backend.repository;

import com.krishimitra.backend.entity.FarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmHistoryRepository extends JpaRepository<FarmHistory, Long> {
    List<FarmHistory> findByUserUidOrderByCreatedAtDesc(Long userUid);
}
