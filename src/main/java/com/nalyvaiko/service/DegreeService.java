package com.nalyvaiko.service;

import com.nalyvaiko.dao.DegreeDAO;
import com.nalyvaiko.dao.impl.DegreeDAOImpl;
import com.nalyvaiko.model.Degree;
import java.util.List;

public class DegreeService {

  private DegreeDAO degreeDAO;

  public DegreeService() {
    degreeDAO = new DegreeDAOImpl();
  }

  public Degree getDegree(Integer id) {
    return degreeDAO.get(id).orElseThrow(RuntimeException::new);
  }

  public void addDegree(Degree degree) {
    degreeDAO.add(degree);
  }

  public void deleteDegree(Degree degree) {
    degreeDAO.delete(degree);
  }

  public Degree updateDegree(Degree degree) {
    return degreeDAO.update(degree);
  }

  public List<Degree> getAllDegrees() {
    return degreeDAO.getAll();
  }
}
