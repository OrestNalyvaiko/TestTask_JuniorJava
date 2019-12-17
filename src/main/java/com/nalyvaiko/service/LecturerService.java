package com.nalyvaiko.service;

import com.nalyvaiko.dao.DegreeDAO;
import com.nalyvaiko.dao.LecturerDAO;
import com.nalyvaiko.dao.impl.DegreeDAOImpl;
import com.nalyvaiko.dao.impl.LecturerDAOImpl;
import com.nalyvaiko.model.Degree;
import com.nalyvaiko.model.Lecturer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LecturerService {

  private LecturerDAO lecturerDAO;
  private DegreeDAO degreeDAO;

  public LecturerService() {
    lecturerDAO = new LecturerDAOImpl();
    degreeDAO = new DegreeDAOImpl();
  }

  public Lecturer getLecturer(Integer id) {
    return lecturerDAO.get(id).orElseThrow(RuntimeException::new);
  }

  public void addLecturer(Lecturer lecturer) {
    lecturerDAO.add(lecturer);
  }

  public void deleteLecturer(Lecturer lecturer) {
    lecturerDAO.delete(lecturer);
  }

  public Lecturer updateLecturer(Lecturer lecturer) {
    return lecturerDAO.update(lecturer);
  }

  public List<Lecturer> getAllLecturers() {
    return lecturerDAO.getAll();
  }

  public Lecturer getDepartmentHead(String departmentName) {
    return lecturerDAO.getDepartmentHead(departmentName);
  }

  public Map<String, Long> getDepartmentDegreeStatistic(String departmentName) {
    List<Degree> degrees = degreeDAO.getAll();
    Map<String, Long> statisticMap = new HashMap<>();
    for (Degree degree : degrees) {
      long numberOfLecturersWithDegree = lecturerDAO
          .countNumberOfDegreeByDepartment(departmentName,
              degree.getDegreeName());
      statisticMap.put(degree.getDegreeName(), numberOfLecturersWithDegree);
    }
    return statisticMap;
  }

  public BigDecimal countAverageSalaryOfDepartment(String departmentName) {
    return lecturerDAO.countAverageSalaryOfDepartment(departmentName);
  }

  public long countEmployeesOfDepartment(String departmentName) {
    return lecturerDAO.countEmployeesOfDepartment(departmentName);
  }
}
