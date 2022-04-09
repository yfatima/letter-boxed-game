package com.example.letterboxed.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerDTO {

    private String userName; 
    private String password;
    private String email;
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public CharSequence getPassword() {
        return password;
    }
}