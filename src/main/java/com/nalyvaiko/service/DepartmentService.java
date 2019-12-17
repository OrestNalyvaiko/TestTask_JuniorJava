package com.nalyvaiko.service;

import com.nalyvaiko.dao.DepartmentDAO;
import com.nalyvaiko.dao.impl.DepartmentDAOImpl;
import com.nalyvaiko.model.Department;
import java.util.List;

public class DepartmentService {

  private DepartmentDAO departmentDAO;

  public DepartmentService() {
    departmentDAO = new DepartmentDAOImpl();
  }

  public Department getDepartment(Integer id) {
    return departmentDAO.get(id).orElseThrow(RuntimeException::new);
  }

  public void addDepartment(Department department) {
    departmentDAO.add(department);
  }

  public void deleteDepartment(Department department) {
    departmentDAO.delete(department);
  }

  public Department updateDepartment(Department department) {
    return departmentDAO.update(department);
  }

  public List<Department> getAllDepartments() {
    return departmentDAO.getAll();
  }
}
