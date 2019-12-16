package com.nalyvaiko.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nalyvaiko.dao.impl.DegreeDAOImpl;
import com.nalyvaiko.model.Degree;
import com.nalyvaiko.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class DegreeDAOImplTest {

  private DegreeDAO degreeDAO = new DegreeDAOImpl();
  private Degree degree;

  @Before
  public void setup() {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    degree = new Degree();
    degree.setDegreeName("assistant");
    session.save(degree);
    transaction.commit();
    session.close();
  }

  @Test
  public void whenAddThenSaveInDB() {
    Degree degree = new Degree();
    degree.setDegreeName("professor");
    degreeDAO.add(degree);

    assertNotNull("Degree entity is not saved to db", degree.getId());
  }

  @Test
  public void whenGetThenReturnOptional() {
    Degree degreeFromDB = degreeDAO.get(1).orElseThrow(RuntimeException::new);

    assertEquals("Degrees id are not equals ", degree.getId(),
        degreeFromDB.getId());
  }

  @Test
  public void whenUpdateThenUpdateInDB() {
    Degree degree = new Degree();
    degree.setId(1);
    degree.setDegreeName("professor");
    Degree updatedDegree = degreeDAO.update(degree);

    assertEquals("Degree entity is not updated", degree.getDegreeName(),
        updatedDegree.getDegreeName());
  }

  @Test
  public void whenDeleteThenDeleteInDB() {
    degreeDAO.delete(degree);

    Session session = HibernateUtil.getSession();
    assertNull("Degree entity is not deleted", session.get(Degree.class, 1));
    session.close();
  }

  @Test
  public void whenGetAllThenReturnList() {
    List<Degree> degrees = degreeDAO.getAll();

    assertNotNull("List of degrees is null", degrees);
  }
}
