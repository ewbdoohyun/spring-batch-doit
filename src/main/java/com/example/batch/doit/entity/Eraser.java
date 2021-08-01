package com.example.batch.doit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Eraser {
    @Id
    private Long id;

    private boolean isDeleted;

    private LocalDateTime createdDate;
}
