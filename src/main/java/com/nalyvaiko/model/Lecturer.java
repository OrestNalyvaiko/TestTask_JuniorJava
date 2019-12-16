package com.nalyvaiko.model;

import com.nalyvaiko.model.enums.Post;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "lecturer")
public class Lecturer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name", length = 120, nullable = false)
  private String firstName;

  @Column(name = "middle_name", length = 120, nullable = false)
  private String middleName;

  @Column(name = "surname", length = 120, nullable = false)
  private String surname;

  @ManyToOne
  @JoinColumn(name = "degree_id", nullable = false)
  private Degree degree;

  @Column(name = "salary", precision = 8, scale = 2)
  private BigDecimal salary;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "post")
  private Post post;

  @ManyToMany()
  @Cascade(value = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
  @JoinTable(
      name = "department_lecturer",
      joinColumns = {@JoinColumn(name = "lecturer_id")},
      inverseJoinColumns = {@JoinColumn(name = "department_id")}
  )
  private Set<Department> departments = new HashSet<>();

  public Lecturer() {
  }

  public Lecturer(Integer id, String firstName, String middleName,
      String surname, Degree degree, BigDecimal salary,
      Post post, Set<Department> departments) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.surname = surname;
    this.degree = degree;
    this.salary = salary;
    this.post = post;
    this.departments = departments;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Degree getDegree() {
    return degree;
  }

  public void setDegree(Degree degree) {
    this.degree = degree;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public Set<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(Set<Department> departments) {
    this.departments = departments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Lecturer)) {
      return false;
    }
    Lecturer lecturer = (Lecturer) o;
    return Objects.equals(getId(), lecturer.getId()) &&
        Objects.equals(getFirstName(), lecturer.getFirstName()) &&
        Objects.equals(getMiddleName(), lecturer.getMiddleName()) &&
        Objects.equals(getSurname(), lecturer.getSurname()) &&
        Objects.equals(getDegree(), lecturer.getDegree()) &&
        Objects.equals(getSalary(), lecturer.getSalary()) &&
        getPost() == lecturer.getPost() &&
        Objects.equals(getDepartments(), lecturer.getDepartments());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getId(), getFirstName(), getMiddleName(), getSurname(),
            getDegree(),
            getSalary(), getPost(), getDepartments());
  }

  @Override
  public String toString() {
    return "Lecturer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", middleName='" + middleName + '\'' +
        ", surname='" + surname + '\'' +
        ", salary='" + salary + '\'' +
        ", post='" + post + '\'' +
        ", degree='" + degree + '\'' +
        '}';
  }
}
