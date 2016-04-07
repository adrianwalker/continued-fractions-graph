package org.adrianwalker.continuedfractions.graph.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "node")
@NamedQueries({
  @NamedQuery(name = "tree",
      query = "SELECT DISTINCT n2 "
      + "FROM Node n1, Node n2, NodePath np1, NodePath np2 "
      + "WHERE n1.id = np1.node.id "
      + "AND (np2.id >= np1.id AND np2.id < np1.sid) "
      + "AND n2.id = np2.node.id "
      + "AND n1.id = :id "
      + "ORDER BY n2.id"
  ),
  @NamedQuery(name = "parents",
      query = "SELECT DISTINCT n2 "
      + "FROM Node n1, Node n2, NodePath np1, NodePath np2 "
      + "WHERE n1.id = np1.node.id "
      + "AND n2.id = np2.node.id "
      + "AND (np1.id > np2.id AND np1.id < np2.sid) "
      + "AND np2.hops = np1.hops - 1 "
      + "AND n1.id = :id "
      + "ORDER BY np2.id"
  ),
  @NamedQuery(name = "children",
      query = "SELECT DISTINCT n2 "
      + "FROM Node n1, Node n2, NodePath np1, NodePath np2 "
      + "WHERE n1.id = np1.node.id "
      + "AND n2.id = np2.node.id "
      + "AND (np2.id > np1.id AND np2.id < np1.sid) "
      + "AND np2.hops = np1.hops + 1 "
      + "AND n1.id = :id "
      + "ORDER BY np2.id"
  )})
public class Node implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Long id;

  @Basic(optional = false)
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true, mappedBy = "node")
  private List<NodePath> nodePaths;

  public Node() {
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<NodePath> getNodePaths() {

    if (null == nodePaths) {
      nodePaths = new ArrayList<>();
    }

    return nodePaths;
  }

  public void setNodePaths(final List<NodePath> nodePaths) {
    this.nodePaths = nodePaths;
  }

  @Override
  public int hashCode() {

    int hash = 5;
    hash = 89 * hash + Objects.hashCode(this.id);

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

    return Objects.equals(this.id, ((Node) obj).id);
  }

  @Override
  public String toString() {
    return "Node{" + "id=" + id + ", name=" + name + '}';
  }
}
