package com.krishimitra.backend.repository;
import java.util.List;
import com.krishimitra.backend.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByUserUid(Long userUid);
}