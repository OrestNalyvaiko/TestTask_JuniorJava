package com.nalyvaiko.dao;

import com.nalyvaiko.model.Lecturer;

public interface LecturerDAO extends GeneralDAO<Lecturer, Integer> {

  Lecturer getDepartmentHead(String departmentName);

  long countNumberOfDegreeByDepartment(String departmentName,
      String degreeName);
}
