package com.nalyvaiko.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "degree")
public class Degree {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "degree_name", length = 120, nullable = false)
  private String degreeName;

  public Degree() {
  }

  public Degree(Integer id, String degreeName) {
    this.id = id;
    this.degreeName = degreeName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDegreeName() {
    return degreeName;
  }

  public void setDegreeName(String degreeName) {
    this.degreeName = degreeName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Degree)) {
      return false;
    }
    Degree degree = (Degree) o;
    return Objects.equals(getId(), degree.getId()) &&
        Objects.equals(getDegreeName(), degree.getDegreeName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDegreeName());
  }

  @Override
  public String toString() {
    return "Degree{" +
        "id=" + id +
        ", degreeName='" + degreeName + '\'' +
        '}';
  }
}
