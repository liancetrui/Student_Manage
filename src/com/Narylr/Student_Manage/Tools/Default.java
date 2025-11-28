package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.Narylr.Student_Manage.Tools.firstLogin.rootUser;

public class Default {
    private static final String sql = "INSERT INTO permissions (p_ID, p_NAME, p_WRITE, p_READ) VALUES (?, ?, ?, ?)";

    public Default(String p_id,String p_name) throws SQLException {
        if (rootUser){
            PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
            preparedStatement.setString(1, p_id);
            preparedStatement.setString(2, p_name);
            String p_write = "N";
            preparedStatement.setString(3, p_write);
            String p_read = "Y";
            preparedStatement.setString(4, p_read);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }else {
            PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
            preparedStatement.setString(1, p_id);
            preparedStatement.setString(2, p_name);
            String p_write = "Y";
            preparedStatement.setString(3, p_write);
            String p_read = "Y";
            preparedStatement.setString(4, p_read);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
    }
}