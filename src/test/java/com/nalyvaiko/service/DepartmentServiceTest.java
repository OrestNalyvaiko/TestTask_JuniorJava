package com.nalyvaiko.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nalyvaiko.dao.DepartmentDAO;
import com.nalyvaiko.model.Department;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

  @Mock
  private DepartmentDAO departmentDAO;
  @InjectMocks
  private DepartmentService departmentService;
  private Department department;

  @Before
  public void setup() {
    department = new Department();
    department.setId(1);
    department.setDepartmentName("SKS");
  }

  @Test
  public void whenGetDepartmentThenReturnDepartment() {
    when(departmentDAO.get(1)).thenReturn(Optional.of(department));

    Department departmentFromDB = departmentService.getDepartment(1);

    assertEquals("Departments are not equals", department, departmentFromDB);
    verify(departmentDAO, times(1)).get(1);
  }

  @Test
  public void whenAddDepartmentThenCallDAOAdd() {
    doNothing().when(departmentDAO).add(department);

    departmentDAO.add(department);

    verify(departmentDAO, times(1)).add(department);
  }

  @Test
  public void whenDeleteDepartmentThenCallDAODelete() {
    doNothing().when(departmentDAO).delete(department);

    departmentDAO.delete(department);

    verify(departmentDAO, times(1)).delete(department);
  }

  @Test
  public void whenUpdateDepartmentThenReturnUpdatedDepartment() {
    department.setDepartmentName("EOM");
    when(departmentDAO.update(department)).thenReturn(department);

    Department updatedDepartment = departmentService
        .updateDepartment(department);

    assertEquals("Department names are not equal",
        department.getDepartmentName(), updatedDepartment.getDepartmentName());
    verify(departmentDAO, times(1)).update(department);
  }

  @Test
  public void whenGetAllDepartmentsThenReturnListOfDepartments() {
    List<Department> departments = new ArrayList<>();
    departments.add(department);
    when(departmentDAO.getAll()).thenReturn(departments);

    List<Department> departmentsFromDB = departmentService.getAllDepartments();

    assertEquals("Departments are not equals", departments, departmentsFromDB);
    verify(departmentDAO, times(1)).getAll();
  }
}
