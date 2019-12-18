package com.nalyvaiko.view;

import com.nalyvaiko.model.Degree;
import com.nalyvaiko.model.Department;
import com.nalyvaiko.model.Lecturer;
import com.nalyvaiko.model.enums.Post;
import com.nalyvaiko.service.DegreeService;
import com.nalyvaiko.service.DepartmentService;
import com.nalyvaiko.service.LecturerService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

class UserConsoleCommunicator {

  private static Scanner input = new Scanner(System.in);
  private LecturerService lecturerService;
  private DepartmentService departmentService;
  private DegreeService degreeService;

  UserConsoleCommunicator() {
    lecturerService = new LecturerService();
    departmentService = new DepartmentService();
    degreeService = new DegreeService();
  }

  void getAllLecturers() {
    lecturerService.getAllLecturers().forEach(System.out::println);
  }

  void getLecturerByID() {
    System.out.print("Input lecturer id: ");
    Integer id = input.nextInt();
    System.out.println(lecturerService.getLecturer(id));
  }

  void deleteLecturer() {
    System.out.print("Input lecturer id: ");
    Integer id = input.nextInt();
    Lecturer deletedLecturer = new Lecturer();
    deletedLecturer.setId(id);
    lecturerService.deleteLecturer(deletedLecturer);
    System.out.println("Lecturer by id = " + id + " was deleted");
  }

  void updateLecturer() {
    System.out.print("Input lecturer id: ");
    Integer id = Integer.parseInt(input.nextLine());
    Lecturer lecturer = createLecturerByUserParameters();
    lecturer.setId(id);
    lecturerService.updateLecturer(lecturer);
  }

  void addLecturer() {
    Lecturer lecturer = createLecturerByUserParameters();
    lecturerService.addLecturer(lecturer);
  }

  private void printAllPosts() {
    System.out.println("Amount of posts: ");
    Arrays.stream(Post.values())
        .map(Post::name)
        .forEach(System.out::println);
  }

  private Lecturer createLecturerByUserParameters() {
    System.out.print("Input lecturer first name: ");
    String firstName = input.nextLine();
    System.out.print("Input lecturer middle name: ");
    String middleName = input.nextLine();
    System.out.print("Input lecturer surname: ");
    String surname = input.nextLine();
    printAllPosts();
    System.out.print("Input lecturer post: ");
    String stringPost = input.nextLine();
    Post post = Post.valueOf(stringPost);
    Degree degree = getDegreeFromUser();
    Set<Department> departments = getDepartmentsFromUser();
    System.out.print("Input lecturer salary: ");
    BigDecimal salary = input.nextBigDecimal();
    Lecturer lecturer = new Lecturer();
    lecturer.setFirstName(firstName);
    lecturer.setMiddleName(middleName);
    lecturer.setSurname(surname);
    lecturer.setSalary(salary);
    lecturer.setPost(post);
    lecturer.setDegree(degree);
    lecturer.setDepartments(departments);
    return lecturer;
  }

  private Degree getDegreeFromUser() {
    System.out.println("All degrees: ");
    List<Degree> degrees = degreeService.getAllDegrees();
    degrees.stream().map(Degree::getDegreeName).forEach(System.out::println);
    System.out.print("Input lecturer degree: ");
    String degreeName = input.nextLine();
    return degrees.stream().filter(e -> e.getDegreeName().equals(degreeName))
        .collect(Collectors.toList()).get(0);
  }

  private Set<Department> getDepartmentsFromUser() {
    System.out.println("All departments: ");
    List<Department> departmentsFromDB = departmentService.getAllDepartments();
    departmentsFromDB.stream().map(Department::getDepartmentName)
        .forEach(System.out::println);
    System.out.println("Input lecturer department till empty line: ");
    String departmentName = input.nextLine();
    Set<Department> departments = new HashSet<>();
    while (!departmentName.isEmpty()) {
      departments
          .add(departmentService.getDepartmentByDepartmentName(departmentName));
      departmentName = input.nextLine();
    }
    return departments;
  }

  void showAverageSalary() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    BigDecimal averageSalary = lecturerService
        .countAverageSalaryOfDepartment(departmentName);
    System.out.println(
        "The average salary of " + departmentName + " is " + averageSalary);
  }

  void showEmployeesCount() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    long employeesCount = lecturerService
        .countEmployeesOfDepartment(departmentName);
    System.out.println(
        "Employees count for " + departmentName + " is " + employeesCount);
  }

  void searchByTemplate() {
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

  void getDepartmentHead() {
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

  void showStatistic() {
    showAllDepartments();
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    Map<String, Long> departmentStatistic = lecturerService
        .getDepartmentDegreeStatistic(departmentName);
    for (Map.Entry entrySet : departmentStatistic.entrySet()) {
      System.out.println(entrySet.getKey() + " - " + entrySet.getValue());
    }
  }

  void addDepartment() {
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    Department department = new Department();
    department.setDepartmentName(departmentName);
    departmentService.addDepartment(department);
  }

  void getAllDepartments() {
    departmentService.getAllDepartments().forEach(System.out::println);
  }

  void updateDepartment() {
    System.out.print("Input department id: ");
    Integer id = Integer.parseInt(input.nextLine());
    System.out.print("Input department name: ");
    String departmentName = input.nextLine();
    Department department = new Department();
    department.setId(id);
    department.setDepartmentName(departmentName);
    departmentService.updateDepartment(department);
  }

  void getDepartmentById() {
    System.out.print("Input department id: ");
    Integer id = Integer.parseInt(input.nextLine());
    System.out.println(departmentService.getDepartment(id));
  }

  void deleteDepartment() {
    System.out.print("Input department id: ");
    Integer id = Integer.parseInt(input.nextLine());
    Department deletedDepartment = new Department();
    deletedDepartment.setId(id);
    departmentService.deleteDepartment(deletedDepartment);
    System.out.println("Department by id = " + id + " was deleted");
  }

  void addDegree() {
    System.out.print("Input degree name: ");
    String degreeName = input.nextLine();
    Degree degree = new Degree();
    degree.setDegreeName(degreeName);
    degreeService.addDegree(degree);
  }

  void getAllDegrees() {
    degreeService.getAllDegrees().forEach(System.out::println);
  }

  void updateDegree() {
    System.out.print("Input degree id: ");
    Integer id = Integer.parseInt(input.nextLine());
    System.out.print("Input degree name: ");
    String degreeName = input.nextLine();
    Degree degree = new Degree();
    degree.setId(id);
    degree.setDegreeName(degreeName);
    degreeService.updateDegree(degree);
  }

  void getDegreeById() {
    System.out.print("Input degree id: ");
    Integer id = Integer.parseInt(input.nextLine());
    System.out.println(degreeService.getDegree(id));
  }

  void deleteDegree() {
    System.out.print("Input delete id: ");
    Integer id = Integer.parseInt(input.nextLine());
    Degree deletedDegree = new Degree();
    deletedDegree.setId(id);
    degreeService.deleteDegree(deletedDegree);
    System.out.println("Degree by id = " + id + " was deleted");
  }
}
