package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.StudentDAO;
import com.Narylr.Student_Manage.model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Student Service
 * 学生服务类，提供学生信息的业务逻辑处理
 */
public class StudentService {
    /**
     * 学生数据访问对象，用于与数据库交互
     */
    private final StudentDAO studentDAO;

    /**
     * 构造方法，初始化学生数据访问对象
     */
    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    /**
     * 添加新学生
     * @param student 要添加的学生对象
     * @return 添加成功返回true，否则返回false
     * @throws SQLException 数据库操作异常或学生已存在时抛出异常
     */
    public boolean addStudent(Student student) throws SQLException {
        // 检查学生是否已存在
        if (studentDAO.exists(student.getStudentNo())) {
            throw new SQLException("Student with number " + student.getStudentNo() + " already exists");
        }
        return studentDAO.insert(student);
    }

    /**
     * 更新学生信息
     * @param student 包含更新信息的学生对象
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库操作异常或学生不存在时抛出异常
     */
    public boolean updateStudent(Student student) throws SQLException {
        if (!studentDAO.exists(student.getStudentNo())) {
            throw new SQLException("Student with number " + student.getStudentNo() + " does not exist");
        }
        return studentDAO.update(student);
    }

    /**
     * 更新学生特定字段
     * @param studentNo 学号
     * @param fieldName 要更新的字段名
     * @param value 新的字段值
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库操作异常或学生不存在时抛出异常
     */
    public boolean updateStudentField(String studentNo, String fieldName, String value) throws SQLException {
        if (!studentDAO.exists(studentNo)) {
            throw new SQLException("Student with number " + studentNo + " does not exist");
        }
        return studentDAO.updateField(studentNo, fieldName, value);
    }

    /**
     * 删除学生
     * @param studentNo 要删除的学生学号
     * @return 删除成功返回true，否则返回false
     * @throws SQLException 数据库操作异常或学生不存在时抛出异常
     */
    public boolean deleteStudent(String studentNo) throws SQLException {
        if (!studentDAO.exists(studentNo)) {
            throw new SQLException("Student with number " + studentNo + " does not exist");
        }
        return studentDAO.delete(studentNo);
    }

    /**
     * 根据学号获取学生信息
     * @param studentNo 学生学号
     * @return 对应的学生对象
     * @throws SQLException 数据库操作异常
     */
    public Student getStudent(String studentNo) throws SQLException {
        return studentDAO.findByNo(studentNo);
    }

    /**
     * 获取所有学生信息
     * @return 包含所有学生信息的列表
     * @throws SQLException 数据库操作异常
     */
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.findAll();
    }

    /**
     * 检查学生是否存在
     * @param studentNo 学生学号
     * @return 存在返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean studentExists(String studentNo) throws SQLException {
        return studentDAO.exists(studentNo);
    }
}
