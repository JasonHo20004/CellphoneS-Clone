package com.example.cellphonesclone.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.cellphonesclone.models.Brand;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("brand")
    private Brand brand;
}

