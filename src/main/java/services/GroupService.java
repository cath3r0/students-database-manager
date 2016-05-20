package main.java.services;

import main.java.dao.GroupDAO;
import main.java.Group;

import java.sql.SQLException;

public class GroupService {

    public static void addGroup(Group gr) {
        try {
            if (GroupDAO.checkUnique(gr)) {
                GroupDAO.addGroup(gr);
            } else {
                System.out.println("Група з таким куратором та з цього факультету вже є базі даних: " + gr.toString());
            }
        } catch (SQLException e) {
            System.out.println("Не вдалось додати студента");
        }
    }

    public static void deleteGroup(Group gr) {
        try {
            GroupDAO.deleteGroup(gr.getGroupId());
        } catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }

    public static void addAllStudentsFromGroup(Group gr) {
        try {
            GroupDAO.addAllStudentsFromGroup(gr);
        }
        catch (SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
    }
}
