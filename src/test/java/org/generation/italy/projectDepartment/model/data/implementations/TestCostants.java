package org.generation.italy.projectDepartment.model.data.implementations;

import java.time.LocalDate;

public class TestCostants {
/*
    COSTANTI TEST
 */
    // ADDRESS
    public static final String A_STREET1 = "Street 1";
    public static final String A_STREET2 = "Street 2";
    public static final int A_HOUSE_NUMBER1 = 1;
    public static final String A_CITY1 = "City 1";
    public static final String A_CITY2 = "City 2";
    public static final String A_COUNTRY1 = "Country 2";
    public static final String A_COUNTRY2 = "Country 2";

    // DEPARTMENT
    public static final String D_NAME1 = "D_Name 1";
    public static final String D_NAME2 = "D_Name 2";
    public static final String D_NAME3 = "D_Name 3";
    public static final int D_MAX_CAPACITY = 20;

    // EMPLOYEE
    public static final String E_NAME1 = "E_Name 1";
    public static final String E_NAME2 = "E_Name 2";
    public static final String E_SURNAME1 = "E_Name 1";
    public static final String E_SURNAME2 = "E_Name 2";
    public static final LocalDate HIRE_DATE = LocalDate.now();

/*
    QUERY
*/
    // INSERIMENTO
    public static final String FIND_DEPARTMENT_BY_ID = """
                SELECT id_department,name,max_capacity
                FROM department
                WHERE id_department = ?
                """;
    public static final String SAVE_EMPLOYEE_RETURNING_ID = """
            INSERT INTO employee(id_employee,name,surname,hire_date,sex,id_department)
            	VALUES(nextval('employee_sequence'),?,?,?,?,?)
            RETURNING id_employee
            """;


}
