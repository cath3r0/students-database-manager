package main.java.services;

import main.java.dao.GeneralDAO;
import main.java.Gender;
import main.java.Group;
import main.java.Student;
import org.joda.time.LocalDate;

import java.sql.SQLException;

public class GeneralService {

    public static void createStudentTable() {
        try {
            GeneralDAO.createTable("students", "studentid", "int");
            GeneralDAO.addColumnToTable("students", "surname", "varchar(60)");
            GeneralDAO.addColumnToTable("students", "name", "varchar(60)");
            GeneralDAO.addColumnToTable("students", "gender", "varchar(15)");
            GeneralDAO.addColumnToTable("students", "dateofbirth", "date");
            GeneralDAO.addColumnToTable("students", "groupid", "int");
            GeneralDAO.addForeignKey("students", "groupid", "groups");
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static void createGroupsTable() {
        try {
            GeneralDAO.createTable("groups", "groupid", "int");
            GeneralDAO.addColumnToTable("groups", "curator", "varchar(60)");
            GeneralDAO.addColumnToTable("groups", "faculty", "varchar(60)");
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static void dropStudentsTable() {
        try {
            GeneralDAO.dropTable("students");
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static void dropGroupsTable() {
        try {
            GeneralDAO.dropTable("groups");
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static void populateDB() {
        createGroupsTable();
        createStudentTable();

        Group g1 = new Group("Павлов", "Медицина");
        Group g2 = new Group("Рубінштейн", "Психологія");

        g1.addStudentToGroup(new Student("Іван", "Петров", LocalDate.parse("1995-3-20"), Gender.valueOf("Male"), g1));
        g1.addStudentToGroup(new Student("Валентина", "Перлова", LocalDate.parse("1990-6-12"), Gender.valueOf("Female"), g1));
        g1.addStudentToGroup(new Student("Ганна", "Васильєва", LocalDate.parse("1990-6-12"), Gender.valueOf("Female"), g1));
        g2.addStudentToGroup(new Student("Марина", "Ткаченко", LocalDate.parse("1993-5-10"), Gender.valueOf("Female"), g2));
        g2.addStudentToGroup(new Student("Маргарита", "Палій", LocalDate.parse("1993-5-10"), Gender.valueOf("Female"), g2));
        g2.addStudentToGroup(new Student("Дмитро", "Корней", LocalDate.parse("1990-6-10"), Gender.valueOf("Male"), g2));

        GroupService.addGroup(g1);
        GroupService.addGroup(g2);

        GroupService.addAllStudentsFromGroup(g1);
        GroupService.addAllStudentsFromGroup(g2);
    }

    public static void dropDB() {
        dropStudentsTable();
        dropGroupsTable();
    }
}
