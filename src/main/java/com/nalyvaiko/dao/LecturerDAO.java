package com.nalyvaiko.dao;

import com.nalyvaiko.model.Lecturer;
import java.math.BigDecimal;
import java.util.List;

public interface LecturerDAO extends GeneralDAO<Lecturer, Integer> {

  Lecturer getDepartmentHead(String departmentName);

  long countNumberOfDegreeByDepartment(String departmentName,
      String degreeName);

  BigDecimal countAverageSalaryOfDepartment(String departmentName);

  long countEmployeesOfDepartment(String departmentName);

  List<Lecturer> getLecturersWhichFirstOrMiddleNamesOrSurnameMatchTemplate(
      String pattern);
}
