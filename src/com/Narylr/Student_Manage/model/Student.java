package com.Narylr.Student_Manage.model;

/**
 * Student entity class
 * 学生实体类，用于表示学生的基本信息
 */
public class Student {
    /**
     * 学号
     */
    private String studentNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 无参构造函数
     */
    public Student() {
    }

    /**
     * 有参构造函数，用于初始化学生对象的所有属性
     *
     * @param studentNo 学号
     * @param name 姓名
     * @param age 年龄
     * @param gender 性别
     * @param grade 年级
     * @param telephone 联系电话
     * @param email 邮箱地址
     * @param address 家庭住址
     */
    public Student(String studentNo, String name, Integer age, String gender,
                   Integer grade, String telephone, String email, String address) {
        this.studentNo = studentNo;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
    }

    /**
     * 获取学号
     *
     * @return 学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学号
     *
     * @param studentNo 学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取年龄
     *
     * @return 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取年级
     *
     * @return 年级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * 设置年级
     *
     * @param grade 年级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取联系电话
     *
     * @return 联系电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置联系电话
     *
     * @param telephone 联系电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取邮箱地址
     *
     * @return 邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱地址
     *
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取家庭住址
     *
     * @return 家庭住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置家庭住址
     *
     * @param address 家庭住址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 重写toString方法，用于输出学生对象的信息
     *
     * @return 包含所有学生信息的字符串
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentNo='" + studentNo + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", grade=" + grade +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
