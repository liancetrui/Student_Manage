package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Data Access Object
 * 提供对学生信息的数据库操作接口，包括增删改查等基本功能。
 */
public class StudentDAO {
    private final Connection connection;

    /**
     * 构造方法：初始化数据库连接对象
     * 使用单例模式获取全局唯一的数据库连接实例。
     */
    public StudentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * 根据学号查找学生信息
     *
     * @param studentNo 学生学号（唯一标识）
     * @return 返回对应的学生对象；若未找到则返回null
     * @throws SQLException 数据库访问异常时抛出
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
     * 插入新的学生记录到数据库中
     *
     * @param student 待插入的学生对象
     * @return 操作成功返回true，失败返回false
     * @throws SQLException 数据库访问异常时抛出
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
     * 更新指定学生的全部信息
     *
     * @param student 包含更新后数据的学生对象
     * @return 操作成功返回true，失败返回false
     * @throws SQLException 数据库访问异常时抛出
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
     * 更新指定学生的某个字段值
     *
     * @param studentNo   学生学号
     * @param fieldName   要更新的字段名称
     * @param value       新的字段值
     * @return 操作成功返回true，失败返回false
     * @throws SQLException 数据库访问异常时抛出
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
     * 删除指定学号的学生记录
     *
     * @param studentNo 学生学号
     * @return 操作成功返回true，失败返回false
     * @throws SQLException 数据库访问异常时抛出
     */
    public boolean delete(String studentNo) throws SQLException {
        String sql = "DELETE FROM student WHERE s_NO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentNo);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 查询所有学生的信息列表
     *
     * @return 所有学生组成的列表集合
     * @throws SQLException 数据库访问异常时抛出
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
     * 判断指定学号的学生是否存在
     *
     * @param studentNo 学生学号
     * @return 若存在返回true，否则返回false
     * @throws SQLException 数据库访问异常时抛出
     */
    public boolean exists(String studentNo) throws SQLException {
        return findByNo(studentNo) != null;
    }

    /**
     * 将查询结果集中的当前行映射为Student对象
     *
     * @param rs 结果集对象，需确保已定位至有效行
     * @return 映射后的Student对象
     * @throws SQLException 数据库访问异常或字段读取错误时抛出
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
