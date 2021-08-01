package com.example.batch.doit.repository;

import com.example.batch.doit.entity.CsvImport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvImportRepository extends JpaRepository<CsvImport,Long> {
}
