package org.adrianwalker.continuedfractions.graph.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.adrianwalker.continuedfractions.Fraction;

@Entity
@Table(name = "node_path",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"id", "sid"})
    },
    indexes = {
      @Index(columnList = "hops", unique = false)
    })
public class NodePath implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "id", nullable = false, precision = (Fraction.SCALE * 2) - 1, scale = Fraction.SCALE)
  private BigDecimal id;

  @Basic(optional = false)
  @Column(name = "sid", nullable = false, precision = (Fraction.SCALE * 2) - 1, scale = Fraction.SCALE)
  private BigDecimal sid;

  @Basic(optional = false)
  @Column(name = "hops", nullable = false)
  private Integer hops;

  @ManyToOne(optional = false)
  @JoinColumn(name = "node_id", referencedColumnName = "id", nullable = false)
  private Node node;

  public NodePath() {
  }

  public BigDecimal getId() {
    return id;
  }

  public void setId(final BigDecimal id) {
    this.id = id;
  }

  public BigDecimal getSid() {
    return sid;
  }

  public void setSid(final BigDecimal sid) {
    this.sid = sid;
  }

  public Integer getHops() {
    return hops;
  }

  public void setHops(final Integer hops) {
    this.hops = hops;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(final Node node) {
    this.node = node;
  }

  @Override
  public int hashCode() {

    int hash = 3;
    hash = 41 * hash + Objects.hashCode(this.id);

    return hash;
  }

  @Override
  public boolean equals(final Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    return Objects.equals(this.id, ((NodePath) obj).id);
  }

  @Override
  public String toString() {
    return "NodePath{" + "id=" + id + ", sid=" + sid + ", hops=" + hops + '}';
  }
}
