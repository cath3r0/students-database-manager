package main.java.services;

import main.java.Gender;
import main.java.Student;
import main.java.dao.StudentDAO;
import org.joda.time.LocalDate;
import org.testng.annotations.*;

import java.sql.*;

import static org.testng.Assert.assertEquals;

public class AddStudentTest {

    private static Connection connection = null;

    @BeforeClass
    public static void populateDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/students", "postgres", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String sql = "CREATE TABLE groups" +
                "(" + "  groupid integer NOT NULL,\n" +
                "  curator character varying(60),\n" +
                "  faculty character varying(60),\n" +
                "  CONSTRAINT groups_pkey PRIMARY KEY (groupid)\n" +
                "); CREATE TABLE students\n" +
                "(\n" +
                "  studentid integer NOT NULL,\n" +
                "  surname character varying(60),\n" +
                "  name character varying(60),\n" +
                "  gender character varying(15),\n" +
                "  dateofbirth date,\n" +
                "  groupid integer,\n" +
                "  CONSTRAINT students_pkey PRIMARY KEY (studentid),\n" +
                "  CONSTRAINT students_groupid_fkey FOREIGN KEY (groupid)\n" +
                "      REFERENCES groups (groupid) MATCH SIMPLE\n" +
                "      ON UPDATE NO ACTION ON DELETE NO ACTION\n" +
                "); INSERT INTO groups (groupid, curator, faculty) VALUES (118, 'Павлов', 'Медицина')," +
                " (987, 'Рубінштейн', 'Психологія');" +
                " INSERT INTO students (studentid, surname, name, gender, dateofbirth, groupid) " +
                "VALUES (382369, 'Ткаченко', 'Марина', 'Female', '1993-05-10', 987)," +
                " (386524, 'Корней', 'Дмитро', 'Male', '1990-06-10', 987)," +
                " (489463, 'Петров', 'Іван', 'Male', '1995-03-20', 118)," +
                " (570546, 'Палій', 'Маргарита', 'Female', '1993-05-10', 987)," +
                " (698055, 'Васильєва', 'Ганна', 'Female', '1990-06-12', 118)," +
                " (833366, 'Перлова', 'Валентина', 'Female', '1990-06-12', 118);";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "AddStudentTest")
    public static Object[][] primeData() {
        return new Object[][]{{new Student("Іван", "Петров", LocalDate.parse("1995-3-20"), Gender.valueOf("Male"), 118), 0},
                {new Student("Василь", "Петров", LocalDate.parse("1995-3-20"), Gender.valueOf("Male"), 118), 1}};
    }

    @Test(dataProvider = "AddStudentTest")
    public void testAddStudent(Student student, int expectedCount) {
        int oldCount = StudentDAO.getStudentsCount();
        StudentService.addStudent(student);
        int actualCount = StudentDAO.getStudentsCount();
        assertEquals(expectedCount, actualCount - oldCount);
    }

    @AfterClass
    public static void cleanDB() {
        String sql = "DROP TABLE students;" + "DROP TABLE groups;";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}