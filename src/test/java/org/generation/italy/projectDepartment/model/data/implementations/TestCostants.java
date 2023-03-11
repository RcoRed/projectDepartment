package org.generation.italy.projectDepartment.model.data.implementations;

import java.time.LocalDate;

public class TestCostants {
/*
    COSTANTI TEST
 */
    // ADDRESS
    public static final String A_STREET1 = "STREET_1";
    public static final String A_STREET2 = "STREET_2";
    public static final int A_HOUSE_NUMBER1 = 1;
    public static final String A_CITY1 = "CITY_1";
    public static final String A_CITY2 = "CITY_2";
    public static final String A_COUNTRY1 = "COUNTRY_2";
    public static final String A_COUNTRY2 = "COUNTRY_2";

    // DEPARTMENT
    public static final String D_NAME1 = "D_NAME_1";
    public static final String D_NAME2 = "D_NAME_2";
    public static final String D_NAME3 = "D_NAME_3";
    public static final int D_MAX_CAPACITY = 20;

    // EMPLOYEE
    public static final String E_NAME1 = "E_NAME_1";
    public static final String E_NAME2 = "E_NAME_2";
    public static final String E_SURNAME1 = "E_SURNAME_1";
    public static final String E_SURNAME2 = "E_SURNAME_2";
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
