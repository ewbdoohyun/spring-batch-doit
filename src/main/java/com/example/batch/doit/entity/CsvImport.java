package com.example.batch.doit.entity;

import com.example.batch.doit.job.csvimport.vo.CsvImportVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CsvImport {

    @Id
    private Long id;

    private String name;

    private String description;

    public static CsvImport from(CsvImportVo csvImportVo){
        CsvImport csvImport = new CsvImport();
        csvImport.setId(csvImportVo.getId());
        csvImport.setName(csvImportVo.getName());
        csvImport.setDescription(csvImportVo.getDescription());
        return csvImport;
    }
}
