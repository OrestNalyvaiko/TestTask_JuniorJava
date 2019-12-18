package com.nalyvaiko.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

  private static Scanner input = new Scanner(System.in);
  private Map<String, String> menu;
  private Map<String, Command> methodsMenu;

  public Menu() {
    menu = new LinkedHashMap<>();
    methodsMenu = new LinkedHashMap<>();
    UserConsoleCommunicator userConsoleCommunicator = new UserConsoleCommunicator();

    menu.put("1", "   1 - Work with Department table");
    menu.put("11", "  11 - Create new Department");
    menu.put("12", "  12 - Update Department");
    menu.put("13", "  13 - Delete from Department");
    menu.put("14", "  14 - Get all Departments");
    menu.put("15", "  15 - Get Department by ID");

    menu.put("2", "   2 - Work with Degree table");
    menu.put("21", "  21 - Create new Degree");
    menu.put("22", "  22 - Update Degree");
    menu.put("23", "  23 - Delete from Degree");
    menu.put("24", "  24 - Get all Degrees");
    menu.put("25", "  25 - Get Degree by ID");

    menu.put("3", "   3 - Work with Lecturer table");
    menu.put("31", "  31 - Create new Lecturer");
    menu.put("32", "  32 - Update Lecturer");
    menu.put("33", "  33 - Delete from Lecturer");
    menu.put("34", "  34 - Get all Lecturer");
    menu.put("35", "  35 - Get Lecturer by ID");

    menu.put("A", "   A - Get Department Head");
    menu.put("B", "   B - Show department statistic");
    menu.put("C", "   C - Show the average salary for department");
    menu.put("D", "   D - Show employees count of department");
    menu.put("E", "   E - Global search by template");

    menu.put("Q", "   Q - exit");

    methodsMenu.put("A", userConsoleCommunicator::getDepartmentHead);
    methodsMenu.put("B", userConsoleCommunicator::showStatistic);
    methodsMenu.put("C", userConsoleCommunicator::showAverageSalary);
    methodsMenu.put("D", userConsoleCommunicator::showEmployeesCount);
    methodsMenu.put("E", userConsoleCommunicator::searchByTemplate);

    methodsMenu.put("11", userConsoleCommunicator::addDepartment);
    methodsMenu.put("12", userConsoleCommunicator::updateDepartment);
    methodsMenu.put("13", userConsoleCommunicator::deleteDepartment);
    methodsMenu.put("14", userConsoleCommunicator::getAllDepartments);
    methodsMenu.put("15", userConsoleCommunicator::getDepartmentById);

    methodsMenu.put("21", userConsoleCommunicator::addDegree);
    methodsMenu.put("22", userConsoleCommunicator::updateDegree);
    methodsMenu.put("23", userConsoleCommunicator::deleteDegree);
    methodsMenu.put("24", userConsoleCommunicator::getAllDegrees);
    methodsMenu.put("25", userConsoleCommunicator::getDegreeById);

    methodsMenu.put("31", userConsoleCommunicator::addLecturer);
    methodsMenu.put("32", userConsoleCommunicator::updateLecturer);
    methodsMenu.put("33", userConsoleCommunicator::deleteLecturer);
    methodsMenu.put("34", userConsoleCommunicator::getAllLecturers);
    methodsMenu.put("35", userConsoleCommunicator::getLecturerByID);


  }

  private void outputMenu() {
    System.out.println("\nMENU:");
    for (String key : menu.keySet()) {
      if (key.length() == 1) {
        System.out.println(menu.get(key));
      }
    }
  }

  private void outputSubMenu(String keyMenu) {
    System.out.println("\nSubMENU:");
    for (String key : menu.keySet()) {
      if (key.length() != 1 && key.substring(0, 1).equals(keyMenu)) {
        System.out.println(menu.get(key));
      }
    }
  }

  public void show() {
    String keyMenu;
    do {
      outputMenu();
      System.out.println("Please, select menu point.");
      keyMenu = input.nextLine();
      if (keyMenu.matches("^\\d")) {
        outputSubMenu(keyMenu);
        System.out.println("Please, select menu point.");
        keyMenu = input.nextLine().toUpperCase();
      }
      try {
        methodsMenu.get(keyMenu).execute();
      } catch (Exception e) {
        keyMenu = "Q";
        System.out.println("Program ends");
      }
    } while (!keyMenu.equals("Q"));
  }
}
