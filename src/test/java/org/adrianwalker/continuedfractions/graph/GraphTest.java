package org.adrianwalker.continuedfractions.graph;

import java.util.Arrays;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.adrianwalker.continuedfractions.graph.controller.Controller;
import org.adrianwalker.continuedfractions.graph.entity.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphTest {

  private static EntityManagerFactory emf;

  public GraphTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    emf = Persistence.createEntityManagerFactory("graph");
  }

  @AfterClass
  public static void tearDownClass() {
    emf.close();
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /*
      A
     /|\
    B | C
     \|/
      D
      |
      E
   */
  @Test
  public void testGraph() throws Exception {

    Controller nc = new Controller(emf);
    Graph hierarchy = new Graph(nc);

    Node a = hierarchy.addNode("A");
    Node b = hierarchy.addNode("B");
    Node c = hierarchy.addNode("C");
    Node d = hierarchy.addNode("D");
    Node e = hierarchy.addNode("E");

    hierarchy.addPath(a, 1);
    hierarchy.addPath(b, 1, 1);
    hierarchy.addPath(c, 1, 2);
    hierarchy.addPath(d, 1, 3);
    hierarchy.addPath(d, 1, 1, 1);
    hierarchy.addPath(d, 1, 2, 1);
    hierarchy.addPath(e, 1, 1, 1, 1);
    hierarchy.addPath(e, 1, 2, 1, 1);
    hierarchy.addPath(e, 1, 3, 1);

    // trees
    Assert.assertEquals(Arrays.asList(new String[]{"A", "B", "C", "D", "E"}),
        hierarchy.tree(a).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"B", "D", "E"}),
        hierarchy.tree(b).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"C", "D", "E"}),
        hierarchy.tree(c).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"D", "E"}),
        hierarchy.tree(d).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"E"}),
        hierarchy.tree(e).stream()
        .map(node -> node.getName())
        .collect(toList()));

    // children
    Assert.assertEquals(Arrays.asList(new String[]{"B", "C", "D"}),
        hierarchy.children(a).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"D"}),
        hierarchy.children(b).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"D"}),
        hierarchy.children(c).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"E"}),
        hierarchy.children(d).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{}),
        hierarchy.children(e).stream()
        .map(node -> node.getName())
        .collect(toList()));

    // parents
    Assert.assertEquals(Arrays.asList(new String[]{}),
        hierarchy.parents(a).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"A"}),
        hierarchy.parents(b).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"A"}),
        hierarchy.parents(c).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"A", "B", "C"}),
        hierarchy.parents(d).stream()
        .map(node -> node.getName())
        .collect(toList()));

    Assert.assertEquals(Arrays.asList(new String[]{"D"}),
        hierarchy.parents(e).stream()
        .map(node -> node.getName())
        .collect(toList()));
  }
}
