package com.workintech.jpa_services.validations;

import com.workintech.jpa_services.exceptions.StudentException;
import com.workintech.jpa_services.user.Role;
import org.springframework.http.HttpStatus;

public class RoleValidation {
    public static void roleNullControl(Role role) {
        if(role==null||role.getAuthority()==null|| role.getAuthority().toString().isBlank()){
            throw new StudentException("Rol veya yetki boş olamaz !", HttpStatus.BAD_REQUEST);
        }
    }
}
