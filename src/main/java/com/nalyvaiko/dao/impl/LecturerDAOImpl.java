package com.nalyvaiko.dao.impl;

import com.nalyvaiko.dao.LecturerDAO;
import com.nalyvaiko.model.Lecturer;
import com.nalyvaiko.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LecturerDAOImpl implements LecturerDAO {

  @Override
  public Optional<Lecturer> get(Integer id) {
    Transaction transaction = null;
    Lecturer lecturer = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      lecturer = session.get(Lecturer.class, id);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return Optional.ofNullable(lecturer);
  }

  @Override
  public void add(Lecturer entity) {
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
  public void delete(Lecturer entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery("DELETE " +
          "FROM Lecturer lecturer WHERE lecturer.id = :id");
      query.setParameter("id", entity.getId());
      query.executeUpdate();
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
  }

  @Override
  public Lecturer update(Lecturer entity) {
    Transaction transaction = null;
    Lecturer updatedLecturer = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      updatedLecturer = (Lecturer) session.merge(entity);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return updatedLecturer;
  }

  @Override
  public List<Lecturer> getAll() {
    Transaction transaction = null;
    List<Lecturer> lecturers = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      lecturers = session.createQuery("SELECT lecturer " +
          "FROM Lecturer lecturer", Lecturer.class)
          .getResultList();
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return lecturers;
  }
}
