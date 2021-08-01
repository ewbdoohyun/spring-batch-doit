package com.example.batch.doit.job.csvimport.vo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CsvImportVo {

    private Long id;
    private String name;
    private String description;
}
