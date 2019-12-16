package com.nalyvaiko.dao.impl;

import com.nalyvaiko.dao.DepartmentDAO;
import com.nalyvaiko.model.Department;
import com.nalyvaiko.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DepartmentDAOImpl implements DepartmentDAO {

  @Override
  public Optional<Department> get(Integer id) {
    Transaction transaction = null;
    Department department = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      department = session.get(Department.class, id);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return Optional.ofNullable(department);
  }

  @Override
  public void add(Department entity) {
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
  public void delete(Department entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery("DELETE " +
          "FROM Department department WHERE department.id = :id");
      query.setParameter("id", entity.getId());
      query.executeUpdate();
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
  }

  @Override
  public Department update(Department entity) {
    Transaction transaction = null;
    Department updatedDepartment = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      updatedDepartment = (Department) session.merge(entity);
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return updatedDepartment;
  }

  @Override
  public List<Department> getAll() {
    Transaction transaction = null;
    List<Department> departments = null;
    try (Session session = HibernateUtil.getSession()) {
      transaction = session.beginTransaction();
      departments = session.createQuery("SELECT department " +
          "FROM Department department ", Department.class)
          .getResultList();
      transaction.commit();
    } catch (HibernateException exception) {
      Optional.ofNullable(transaction).ifPresent(Transaction::rollback);
      exception.printStackTrace();
    }
    return departments;
  }
}
