package com.example.cellphonesclone.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Data//to string
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
