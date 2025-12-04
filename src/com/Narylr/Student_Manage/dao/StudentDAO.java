package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Data Access Object
 */
public class StudentDAO {
    private final Connection connection;

    public StudentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Find student by number
     */
    public Student findByNo(String studentNo) throws SQLException {
        String sql = "SELECT * FROM student WHERE s_NO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }
        }
        return null;
    }

    /**
     * Insert new student
     */
    public boolean insert(Student student) throws SQLException {
        String sql = "INSERT INTO student (s_NO, s_NAME, s_AGE, s_GENDER, s_GRADE, s_TEL, s_EMAIL, s_ADDRESS) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student.getStudentNo());
            ps.setString(2, student.getName());
            ps.setObject(3, student.getAge());
            ps.setString(4, student.getGender());
            ps.setObject(5, student.getGrade());
            ps.setString(6, student.getTelephone());
            ps.setString(7, student.getEmail());
            ps.setString(8, student.getAddress());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Update student information
     */
    public boolean update(Student student) throws SQLException {
        String sql = "UPDATE student SET s_NAME = ?, s_AGE = ?, s_GENDER = ?, s_GRADE = ?, " +
                     "s_TEL = ?, s_EMAIL = ?, s_ADDRESS = ? WHERE s_NO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setObject(2, student.getAge());
            ps.setString(3, student.getGender());
            ps.setObject(4, student.getGrade());
            ps.setString(5, student.getTelephone());
            ps.setString(6, student.getEmail());
            ps.setString(7, student.getAddress());
            ps.setString(8, student.getStudentNo());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Update specific field
     */
    public boolean updateField(String studentNo, String fieldName, String value) throws SQLException {
        String sql = "UPDATE student SET " + fieldName + " = ? WHERE s_NO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, studentNo);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Delete student
     */
    public boolean delete(String studentNo) throws SQLException {
        String sql = "DELETE FROM student WHERE s_NO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentNo);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Find all students
     */
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        }
        return students;
    }

    /**
     * Check if student exists
     */
    public boolean exists(String studentNo) throws SQLException {
        return findByNo(studentNo) != null;
    }

    /**
     * Map ResultSet to Student object
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentNo(rs.getString("s_NO"));
        student.setName(rs.getString("s_NAME"));
        student.setAge(rs.getObject("s_AGE", Integer.class));
        student.setGender(rs.getString("s_GENDER"));
        student.setGrade(rs.getObject("s_GRADE", Integer.class));
        student.setTelephone(rs.getString("s_TEL"));
        student.setEmail(rs.getString("s_EMAIL"));
        student.setAddress(rs.getString("s_ADDRESS"));
        return student;
    }
}
