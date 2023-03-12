package org.generation.italy.projectDepartment.model.data.implementations;

import org.generation.italy.projectDepartment.model.data.abstractions.DepartmentRepository;
import org.generation.italy.projectDepartment.model.data.abstractions.LambdaRawMapperInterface;
import org.generation.italy.projectDepartment.model.data.abstractions.LambdaSetStatementsInterface;
import org.generation.italy.projectDepartment.model.data.exceptions.DataException;
import org.generation.italy.projectDepartment.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.projectDepartment.model.entities.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;

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
                PreparedStatement st = setStatements(SAVE_DEPARTMENT_RETURNING_ID,true, lambdaSetStatements(),
                        department.getName(),department.getAddress().getId(),department.getMaxCapacity())
        ){
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
                PreparedStatement st = setStatements(DELETE_DEPARTMENT,false, lambdaSetStatements(),id)
        ){
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
                PreparedStatement st = setStatements(FIND_DEPARTMENT_BY_NAME_LIKE,false, lambdaSetStatements(),"%"+part+"%")
        )
        {
            try (ResultSet rs = st.executeQuery()){
                return (Iterable<Department>) rawMapper(rs, true, lambdaRawMapper());
            }
        } catch (SQLException e) {
            throw new DataException("Errore nel eliminazione di un Department",e);
        }
    }

    private LambdaSetStatementsInterface lambdaSetStatements(){
        return (query,returning,params) -> {
            PreparedStatement st = returning? con.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS)
                    :  con.prepareStatement(query);
            for(int i = 0; i < params.length; i++){
                if (params[i] instanceof Enum<?>){
                    st.setObject(i+1,params[i], Types.OTHER,0);
                }else {
                    st.setObject(i+1,params[i]);
                }
            }
            return st;
        };
    }

    private LambdaRawMapperInterface lambdaRawMapper(){
        return  (rs,returnACollection) -> {
            HashMap<Long,Department> departmentHashMap = new HashMap<>();
            Department department = null;
            Address address;
            Employee employee;
            long key = 0;
            while (rs.next()){
                try{
                    rs.findColumn("id_department");
                    key = rs.getLong("id_department");
                    if (!departmentHashMap.containsKey(key)){
                        department = new Department(
                                key,
                                rs.getString("d_name"),
                                rs.getInt("d_max_capacity")
                        );
                        departmentHashMap.put(department.getId(),department);
                    }
                }catch (SQLException e){
                }
                try{
                    rs.findColumn("id_address");
                    address = new Address(
                            rs.getLong("id_address"),
                            rs.getString("a_street"),
                            rs.getInt("a_house_number"),
                            rs.getString("a_city"),
                            rs.getString("a_country")
                    );
                    if (department != null){
                        department.setAddress(address);
                    }
                }catch (SQLException e){
                }
                try{
                    rs.findColumn("id_employee");
                    employee = new Employee(
                            rs.getLong("id_employee"),
                            rs.getString("e_name"),
                            rs.getString("e_surname"),
                            rs.getDate("e_hire_date").toLocalDate(),
                            Sex.valueOf(rs.getString("e_sex"))
                    );
                    if (department != null){
                        employee.setDepartment(department);
                        if (key!=0){
                            key = rs.getLong("id_department");
                            departmentHashMap.get(key).getEmployeeSet().add(employee);
                        }
                    }
                }catch (SQLException e){
                }
            }
            if (returnACollection){
                return departmentHashMap.values();
            }
            return departmentHashMap.get(key);
        };
    }

    public PreparedStatement setStatements(String query, boolean returning, LambdaSetStatementsInterface myFunction, Object... params) throws SQLException{
        return myFunction.setStatements(query,returning,params);
    }

    public Object rawMapper(ResultSet rs, boolean returnACollection, LambdaRawMapperInterface myFunction) throws SQLException {
        return myFunction.lambdaRawMapper(rs,returnACollection);
    }

    @Override
    public String toString() {
        return "JDBCDepartmentRepository -> Profile = jdbc";
    }
}
