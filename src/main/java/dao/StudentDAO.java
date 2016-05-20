package main.java.dao;

import main.java.Gender;
import main.java.Student;
import org.joda.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import static main.java.dao.GeneralDAO.connection;
import static main.java.dao.GeneralDAO.getConnection;

public class StudentDAO {


    public static void addStudent(Student student) throws SQLException {
        getConnection();
        String sql = "INSERT INTO Students (studentId, Surname, Name, Gender, DateOfBirth, groupid) VALUES (?,?,?,?,?,?)";
        PreparedStatement prepSt = connection.prepareStatement(sql);
        prepSt.setInt(1, student.getStudentId());
        prepSt.setString(2, student.getSurName());
        prepSt.setString(3, student.getName());
        prepSt.setString(4, student.getGender().toString());
        prepSt.setDate(5, java.sql.Date.valueOf(String.valueOf(student.getDateOfBirth())));
        prepSt.setInt(6, student.getGroupId());

        prepSt.executeUpdate();
        System.out.println("Студента успішно додано!");
    }

    public static boolean checkUnique(Student s) throws SQLException {
        getConnection();
        Statement stmt = connection.createStatement();
        String sql = "SELECT studentid FROM students WHERE surname='" + s.getSurName() + "' AND name='" + s.getName() + "'";
        ResultSet rs = stmt.executeQuery(sql);
        return !rs.next();
    }

    public static void updateStudent(Student student) throws SQLException {
        getConnection();
        System.out.println("Оновлення інформації про студента...");
        String sql = "UPDATE Students SET surname = ?, name = ?, gender = ?, dateofbirth = ? " +
                "WHERE studentid = " + student.getStudentId();
        PreparedStatement prepSt = connection.prepareStatement(sql);
        prepSt.setString(1, student.getSurName());
        prepSt.setString(2, student.getName());
        prepSt.setString(3, student.getGender().toString());
        prepSt.setDate(4, java.sql.Date.valueOf(String.valueOf(student.getDateOfBirth())));

        prepSt.executeUpdate();
        System.out.println("Інформацію про студента успішно оновлено!");
    }

    public static int getStudentsCount() {
        int studCount = 0;
        try {
            getConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT COUNT (studentid) FROM students";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                studCount = rs.getInt(1);
            }
            return studCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studCount;
    }

    public static Student getStudent(int studentId) throws SQLException {
        getConnection();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM students WHERE studentid = " + studentId;
        ResultSet rs = stmt.executeQuery(sql);
        Student student = new Student();
        if (rs.next()) {
            student.setStudentId(rs.getInt("studentid"));
            student.setName(rs.getString("name"));
            student.setSurName(rs.getString("surname"));
            student.setDateOfBirth(LocalDate.parse(rs.getDate("dateofbirth").toString()));
            student.setGender(Gender.valueOf(rs.getString("gender")));
            student.setGroupId(rs.getInt("groupid"));
            student.getAge();
        }
        return student;
    }

    public static void moveStudentToGroup(GeneralDAO generalDAO, int studentId, int groupId) throws SQLException {
        getConnection();
        String sql = "UPDATE Students SET groupid = ? WHERE studentid = " + studentId;
        generalDAO.prepSt = connection.prepareStatement(sql);
        generalDAO.prepSt.setInt(1, groupId);

        generalDAO.prepSt.executeUpdate();
    }

    public static TreeSet<Student> getAllStudents() throws SQLException {
        getConnection();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM students";
        ResultSet rs = stmt.executeQuery(sql);
        TreeSet<Student> students = new TreeSet<>();
        while (rs.next()) {
            Student student = new Student();
            student.setStudentId(rs.getInt("studentid"));
            student.setName(rs.getString("name"));
            student.setSurName(rs.getString("surname"));
            student.setDateOfBirth(LocalDate.parse(rs.getDate("dateofbirth").toString()));
            student.setGender(Gender.valueOf(rs.getString("gender")));
            student.setGroupId(rs.getInt("groupid"));
            student.getAge();
            students.add(student);
        }
        return students;
    }

    public static void deleteStudent(String surname, String name) throws SQLException {
        getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM students WHERE surname LIKE '%" + surname.substring(3) + "' AND name='" + name + "'";
        stmt.executeUpdate(sql);
    }
}
