package com.example.cellphonesclone.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    @JsonProperty("user_address")
    private  String userAddress;

    @JsonProperty("user_password")
    @NotBlank(message = "Password cannot be blank!")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountID;


    @JsonProperty("google_account_id")
    private Integer googleAccountID;

    @NotNull(message = "Role ID is required!")
    @JsonProperty("role_id")
    private Long roleID;
}
