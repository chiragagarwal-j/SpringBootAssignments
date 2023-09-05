package com.learning.athome.entity.cycle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "Cycles", uniqueConstraints = { @UniqueConstraint(columnNames = { "brand" }) })
@Data
public class CycleStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    
    private int availableCycles;

}
