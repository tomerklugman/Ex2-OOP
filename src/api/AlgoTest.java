package api;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoTest {

    graph gr = new graph();
    DirectedWeightedGraphAlgorithms alg = new Algo();
    Geo g = new Geo(0, 0, 0);


    @Test
    void Testinit() {

        alg.init(gr);
        alg.load("data/G1.json");
        Iterator<NodeData> Iter = gr.nodeIter();
        while (Iter.hasNext()) {
            NodeData node = Iter.next();
            assertEquals(node, alg.getGraph().getNode(node.getKey()));
        }
    }

    @Test
    void TestgetGraph() {

        alg.load("data/G1.json");
        DirectedWeightedGraph gr = alg.getGraph();
        assertEquals(gr, alg.getGraph());
    }

    @Test
    void Testcopy() {
        alg.load("data/G1.json");
        DirectedWeightedGraph gr = alg.copy();
        Iterator<NodeData> Iter = gr.nodeIter();
        while (Iter.hasNext()) {
            NodeData node = Iter.next();
            assertEquals(node, alg.getGraph().getNode(node.getKey()));
        }

    }

    @Test
    void TestisConnected() {
        alg.load("data/G1.json");

        assertTrue(alg.isConnected());

        alg.getGraph().addNode(new Node(9999, g));

        assertFalse(alg.isConnected());

    }

    @Test
    void TestshortestPathDist() {
        alg.load("data/G1.json");
        assertEquals(1.3118716362419698, alg.shortestPathDist(0, 16));
        alg.load("data/G2.json");
        assertEquals(-1, alg.shortestPathDist(0, 31));
        alg.load("data/G3.json");
        assertEquals(21.075933175087, alg.shortestPathDist(0, 47));

    }

    @Test
    void TestshortestPath() {
        alg.load("data/G1.json");

        List<NodeData> checklist = new LinkedList<>();
        checklist.add(alg.getGraph().getNode(2));
        checklist.add(alg.getGraph().getNode(6));
        checklist.add(alg.getGraph().getNode(7));

        assertEquals(checklist, alg.shortestPath(2, 7));
    }

    @Test
    void Testcenter() {
        alg.load("data/G3.json");
        assertEquals(8, alg.center().getKey());
    }

    @Test
    void Testtsp() {
        alg.load("data/G1.json");
        List<NodeData> checklist = new LinkedList<>();
        checklist.add(alg.getGraph().getNode(2));
        checklist.add(alg.getGraph().getNode(5));
        checklist.add(alg.getGraph().getNode(16));
        List<NodeData> tsp = new LinkedList<>();
        tsp.add(alg.getGraph().getNode(2));
        tsp.add(alg.getGraph().getNode(3));
        tsp.add(alg.getGraph().getNode(4));
        tsp.add(alg.getGraph().getNode(5));
        tsp.add(alg.getGraph().getNode(4));
        tsp.add(alg.getGraph().getNode(3));
        tsp.add(alg.getGraph().getNode(2));
        tsp.add(alg.getGraph().getNode(1));
        tsp.add(alg.getGraph().getNode(0));
        tsp.add(alg.getGraph().getNode(16));

        assertEquals(tsp, alg.tsp(checklist));

    }

    @Test
    void Testsaveload() {
        Node n1 = new Node(1, g);
        Node n2 = new Node(2, g);

        alg.getGraph().addNode(n1);
        alg.getGraph().addNode(n2);
        alg.getGraph().connect(1, 2, 1);
        alg.getGraph().connect(2, 1, 1);

        alg.save("check.json");
        alg.load("check.json");
    }

}