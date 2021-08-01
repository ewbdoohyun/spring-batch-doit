package com.example.batch.doit.job.eraser.config;

import com.example.batch.doit.entity.Eraser;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.batch.doit.job.eraser.config.EraserJobConfig.JOB_NAME;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class EraserJobConfig {
    public static final String JOB_NAME = "eraserJob";
    private static final String STEP_NAME = "eraserStep";
    public static final int CHUNK_SIZE = 500;

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job eraserJob(){
        return jobBuilderFactory.get(JOB_NAME)
                .start(eraserStep())
                .build();
    }

    @Bean
    @JobScope
    public Step eraserStep(){

        return stepBuilderFactory.get(STEP_NAME)
                .<Eraser,Eraser>chunk(CHUNK_SIZE)
                .reader(eraserReader())
                .processor(eraserProcessor())
                .writer(eraserWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Eraser> eraserReader(){
        return new JpaPagingItemReaderBuilder<Eraser>()
                .queryString("SELECT e FROM Eraser e WHERE e.isDeleted = false")
                .pageSize(CHUNK_SIZE)
                .entityManagerFactory(entityManagerFactory)
                .name("eraserReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Eraser, Eraser> eraserProcessor() {
        return item -> {
            item.setDeleted(true);
            return item;
        };
    }


    @Bean
    public JpaItemWriter<Eraser> eraserWriter(){

        JpaItemWriter<Eraser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
