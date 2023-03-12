package org.generation.italy.projectDepartment.model.data.services.implementations;

import org.generation.italy.projectDepartment.model.data.abstractions.DepartmentRepository;
import org.generation.italy.projectDepartment.model.data.exceptions.DataException;
import org.generation.italy.projectDepartment.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.projectDepartment.model.data.services.abstractions.AbstractDepartmentService;
import org.generation.italy.projectDepartment.model.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardDepartmentService implements AbstractDepartmentService {

    private DepartmentRepository repo;

    @Autowired
    public StandardDepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Department saveDepartment(Department department) throws DataException {
        return null;
    }

    @Override
    public void deleteById(long id) throws DataException, EntityNotFoundException {

    }

    @Override
    public Iterable<Department> findDepartmentByNameLike(String part) throws DataException {
        return null;
    }

    @Override
    public String toString() {
        return "Service = StandardDepartmentService -> Repository = " + repo;
    }
}
