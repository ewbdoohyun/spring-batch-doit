package com.example.batch.doit.job.csvimport;

import com.example.batch.doit.entity.CsvImport;
import com.example.batch.doit.job.csvimport.vo.CsvImportVo;
import com.example.batch.doit.repository.CsvImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@StepScope
@Component
public class CsvImportWriter implements ItemWriter<CsvImportVo>, StepExecutionListener {

    @Autowired
    CsvImportRepository csvImportRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        List<CsvImport> csvImports =csvImportRepository.findAll();
        for(CsvImport csvImport : csvImports){
            log.info("database item : {}",csvImport);
        }
        log.info("동대동 데이터 완료 : new - {}, update - {}, maintain - {}, total - {}");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends CsvImportVo> items) {
        for (CsvImportVo item : items) {
            log.info("item : {}",item);
            csvImportRepository.save(CsvImport.from(item));
        }
        log.info("동대동 데이터 현재 cnt new - {}, update - {}, maintain - {}, total - {}");
    }
}
