package com.nalyvaiko.service;

import com.nalyvaiko.dao.LecturerDAO;
import com.nalyvaiko.dao.impl.LecturerDAOImpl;
import com.nalyvaiko.model.Lecturer;
import java.util.List;

public class LecturerService {

  private LecturerDAO lecturerDAO;

  public LecturerService() {
    lecturerDAO = new LecturerDAOImpl();
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
}
