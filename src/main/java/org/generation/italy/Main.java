package org.generation.italy;

import org.generation.italy.projectDepartment.model.data.implementations.JDBCDepartmentRepository;
import org.generation.italy.projectDepartment.view.UserInterfaceConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.generation.italy.projectDepartment.model.data.JDBCConstants.*;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {

        System.setProperty("spring.profiles.active", "jdbc");

        try (var ctx =
                     new AnnotationConfigApplicationContext
                             (Main.class)) {

            UserInterfaceConsole console = ctx.getBean(UserInterfaceConsole.class);
            console.start();
        }

    }

    @Bean
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}