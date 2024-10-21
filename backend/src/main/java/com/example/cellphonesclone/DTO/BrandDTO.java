package com.example.cellphonesclone.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class BrandDTO {
    @NotEmpty(message = "Brand's name cannot be empty")
    private String name;
}
