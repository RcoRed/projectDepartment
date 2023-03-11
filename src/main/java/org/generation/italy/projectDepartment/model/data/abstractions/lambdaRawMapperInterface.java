package org.generation.italy.projectDepartment.model.data.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface lambdaRawMapperInterface {
    Object lambdaRawMapper(ResultSet rs) throws SQLException;
}
