package org.generation.italy.projectDepartment.model.data.services.abstractions;

import org.generation.italy.projectDepartment.model.data.abstractions.DepartmentRepository;
import org.generation.italy.projectDepartment.model.data.exceptions.DataException;
import org.generation.italy.projectDepartment.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.projectDepartment.model.entities.Department;

public interface AbstractDepartmentService {

    Department saveDepartment(Department department) throws DataException;
    void deleteById(long id) throws DataException, EntityNotFoundException;
    Iterable<Department> findDepartmentByNameLike(String part) throws DataException;
}
