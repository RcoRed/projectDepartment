package org.generation.italy.projectDepartment.model.data.abstractions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface lambdaSetStatementsInterface {
    PreparedStatement setStatements(String query, boolean returning, Object... params) throws SQLException;
}

