package org.generation.italy.projectDepartment.view;

import org.generation.italy.projectDepartment.model.data.services.abstractions.AbstractDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInterfaceConsole {

    AbstractDepartmentService service;

    @Autowired
    public UserInterfaceConsole(AbstractDepartmentService service) {
        this.service = service;
    }

    public void start(){
        System.out.println("start");
        System.out.println(service);
    }

}
