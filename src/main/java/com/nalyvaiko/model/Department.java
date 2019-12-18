package com.nalyvaiko.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "department_name", nullable = false)
  private String departmentName;

  @ManyToMany(mappedBy = "departments")
  private Set<Lecturer> lecturers = new HashSet<>();

  public Department() {
  }

  public Department(Integer id, String departmentName,
      Set<Lecturer> lecturers) {
    this.id = id;
    this.departmentName = departmentName;
    this.lecturers = lecturers;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public Set<Lecturer> getLecturers() {
    return lecturers;
  }

  public void setLecturers(Set<Lecturer> lecturers) {
    this.lecturers = lecturers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Department)) {
      return false;
    }
    Department that = (Department) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getDepartmentName(), that.getDepartmentName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDepartmentName());
  }

  @Override
  public String toString() {
    return "Department{" +
        "id=" + id +
        ", departmentName='" + departmentName + '\'' +
        '}';
  }
}
