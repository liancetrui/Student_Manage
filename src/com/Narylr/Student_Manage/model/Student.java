package com.Narylr.Student_Manage.model;

/**
 * Student entity class
 */
public class Student {
    private String studentNo;
    private String name;
    private Integer age;
    private String gender;
    private Integer grade;
    private String telephone;
    private String email;
    private String address;

    public Student() {
    }

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

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
