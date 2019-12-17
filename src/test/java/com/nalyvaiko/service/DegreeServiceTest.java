package com.nalyvaiko.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nalyvaiko.dao.DegreeDAO;
import com.nalyvaiko.model.Degree;
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
public class DegreeServiceTest {

  @Mock
  private DegreeDAO degreeDAO;
  @InjectMocks
  private DegreeService degreeService;
  private Degree degree;

  @Before
  public void setup() {
    degree = new Degree();
    degree.setId(1);
    degree.setDegreeName("professor");
  }

  @Test
  public void whenGetDegreeThenReturnDegree() {
    when(degreeDAO.get(1)).thenReturn(Optional.of(degree));

    Degree returnedDegree = degreeService.getDegree(1);

    assertEquals("Degrees is not equals", degree, returnedDegree);
    verify(degreeDAO, times(1)).get(1);
  }

  @Test
  public void whenAddDegreeThenCallDAOAdd() {
    doNothing().when(degreeDAO).add(degree);

    degreeService.addDegree(degree);

    verify(degreeDAO, times(1)).add(degree);
  }

  @Test
  public void whenDeleteDegreeThenCallDAODelete() {
    doNothing().when(degreeDAO).delete(degree);

    degreeService.deleteDegree(degree);

    verify(degreeDAO, times(1)).delete(degree);
  }

  @Test
  public void whenUpdateDegreeThenReturnUpdatedDegree() {
    degree.setDegreeName("assistant");
    when(degreeDAO.update(degree)).thenReturn(degree);

    Degree updatedDegree = degreeService.updateDegree(degree);

    assertEquals("Degree names are not equals", degree.getDegreeName(),
        updatedDegree.getDegreeName());
    verify(degreeDAO, times(1)).update(degree);
  }

  @Test
  public void whenGetAllDegreesThenReturnListOfDegrees() {
    List<Degree> degrees = new ArrayList<>();
    degrees.add(degree);
    when(degreeDAO.getAll()).thenReturn(degrees);

    List<Degree> degreesFromDB = degreeService.getAllDegrees();

    assertEquals("Degrees are not equals", degrees, degreesFromDB);
    verify(degreeDAO, times(1)).getAll();
  }
}
