package org.generation.italy.projectDepartment.model.data.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@FunctionalInterface
public interface LambdaRawMapperInterface2 {
    ArrayList<String> lambdaRawMapper(String query) throws SQLException;
}
