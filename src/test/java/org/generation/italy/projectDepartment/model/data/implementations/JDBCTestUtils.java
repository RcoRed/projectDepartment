package org.generation.italy.projectDepartment.model.data.implementations;

import org.generation.italy.projectDepartment.model.entities.Department;

import java.sql.*;
import java.util.Optional;

import static org.generation.italy.projectDepartment.model.data.implementations.TestCostants.*;

public class JDBCTestUtils {

    public static Optional<Department> findDepartmentById(long id, Connection con) {
        try (
                PreparedStatement st = con.prepareStatement(FIND_DEPARTMENT_BY_ID);
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createDepartmentFrom(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Department createDepartmentFrom(ResultSet rs) throws SQLException {
        return new Department(
                rs.getLong("id_department"),
                rs.getString("name"),
                rs.getInt("max_capacity"));
    }

    public static int update(String query, Connection con, boolean inserting, Object... params){
        try (PreparedStatement st = inserting? con.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS)
                :  con.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                if (params[i] instanceof Enum<?>){
                    st.setObject(i+1,params[i], Types.OTHER,0);
                }else {
                    st.setObject(i+1,params[i]);
                }
            }
            if(inserting){
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    keys.next();
                    long key = keys.getLong(1);
                    return (int) key;
                }
            }else {
                return st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
