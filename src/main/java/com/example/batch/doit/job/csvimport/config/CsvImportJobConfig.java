package com.example.batch.doit.job.csvimport.config;

import com.example.batch.doit.job.csvimport.CsvImportWriter;
import com.example.batch.doit.job.csvimport.vo.CsvImportVo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CsvImportJobConfig {
    private static final String JOB_NAME = "csvImportJob";
    private static final String STEP_NAME = "safeNumberDeallocateStep";
    public static final int CHUNK_SIZE = 500;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CsvImportWriter csvImportWriter;

    @Bean
    public Job csvImportJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(csvImportStep())
                .build();

    }

    @Bean
    public Step csvImportStep() {

        return stepBuilderFactory.get(STEP_NAME)
                .<CsvImportVo, CsvImportVo>chunk(CHUNK_SIZE)
                .reader(csvImportReader())
                .writer(csvImportWriter)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CsvImportVo> csvImportReader() {
        return new FlatFileItemReaderBuilder<CsvImportVo>()
                .name("addressFareReader")
                .resource(new ClassPathResource("data.csv"))
                .delimited().delimiter(",")
                .names("id", "name", "description")
                .linesToSkip(1)
                .targetType(CsvImportVo.class)
                .build();
    }
}
