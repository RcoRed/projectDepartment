package org.generation.italy.projectDepartment.model.data;

public class JDBCConstants {
    //CONNECTION
    public static final String URL = "jdbc:postgresql://localhost:5432/projectDepartment";
    public static final String USER_NAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";

    //INSERIMENTO
    public static final String SAVE_ADDRESS_RETURNING_ID = """
            INSERT INTO address(id_address,street,house_number,city,country)
            	VALUES(nextval('address_sequence'),?,?,?,?)
            RETURNING id_address
            """;
    public static final String SAVE_DEPARTMENT_RETURNING_ID = """
            INSERT INTO department(id_department,name,id_address,max_capacity)
            	VALUES(nextval('department_sequence'),?,?,?)
            RETURNING id_department
            """;

    //QUERY UTILS
    public static final String DELETE_DEPARTMENT = """
            DELETE FROM department
            WHERE id_department = ?
            """;
    public static final String FIND_DEPARTMENT_BY_NAME_LIKE = """
            SELECT
            id_department,d.name as d_name,d.max_capacity as d_max_capacity,
            e.id_employee,e.name as e_name,e.surname as e_surname,e.hire_date as e_hire_date,e.sex as e_sex,
            id_address,a.street as a_street,a.house_number as a_house_number,a.city as a_city,a.country as a_country
            FROM department as d JOIN employee as e
            USING (id_department)
            JOIN address as a
            USING (id_address)
            WHERE d.name LIKE ?
            """;
}
