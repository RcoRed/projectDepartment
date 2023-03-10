package org.generation.italy.projectDepartment.model.data.implementations;

import org.generation.italy.projectDepartment.model.data.abstractions.DepartmentRepository;
import org.generation.italy.projectDepartment.model.data.exceptions.DataException;
import org.generation.italy.projectDepartment.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.projectDepartment.model.entities.Address;
import org.generation.italy.projectDepartment.model.entities.Department;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.generation.italy.projectDepartment.model.data.JDBCConstants.*;

@Repository
@Profile("jdbc")
public class JDBCDepartmentRepository implements DepartmentRepository {
    private Connection con;

    public JDBCDepartmentRepository(Connection con) {
        this.con = con;
    }

    @Override
    public Department saveDepartment(Department department) throws DataException {
        try (
                PreparedStatement st = con.prepareStatement(SAVE_DEPARTMENT_RETURNING_ID,Statement.RETURN_GENERATED_KEYS);
        ){
            st.setString(1,department.getName());
            st.setLong(2,department.getAddress().getId());
            st.setInt(3,department.getMaxCapacity());
            st.executeUpdate();
            try (ResultSet key = st.getGeneratedKeys()){
                key.next();
                department.setId(key.getLong(1));
            }
            return department;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataException("Errore nel slavataggio di Department",e);
        }
    }

    @Override
    public void deleteById(long id) throws DataException,EntityNotFoundException{
        try (
                PreparedStatement st = con.prepareStatement(DELETE_DEPARTMENT)
        ){
            st.setLong(1,id);
            int rs = st.executeUpdate();
            if (rs != 1){
                throw new EntityNotFoundException("Errore non è stato trovato nessun department con ID = " + id);
            }
        } catch (SQLException e) {
            throw new DataException("Errore nel eliminazione di un Department",e);
        }
    }

    @Override
    public Iterable<Department> findDepartmentByNameLike(String part) throws DataException {
        try (
                PreparedStatement st = con.prepareStatement(FIND_DEPARTMENT_BY_NAME_LIKE)
        ){
            st.setString(1,"%"+part+"%");
            try (ResultSet rs = st.executeQuery()){
                List<Department> departmentList = new ArrayList<>();
                while (rs.next()){
                    departmentList.add(databaseToDepartment(rs));
                }
                return departmentList;
            }
        } catch (SQLException e) {
            throw new DataException("Errore nel eliminazione di un Department",e);
        }
    }

    private static Department databaseToDepartment(ResultSet rs) throws SQLException {
        Address address = new Address(
                rs.getLong("id_address"),
                rs.getString("a_street"),
                rs.getInt("a_house_number"),
                rs.getString("a_city"),
                rs.getString("a_country")
        );

        return new Department(
                rs.getLong("id_department"),
                rs.getString("d_name"),
                address,
                rs.getInt("d_max_capacity")
        );
    }
}
