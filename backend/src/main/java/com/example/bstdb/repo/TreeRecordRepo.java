package com.example.bstdb.repo;

import com.example.bstdb.entity.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRecordRepo extends JpaRepository<TreeRecord, Long> {
    Page<TreeRecord> findAllByOrderByCreatedAtDesc(Pageable pageable);
}


