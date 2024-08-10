package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserForm {



    @NotBlank(message = "Username is required")
    @Size(min=3,message = "Min 3 Characters is required")
    private String Name;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Min 6 Characters is required")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min = 8 , max = 12 , message = "Invalid Phone Number")
    private String phoneNumber;
    

}
