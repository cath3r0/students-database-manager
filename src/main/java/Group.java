package main.java;


import java.util.TreeSet;

public class Group {
    private int groupId;
    private String curator;
    private String faculty;
    public TreeSet<Student> students = new TreeSet<>();

    Group() {}
    public Group(String curator, String faculty) {
        this.curator = curator;
        this.faculty = faculty;
        groupId = IdGenerator.getUniqueGroupId();
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void addStudentToGroup(Student s) {
        if (checkUniqueStudent(s)) {
            students.add(s);
        }
        else
            System.out.println("Студент з такими ім’я/прізвищем вже є цій групі, будь-ласка, змініть ім’я: " + s.toString());

    }

    public boolean checkUniqueStudent(Student s) {
        boolean unique = true;
        for (Student st : students) {
            if (st.equals(s)) {
                unique = false;
                break;
            }
            else {
                unique = true;
                break;
            }
        }
        return unique;
    }

    public TreeSet<Student> getStudents() {
        return students;
    }

    public void setStudents(TreeSet<Student> students) {
        this.students = students;
    }

    public TreeSet<Student> getByGender(Gender gender) {
        TreeSet<Student> l = new TreeSet<>();
        for (Student si : students) {
            if (si.getGender() == gender) {
                l.add(si);
            }
        }
        return l;
    }

    @Override
    public String toString() {
        return "Group { " +
                "groupId = " + groupId +
                ", curator = '" + curator + '\'' +
                ", faculty = '" + faculty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupId != group.groupId) return false;
        if (!curator.equals(group.curator)) return false;
        return faculty.equals(group.faculty);

    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + curator.hashCode();
        result = 31 * result + faculty.hashCode();
        return result;
    }

}
