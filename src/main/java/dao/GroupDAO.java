package main.java.dao;

import main.java.Gender;
import main.java.Group;
import main.java.Student;
import org.joda.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class GroupDAO {

    public static TreeSet<Student> getStudentsFromGroup(int groupid) throws SQLException {
        GeneralDAO.getConnection();
        Statement stmt = GeneralDAO.connection.createStatement();
        String sql = "SELECT * FROM students WHERE groupid = " + groupid;
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

    public static void addAllStudentsFromGroup(Group group) throws SQLException {
        GeneralDAO.getConnection();
        for (Student st : group.getStudents()) {
            String sqlst = "INSERT INTO Students (studentId, Surname, Name, Gender, DateOfBirth, groupId) VALUES (?,?,?,?,?,?)";
            PreparedStatement prepSt = GeneralDAO.connection.prepareStatement(sqlst);
            prepSt.setInt(1, st.getStudentId());
            prepSt.setString(2, st.getSurName());
            prepSt.setString(3, st.getName());
            prepSt.setString(4, st.getGender().toString());
            prepSt.setDate(5, java.sql.Date.valueOf(String.valueOf(st.getDateOfBirth())));
            prepSt.setInt(6, st.getGroupId());

            prepSt.executeUpdate();
        }
    }

    public static void addGroup(Group group) throws SQLException {
        GeneralDAO.getConnection();
        String sql = "INSERT INTO groups (groupid, curator, faculty) VALUES (?,?,?)";
        PreparedStatement prepSt = GeneralDAO.connection.prepareStatement(sql);
        prepSt.setInt(1, group.getGroupId());
        prepSt.setString(2, group.getCurator());
        prepSt.setString(3, group.getFaculty());

        prepSt.executeUpdate();
    }

    public static void deleteGroup(int groupid) throws SQLException {
        GeneralDAO.getConnection();
        Statement stmt = GeneralDAO.connection.createStatement();
        String sql = "DELETE FROM groups WHERE groupid=" + groupid;
        stmt.executeUpdate(sql);
        System.out.println("Групу успішно видалено!");
    }

    public static boolean checkUnique(Group group) throws SQLException {
        GeneralDAO.getConnection();
        Statement stmt = GeneralDAO.connection.createStatement();
        String sql = "SELECT groupid FROM groups WHERE curator='" + group.getCurator() + "' AND faculty='" + group.getFaculty() + "'";
        ResultSet rs = stmt.executeQuery(sql);
        return !rs.next();
    }
}
