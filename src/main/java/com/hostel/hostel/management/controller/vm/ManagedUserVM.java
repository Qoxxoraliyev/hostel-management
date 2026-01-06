package com.hostel.hostel.management.controller.vm;

import com.hostel.hostel.management.service.dto.AdminUserDTO;
import jakarta.validation.constraints.Size;

public class ManagedUserVM extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH=4;

    public static final int PASSWORD_MAX_LENGTH=100;

    @Size(min = PASSWORD_MIN_LENGTH,max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "ManagedUserVM{"+super.toString()+"}";
    }

}
