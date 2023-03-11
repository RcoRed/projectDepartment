package org.generation.italy.projectDepartment.model.data.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface LambdaRawMapperInterface {
    Object lambdaRawMapper(ResultSet rs,boolean returnACollection) throws SQLException;
}
