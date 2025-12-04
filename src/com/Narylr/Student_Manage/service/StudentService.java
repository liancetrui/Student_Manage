package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.StudentDAO;
import com.Narylr.Student_Manage.model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Student Service
 */
public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    /**
     * Add new student
     */
    public boolean addStudent(Student student) throws SQLException {
        // Check if student already exists
        if (studentDAO.exists(student.getStudentNo())) {
            throw new SQLException("Student with number " + student.getStudentNo() + " already exists");
        }
        return studentDAO.insert(student);
    }

    /**
     * Update student
     */
    public boolean updateStudent(Student student) throws SQLException {
        if (!studentDAO.exists(student.getStudentNo())) {
            throw new SQLException("Student with number " + student.getStudentNo() + " does not exist");
        }
        return studentDAO.update(student);
    }

    /**
     * Update specific field
     */
    public boolean updateStudentField(String studentNo, String fieldName, String value) throws SQLException {
        if (!studentDAO.exists(studentNo)) {
            throw new SQLException("Student with number " + studentNo + " does not exist");
        }
        return studentDAO.updateField(studentNo, fieldName, value);
    }

    /**
     * Delete student
     */
    public boolean deleteStudent(String studentNo) throws SQLException {
        if (!studentDAO.exists(studentNo)) {
            throw new SQLException("Student with number " + studentNo + " does not exist");
        }
        return studentDAO.delete(studentNo);
    }

    /**
     * Get student by number
     */
    public Student getStudent(String studentNo) throws SQLException {
        return studentDAO.findByNo(studentNo);
    }

    /**
     * Get all students
     */
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.findAll();
    }

    /**
     * Check if student exists
     */
    public boolean studentExists(String studentNo) throws SQLException {
        return studentDAO.exists(studentNo);
    }
}
