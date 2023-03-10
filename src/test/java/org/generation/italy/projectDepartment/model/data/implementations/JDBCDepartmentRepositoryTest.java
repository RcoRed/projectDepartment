package org.generation.italy.projectDepartment.model.data.implementations;

import org.generation.italy.projectDepartment.model.data.exceptions.DataException;
import org.generation.italy.projectDepartment.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.projectDepartment.model.entities.Address;
import org.generation.italy.projectDepartment.model.entities.Department;
import org.generation.italy.projectDepartment.model.entities.Employee;
import org.generation.italy.projectDepartment.model.entities.Sex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.generation.italy.projectDepartment.model.data.implementations.JDBCTestUtils.*;
import static org.generation.italy.projectDepartment.model.data.JDBCConstants.*;
import static org.generation.italy.projectDepartment.model.data.implementations.TestCostants.*;


class JDBCDepartmentRepositoryTest {
    Connection con;
    JDBCDepartmentRepository repo;
    Address address1;
    Address address2;
    Address address3;
    Department department1;
    Department department2;
    Department department3;
    Department department4;
    Employee employee1;
    Employee employee2;


    @BeforeEach
    void setUp() {
        try {
            //CREAZIONE ADDRESS
            address1 = new Address(0,A_STREET1,A_HOUSE_NUMBER1,A_CITY1,A_COUNTRY1);
            address2 = new Address(0,A_STREET2,A_HOUSE_NUMBER1+1,A_CITY2,A_COUNTRY2);
            //CREAZIONE DEPARTMENT
            department1 = new Department(0,D_NAME1,address1,D_MAX_CAPACITY);
            department2 = new Department(0,D_NAME2,address2,D_MAX_CAPACITY+10);
            department3 = new Department(0,D_NAME3,address1,D_MAX_CAPACITY+20);
            department4 = new Department(0,D_NAME3,address2,D_MAX_CAPACITY);
            //CREAZIONE EMPLOYEE
            employee1 = new Employee(0,E_NAME1,E_SURNAME1,HIRE_DATE, Sex.MALE,department3);
            employee2 = new Employee(0,E_NAME2,E_SURNAME2,HIRE_DATE, Sex.FEMALE,department4);
            //CREAZIONE CONNECTION
            con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            con.setAutoCommit(false);

            //INSERIMENTO ADDRESS
            int a_key1 = update(SAVE_ADDRESS_RETURNING_ID,con,true,
                    address1.getStreet(),address1.getHouseNumber(),address1.getCity(),address1.getCountry());
            address1.setId(a_key1);
            int a_key2 = update(SAVE_ADDRESS_RETURNING_ID,con,true,
                    address2.getStreet(),address2.getHouseNumber(),address2.getCity(),address2.getCountry());
            address2.setId(a_key2);
            //INSERIMENTO DEPARTMENT
            int d_key1 = update(SAVE_DEPARTMENT_RETURNING_ID,con,true,
                    department1.getName(),address1.getId(),department1.getMaxCapacity());
            department1.setId(d_key1);
            int d_key2 = update(SAVE_DEPARTMENT_RETURNING_ID,con,true,
                    department2.getName(),address2.getId(),department2.getMaxCapacity());
            department2.setId(d_key2);
            int d_key3 = update(SAVE_DEPARTMENT_RETURNING_ID,con,true,
                    department3.getName(),address1.getId(),department3.getMaxCapacity());
            department3.setId(d_key3);
            int d_key4 = update(SAVE_DEPARTMENT_RETURNING_ID,con,true,
                    department4.getName(),address2.getId(),department4.getMaxCapacity());
            department4.setId(d_key4);
            //INSERIMENTO EMPLOYEE
            int e_key1 = update(SAVE_EMPLOYEE_RETURNING_ID,con,true,
                    employee1.getName(),employee1.getSurname(), Date.valueOf(employee1.getHireDate()),employee1.getSex(),department3.getId());
            employee1.setId(e_key1);
            int e_key2 = update(SAVE_EMPLOYEE_RETURNING_ID,con,true,
                    employee2.getName(),employee2.getSurname(), Date.valueOf(employee2.getHireDate()),employee2.getSex(),department4.getId());
            employee2.setId(e_key2);

            repo = new JDBCDepartmentRepository(con);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Errore nel setUp",e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella tearDown",e);
        }
    }

    @Test
    void save_department_should_add_department() {
        try {
            Department dTest1 = new Department(0,D_NAME1+"test",address1,D_MAX_CAPACITY);
            dTest1 = repo.saveDepartment(dTest1);
            Optional<Department> dOptional = findDepartmentById(dTest1.getId(),con);
            assertTrue(dOptional.isPresent());
            Department dTest2 = dOptional.get();
            assertEquals(dTest1.getId(),dTest2.getId());
            assertEquals(dTest1.getName(),dTest2.getName());
            assertEquals(dTest1.getMaxCapacity(),dTest2.getMaxCapacity());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete_by_id_should_delete_department_when_present() {
        try {
            Optional<Department> dOptionalBefore = findDepartmentById(department1.getId(),con);
            assertTrue(dOptionalBefore.isPresent());
            repo.deleteById(department1.getId());
            Optional<Department> dOptionalAfter = findDepartmentById(department1.getId(),con);
            assertTrue(dOptionalAfter.isEmpty());
        } catch (DataException | EntityNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete_by_id_should_throw_if_department_absent() {
        try {
            Optional<Department> dOptional = findDepartmentById(0,con);
            assertTrue(dOptional.isEmpty());
            repo.deleteById(department1.getId());
        } catch (EntityNotFoundException ignored) {

        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findDepartmentByNameLike_should_find_department() {
        try {
            Iterable<Department> result = repo.findDepartmentByNameLike("m");
            Iterator<Department> it = result.iterator();
            assertTrue(it.hasNext());
            Department dTest1 = it.next();
            assertTrue(department3.getId() == dTest1.getId() || department4.getId() == dTest1.getId());
            assertTrue(it.hasNext());
            Department dTest2 = it.next();
            assertTrue(department3.getId() == dTest2.getId() || department4.getId() == dTest2.getId());
            assertFalse(it.hasNext());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
}