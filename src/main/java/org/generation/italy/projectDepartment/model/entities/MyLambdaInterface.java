package org.generation.italy.projectDepartment.model.entities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface MyLambdaInterface {
    PreparedStatement setParameter(String query,boolean returning,Object... params) throws SQLException;
}

