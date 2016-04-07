package org.adrianwalker.continuedfractions.graph;

import java.math.BigDecimal;
import java.util.List;
import static org.adrianwalker.continuedfractions.Fraction.decimal;
import org.adrianwalker.continuedfractions.controller.Controller;
import org.adrianwalker.continuedfractions.graph.entity.Node;
import org.adrianwalker.continuedfractions.graph.entity.NodePath;
import static org.adrianwalker.continuedfractions.graph.Path.sibling;
import static org.adrianwalker.continuedfractions.Fraction.fraction;

public final class Graph {

  private final Controller controller;

  public Graph(final Controller controller) {

    this.controller = controller;
  }

  public Node addNode(final String name) throws Exception {

    Node node = new Node();
    node.setName(name);

    return controller.create(node);
  }

  public NodePath addPath(final Node node, final int... path) throws Exception {

    NodePath nodePath = new NodePath();
    int[] nvDv = fraction(path);
    BigDecimal id = decimal(nvDv);
    int[] snvSdv = fraction(sibling(path));
    BigDecimal sid = decimal(snvSdv);

    nodePath.setId(id);
    nodePath.setSid(sid);
    nodePath.setHops(path.length);
    nodePath.setNode(node);

    return controller.create(nodePath);
  }

  public List<Node> tree(final Node node) {

    return controller.tree(node);
  }

  public List<Node> parents(final Node node) {

    return controller.parents(node);
  }

  public List<Node> children(final Node node) {

    return controller.children(node);
  }
}