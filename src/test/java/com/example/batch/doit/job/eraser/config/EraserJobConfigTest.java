package com.example.batch.doit.job.eraser.config;

import com.example.batch.doit.entity.Eraser;
import com.example.batch.doit.repository.EraserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"job.name=" + EraserJobConfig.JOB_NAME})
@Slf4j
public class EraserJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private EraserRepository eraserRepository;

    @Test
    public void 테스트() throws Exception {
        System.out.println("Hell0");

        //given
        for (long i = 0; i < 20; i++) {
            eraserRepository.save(new Eraser(i, true, LocalDateTime.now()));
        }
        System.out.println("Hello2");
        for (long i = 20; i < 50; i++) {
            eraserRepository.save(new Eraser(i, false, LocalDateTime.now()));
        }
        eraserRepository.flush();
        System.out.println("Hell3");
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        List<Eraser> eraserList = eraserRepository.findAll();

        assertTrue(eraserRepository.findAll().stream().filter(eraser -> !eraser.isDeleted())
                .count() == 0);

    }
}