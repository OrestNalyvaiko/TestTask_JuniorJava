package com.nalyvaiko.view;

import com.nalyvaiko.model.Department;
import com.nalyvaiko.model.Lecturer;
import com.nalyvaiko.service.DepartmentService;
import com.nalyvaiko.service.LecturerService;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

  private static Scanner input = new Scanner(System.in);
  private Map<String, String> menu;
  private Map<String, Printable> methodsMenu;
  private LecturerService lecturerService;
  private DepartmentService departmentService;

  public Menu() {
    menu = new LinkedHashMap<>();
    methodsMenu = new LinkedHashMap<>();
    lecturerService = new LecturerService();
    departmentService = new DepartmentService();
    menu.put("A", "   A - Get Department Head");
    menu.put("B", "   B - Show department statistic");
    menu.put("C", "   C - Show the average salary for department");
    menu.put("D", "   D - Show employees count of department");
    menu.put("E", "   E - Global search by template");

    menu.put("Q", "   Q - exit");

    methodsMenu.put("A", this::getDepartmentHead);
    methodsMenu.put("B", this::showStatistic);
    methodsMenu.put("C", this::showAverageSalary);
    methodsMenu.put("D", this::showEmployeesCount);
    methodsMenu.put("E", this::searchByTemplate);
  }

  private void getDepartmentHead() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    Lecturer departmentHead = lecturerService.getDepartmentHead(departmentName);
    System.out.println(
        "Head of " + departmentName + " department is " + departmentHead
            .getDegree().getDegreeName() + " " + departmentHead.getSurname()
            + " " + departmentHead.getFirstName() + " " + departmentHead
            .getMiddleName());
  }

  private void showStatistic() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    Map<String, Long> departmentStatistic = lecturerService
        .getDepartmentDegreeStatistic(departmentName);
    for (Map.Entry entrySet : departmentStatistic.entrySet()) {
      System.out.println(entrySet.getKey() + " - " + entrySet.getValue());
    }
  }

  private void showAverageSalary() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    BigDecimal averageSalary = lecturerService
        .countAverageSalaryOfDepartment(departmentName);
    System.out.println(
        "The average salary of " + departmentName + " is " + averageSalary);
  }

  private void showEmployeesCount() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    long employeesCount = lecturerService
        .countEmployeesOfDepartment(departmentName);
    System.out.println(
        "Employees count for " + departmentName + " is " + employeesCount);
  }

  private void searchByTemplate() {
    System.out.print("Input template: ");
    String template = input.nextLine();
    List<String> lecturersName = lecturerService
        .searchLecturersByTemplate(template);
    lecturersName.forEach(System.out::println);
  }

  private void showAllDepartments() {
    System.out.println("Department names:");
    departmentService.getAllDepartments().stream()
        .map(Department::getDepartmentName).forEach(System.out::println);
  }


  private void outputMenu() {
    System.out.println("\nMENU:");
    for (String key : menu.keySet()) {
      if (key.length() == 1) {
        System.out.println(menu.get(key));
      }
    }
  }

  public void show() {
    String keyMenu;
    do {
      outputMenu();
      System.out.println("Please, select menu point.");
      keyMenu = input.nextLine().toUpperCase();
      try {
        methodsMenu.get(keyMenu).print();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Program ends");
      }
    } while (!keyMenu.equals("Q"));
  }
}
