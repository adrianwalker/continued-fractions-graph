package org.adrianwalker.continuedfractions.graph.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.adrianwalker.continuedfractions.graph.entity.Node;
import org.adrianwalker.continuedfractions.graph.entity.NodePath;

public final class Controller {

  private final EntityManagerFactory emf;

  public Controller(final EntityManagerFactory emf) {
    this.emf = emf;
  }

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public Node create(final Node node) throws Exception {

    EntityManager em = getEntityManager();

    try {
      begin(em);
      em.persist(node);
      end(em);
    } finally {
      em.close();
    }

    return node;
  }

  public NodePath create(final NodePath nodePath) throws Exception {

    EntityManager em = getEntityManager();

    try {
      begin(em);
      em.persist(nodePath);
      end(em);
    } finally {
      em.close();
    }

    return nodePath;
  }

  public List<Node> tree(final Node node) {

    EntityManager em = getEntityManager();
    Query tree = em.createNamedQuery("tree");
    tree.setParameter("id", node.getId());

    try {
      return tree.getResultList();
    } finally {
      em.close();
    }
  }

  public List<Node> parents(final Node node) {

    EntityManager em = getEntityManager();
    Query children = em.createNamedQuery("parents");
    children.setParameter("id", node.getId());

    try {
      return children.getResultList();
    } finally {
      em.close();
    }
  }

  public List<Node> children(final Node node) {

    EntityManager em = getEntityManager();
    Query children = em.createNamedQuery("children");
    children.setParameter("id", node.getId());

    try {
      return children.getResultList();
    } finally {
      em.close();
    }
  }

  private void begin(final EntityManager em) {
    em.getTransaction().begin();
  }

  private void end(final EntityManager em) {
    em.getTransaction().commit();
  }
}
