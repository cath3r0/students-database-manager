package main.java.services;

import main.java.dao.StudentDAO;
import main.java.Gender;
import main.java.Student;
import org.joda.time.LocalDate;

import java.io.*;
import java.sql.SQLException;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class StudentService {

    public static void writeToFile(String fileName, TreeSet<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
            String tableHeader = String.format("%10s|%10s|%10s|%10s|%10s|%10s", "StudentID", "Surname  ",
                    "Name   ", "Gender  ", "DateOfBirth", "GroupID  ");
            String line = "-----------------------------------------------------------------------------";
            writer.write(tableHeader + '\n' + line + '\n');
            for (Student si : students) {
                writer.write(String.format("%-10d|%-10s|%-10s|%-10s|%-10tF|%-10d%n", si.getStudentId(), si.getSurName(),
                        si.getName(), si.getGender().toString(), java.sql.Date.valueOf(String.valueOf(si.getDateOfBirth())),
                        si.getGroupId()));
            }
        } catch (IOException e) {
            System.out.println("Не вдалось записати у файл");
        }

    }

    public static void showStudents(TreeSet<Student> students) {
        System.out.println(String.format("%10s|%10s|%10s|%10s|%10s|%10s", "StudentID", "Surname  ",
                "Name   ", "Gender  ", "DateOfBirth", "GroupID  "));
        System.out.println("-----------------------------------------------------------------------------");
        for (Student si : students) {
            System.out.println(String.format("%-10d|%-10s|%-10s|%-10s|%-10tF|%-10d%n", si.getStudentId(), si.getSurName(),
                    si.getName(), si.getGender().toString(), java.sql.Date.valueOf(String.valueOf(si.getDateOfBirth())),
                    si.getGroupId()));
        }
    }

    public static void updateStudent() {
        int studentId = 0;
        Student updatedStud;
        System.out.println("ID студента, якого Ви хочете змінити:");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            studentId = Integer.parseInt(reader.readLine());
            updatedStud = StudentDAO.getStudent(studentId);
            System.out.println("Введіть нове прізвище (натисніть Enter, щоб пропустити):");
            String readString = reader.readLine();
            if (readString.equals("")) {
            } else {
                updatedStud.setSurName(readString);
            }
            System.out.println("Введіть нове ім’я (натисніть Enter, щоб пропустити):");
            readString = reader.readLine();
            if (readString.equals("")) {
            } else {
                updatedStud.setName(readString);
            }
            System.out.println("Оберіть нову стать (натисніть Enter, щоб пропустити): M/F?");
            readString = reader.readLine();
            if (readString.equals("M")) {
                updatedStud.setGender(Gender.Male);
            } else if (readString.equals("F")) {
                updatedStud.setGender(Gender.Female);
            } else if (reader.readLine().equals("")) {
            }
            System.out.println("Введіть нову дату народження (натисніть Enter, щоб пропустити): YYYY-MM-DD");
            readString = reader.readLine();
            if (readString.equals("")) {
            } else {
                updatedStud.setDateOfBirth(LocalDate.parse(readString));
            }
            StudentDAO.updateStudent(updatedStud);
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static TreeSet<Student> readStudentsFromFile(String fileName) {

        TreeSet<Student> students = new TreeSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            bufferedReader.readLine();
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] stringArray = line.split(Pattern.quote("|"));
                students.add(new Student(Integer.parseInt(stringArray[0].trim()), stringArray[2].trim(),
                        stringArray[1].trim(), LocalDate.parse(stringArray[4].trim()),
                        Gender.valueOf(stringArray[3].trim()), Integer.parseInt(stringArray[5].trim())));
            }
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return students;
    }

    public static void addStudent(Student s) {
        try {
            if (StudentDAO.checkUnique(s)) {
                StudentDAO.addStudent(s);
            } else {
                System.out.println("Студент з такими ім’я/прізвищем вже є базі даних, будь-ласка, змініть ім’я: " + s.toString());
            }
        } catch (SQLException e) {
            System.out.println("Не вдалось додати студента");
        }
    }

    public static void deleteStudent(String surname, String name) {
        try {
            StudentDAO.deleteStudent(surname, name);
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

}
