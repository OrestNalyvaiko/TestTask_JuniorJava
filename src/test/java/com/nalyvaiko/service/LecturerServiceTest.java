package com.nalyvaiko.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nalyvaiko.dao.DegreeDAO;
import com.nalyvaiko.dao.LecturerDAO;
import com.nalyvaiko.model.Degree;
import com.nalyvaiko.model.Department;
import com.nalyvaiko.model.Lecturer;
import com.nalyvaiko.model.enums.Post;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LecturerServiceTest {

  @Mock
  private LecturerDAO lecturerDAO;
  @Mock
  private DegreeDAO degreeDAO;
  @InjectMocks
  private LecturerService lecturerService;
  private Lecturer lecturer;

  @Before
  public void setup() {
    lecturer = new Lecturer();
    lecturer.setId(1);
    lecturer.setFirstName("Orest");
    lecturer.setMiddleName("ole");
    lecturer.setSurname("Nalyvaiko");
    Degree degree = new Degree();
    degree.setId(1);
    degree.setDegreeName("assistant");
    lecturer.setDegree(degree);
    lecturer.setSalary(BigDecimal.valueOf(1500.00));
    lecturer.setPost(Post.DEPARTMENT_MEMBER);
    Department firstDepartment = new Department();
    firstDepartment.setDepartmentName("EOM");
    firstDepartment.setId(1);
    Set<Department> departments = new HashSet<>();
    departments.add(firstDepartment);
    lecturer.setDepartments(departments);
  }

  @Test
  public void whenGetLecturerThenReturnLecturer() {
    when(lecturerDAO.get(1)).thenReturn(Optional.of(lecturer));

    Lecturer lecturerFromDB = lecturerService.getLecturer(1);

    assertEquals("Lecturers are not equals", lecturer, lecturerFromDB);
    verify(lecturerDAO, times(1)).get(1);
  }

  @Test
  public void whenAddLecturerThenCallDAOAdd() {
    doNothing().when(lecturerDAO).add(lecturer);

    lecturerService.addLecturer(lecturer);

    verify(lecturerDAO, times(1)).add(lecturer);
  }

  @Test
  public void whenDeleteLecturerThenCallDAODelete() {
    doNothing().when(lecturerDAO).delete(lecturer);

    lecturerService.deleteLecturer(lecturer);

    verify(lecturerDAO, times(1)).delete(lecturer);
  }

  @Test
  public void whenUpdateLecturerThenReturnUpdatedLecturer() {
    lecturer.setPost(Post.DEPARTMENT_HEAD);
    when(lecturerDAO.update(lecturer)).thenReturn(lecturer);

    Lecturer updatedLecturer = lecturerService.updateLecturer(lecturer);

    assertEquals("Lecturer posts are not equal", lecturer.getPost(),
        updatedLecturer.getPost());
    verify(lecturerDAO, times(1)).update(lecturer);
  }

  @Test
  public void whenGetAllLecturersThenReturnListOfLecturers() {
    List<Lecturer> lecturers = new ArrayList<>();
    lecturers.add(lecturer);
    when(lecturerDAO.getAll()).thenReturn(lecturers);

    List<Lecturer> lecturersFromDB = lecturerService.getAllLecturers();

    assertEquals("Lecturers are not equals", lecturers, lecturersFromDB);
    verify(lecturerDAO, times(1)).getAll();
  }

  @Test
  public void whenGetDepartmentHeadThenReturnLecturer() {
    lecturer.setPost(Post.DEPARTMENT_HEAD);
    when(lecturerDAO.getDepartmentHead("EOM")).thenReturn(lecturer);

    Lecturer departmentHead = lecturerService.getDepartmentHead("EOM");

    assertNotNull("Head of department is null", departmentHead);
    verify(lecturerDAO, times(1)).getDepartmentHead("EOM");
  }

  @Test
  public void whenGetDepartmentDegreeStatisticThenReturnMap() {
    List<Degree> degrees = new ArrayList<>();
    Degree degree = new Degree();
    degree.setDegreeName("assistant");
    degrees.add(degree);
    when(degreeDAO.getAll()).thenReturn(degrees);
    when(lecturerDAO.countNumberOfDegreeByDepartment("EOM", "assistant"))
        .thenReturn(1L);
    Map<String, Long> statisticMap = new HashMap<>();
    statisticMap.put("assistant", 1L);

    Map<String, Long> returnedMap = lecturerService
        .getDepartmentDegreeStatistic("EOM");

    assertEquals("Maps is not equals", statisticMap, returnedMap);
    verify(lecturerDAO, times(1))
        .countNumberOfDegreeByDepartment("EOM", "assistant");
    verify(degreeDAO, times(1)).getAll();

  }

  @Test
  public void whenCountAverageSalaryOfDepartmentThenReturnAverageSalary() {
    when(lecturerDAO.countAverageSalaryOfDepartment("EOM"))
        .thenReturn(BigDecimal.valueOf(1500.00));

    BigDecimal averageSalary = lecturerService
        .countAverageSalaryOfDepartment("EOM");

    assertEquals("Expected and actual average salary are not equal",
        lecturer.getSalary(), averageSalary);
    verify(lecturerDAO, times(1)).countAverageSalaryOfDepartment("EOM");
  }
}
