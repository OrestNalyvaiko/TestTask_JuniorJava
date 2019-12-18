package com.nalyvaiko.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nalyvaiko.dao.impl.DepartmentDAOImpl;
import com.nalyvaiko.model.Department;
import com.nalyvaiko.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class DepartmentDAOImplTest {

  private DepartmentDAO departmentDAO = new DepartmentDAOImpl();
  private Department department;

  @Before
  public void setup() {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    department = new Department();
    department.setDepartmentName("Specialized computer systems");
    session.save(department);
    transaction.commit();
    session.close();
  }

  @Test
  public void whenAddThenSaveInDB() {
    Department department = new Department();
    department.setDepartmentName("Electronic computers");
    departmentDAO.add(department);

    assertNotNull("Department entity is not saved to db", department.getId());
  }

  @Test
  public void whenGetThenReturnOptional() {
    Department departmentFromDB = departmentDAO.get(1)
        .orElseThrow(RuntimeException::new);

    assertEquals("Departments id are not equals ", department.getId(),
        departmentFromDB.getId());
  }

  @Test
  public void whenUpdateThenUpdateInDB() {
    Department department = new Department();
    department.setId(1);
    department.setDepartmentName("Electronic computers");
    Department updatedDepartment = departmentDAO.update(department);

    assertEquals("Department entity is not updated",
        department.getDepartmentName(),
        updatedDepartment.getDepartmentName());
  }

  @Test
  public void whenDeleteThenDeleteInDB() {
    departmentDAO.delete(department);

    Session session = HibernateUtil.getSession();
    assertNull("Department entity is not deleted",
        session.get(Department.class, 1));
    session.close();
  }

  @Test
  public void whenGetAllThenReturnList() {
    List<Department> departments = departmentDAO.getAll();

    assertNotNull("List of degrees is null", departments);
  }

  @Test
  public void whenGetDepartmentByDepartmentNameThenReturnDepartmentFromDB() {
    Department departmentFromDB = departmentDAO
        .getDepartmentByDepartmentName(department.getDepartmentName());

    assertEquals("Departments are not equal", department, departmentFromDB);
  }
}
