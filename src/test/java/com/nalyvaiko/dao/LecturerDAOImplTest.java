package com.nalyvaiko.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nalyvaiko.dao.impl.LecturerDAOImpl;
import com.nalyvaiko.model.Degree;
import com.nalyvaiko.model.Department;
import com.nalyvaiko.model.Lecturer;
import com.nalyvaiko.model.enums.Post;
import com.nalyvaiko.util.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class LecturerDAOImplTest {

  private LecturerDAO lecturerDAO = new LecturerDAOImpl();
  private Lecturer lecturer;

  @Before
  public void setup() {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    lecturer = new Lecturer();
    lecturer.setFirstName("Orest");
    lecturer.setMiddleName("ole");
    lecturer.setSurname("Nalyvaiko");
    Degree degree = new Degree();
    degree.setDegreeName("assistant");
    session.save(degree);
    lecturer.setDegree(degree);
    lecturer.setSalary(BigDecimal.valueOf(1500.00));
    lecturer.setPost(Post.DEPARTMENT_MEMBER);
    Department firstDepartment = new Department();
    firstDepartment.setDepartmentName("EOM");
    session.save(firstDepartment);
    Department secondDepartment = new Department();
    secondDepartment.setDepartmentName("SKS");
    session.save(secondDepartment);
    Set<Department> departments = new HashSet<>();
    departments.add(firstDepartment);
    departments.add(secondDepartment);
    lecturer.setDepartments(departments);
    session.save(lecturer);
    transaction.commit();
    session.close();
  }

  @Test
  public void whenAddThenSaveInDB() {
    Lecturer lecturer = new Lecturer();
    lecturer.setFirstName("Orest");
    lecturer.setMiddleName("ole");
    lecturer.setSurname("Nalyvaiko");
    Degree degree = new Degree();
    degree.setId(1);
    degree.setDegreeName("assistant");
    lecturer.setDegree(degree);
    lecturer.setSalary(new BigDecimal(1500.00));
    lecturer.setPost(Post.DEPARTMENT_MEMBER);
    Department firstDepartment = new Department();
    firstDepartment.setId(1);
    firstDepartment.setDepartmentName("EOM");
    Set<Department> departments = new HashSet<>();
    departments.add(firstDepartment);
    lecturer.setDepartments(departments);
    lecturerDAO.add(lecturer);

    assertNotNull("Lecturer entity is not saved to db", lecturer.getId());
  }

  @Test
  public void whenGetThenReturnOptional() {
    Lecturer lecturerFromDB = lecturerDAO.get(1)
        .orElseThrow(RuntimeException::new);

    assertEquals("Lecturer id are not equals ", lecturer.getId(),
        lecturerFromDB.getId());
  }

  @Test
  public void whenUpdateThenUpdateInDB() {
    Lecturer lecturer = new Lecturer();
    lecturer.setId(1);
    lecturer.setFirstName("Orest");
    lecturer.setMiddleName("ole");
    lecturer.setSurname("Nalyvaiko");
    Degree degree = new Degree();
    degree.setId(1);
    degree.setDegreeName("assistant");
    lecturer.setDegree(degree);
    lecturer.setSalary(BigDecimal.valueOf(1600.00));
    lecturer.setPost(Post.DEPARTMENT_MEMBER);
    Department firstDepartment = new Department();
    firstDepartment.setId(1);
    firstDepartment.setDepartmentName("EOM");
    Set<Department> departments = new HashSet<>();
    departments.add(firstDepartment);
    lecturer.setDepartments(departments);
    Lecturer updatedLecturer = lecturerDAO.update(lecturer);

    assertEquals("Lecturer entity salary is not updated",
        lecturer.getSalary(),
        updatedLecturer.getSalary());
  }

  @Test
  public void whenDeleteThenDeleteInDB() {
    lecturerDAO.delete(lecturer);

    Session session = HibernateUtil.getSession();
    assertNull("Lecturer entity is not deleted",
        session.get(Lecturer.class, 1));
    session.close();
  }

  @Test
  public void whenGetAllThenReturnList() {
    List<Lecturer> lecturers = lecturerDAO.getAll();

    assertNotNull("List of lecturers is null", lecturers);
  }

  @Test
  public void whenGetDepartmentHeadThenReturnLecturer() {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    lecturer.setPost(Post.DEPARTMENT_HEAD);
    session.save(lecturer);
    transaction.commit();
    session.close();

    Lecturer departmentHead = lecturerDAO.getDepartmentHead("SKS");

    assertNotNull("There are none department head", departmentHead);
  }

  @Test
  public void whenCountNumberOfDegreeByDepartmentReturnNumber() {
    long numberOfLecturersWithDegree = lecturerDAO
        .countNumberOfDegreeByDepartment("EOM", "assistant");

    assertEquals("Expected and actual count is not equal", 1,
        numberOfLecturersWithDegree);
  }

  @Test
  public void whenCountAverageSalaryOfDepartmentThenReturnAverageSalary() {
    BigDecimal averageSalary = lecturerDAO
        .countAverageSalaryOfDepartment("EOM");

    assertEquals("Expected and actual average salary are not equal",
        lecturer.getSalary(), averageSalary);
  }

  @Test
  public void whenCountEmployeesOfDepartmentThenReturnAmountOfEmployees() {
    long employeesCount = lecturerDAO.countEmployeesOfDepartment("EOM");

    assertEquals("Expected and actual count of employees are not equal",
        1, employeesCount);
  }

  @Test
  public void whenGetLecturersWhichFirstOrMiddleNamesOrSurnameMatchTemplateThenReturnListOfLecturer() {
    List<Lecturer> lecturers = new ArrayList<>();
    lecturers.add(lecturer);

    List<Lecturer> lecturersFromDB = lecturerDAO
        .getLecturersWhichFirstOrMiddleNamesOrSurnameMatchTemplate("Naly");

    assertEquals("Expected and actual lecturers are not equal",
        lecturers.get(0).getId(),
        lecturersFromDB.get(0).getId());
  }
}
