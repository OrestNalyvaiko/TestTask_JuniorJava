package com.nalyvaiko.dao.impl;

import com.nalyvaiko.dao.DegreeDAO;
import com.nalyvaiko.model.Degree;
import com.nalyvaiko.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DegreeDAOImpl implements DegreeDAO {

  @Override
  public Optional<Degree> get(Integer id) {
    Transaction transaction = null;
    Degree degree = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      degree = session.get(Degree.class, id);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return Optional.ofNullable(degree);
  }

  @Override
  public void add(Degree entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
  }

  @Override
  public void delete(Degree entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      Degree degree = session.get(Degree.class, entity.getId());
      session.delete(degree);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
  }

  @Override
  public Degree update(Degree entity) {
    Transaction transaction = null;
    Degree updatedDegree = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      updatedDegree = (Degree) session.merge(entity);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return updatedDegree;
  }

  @Override
  public List<Degree> getAll() {
    Transaction transaction = null;
    List<Degree> degrees = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      degrees = session.createQuery("select degree " +
          "from Degree degree ", Degree.class)
          .getResultList();
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return degrees;
  }
}
