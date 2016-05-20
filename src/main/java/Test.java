package main.java;

import main.java.services.GeneralService;

import java.io.IOException;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws SQLException, IOException {

        GeneralService.dropDB();
        GeneralService.populateDB();

    }


}



