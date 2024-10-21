package com.example.cellphonesclone.models;

//import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "score")
    private Integer score;

    @Column(name = "comment", length = 350)
    private String comment;

}
