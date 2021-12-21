package api;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class graphTest {


    graph gr = new graph();
    Geo g = new Geo(0, 0, 0);
    Node n1 = new Node(1, g);
    Node n2 = new Node(2, g);


    @Test
    void TestgetNode() {

        gr.addNode(n1);

        assertEquals(gr.getNode(1), n1);

    }

    @Test
    void TestgetEdge() {

        gr.addNode(n1);
        gr.addNode(n2);

        gr.connect(n1.getKey(), n2.getKey(), 1);
        EdgeData e = gr.getEdge(1, 2);

        assertEquals(gr.getEdge(1, 2), e);

    }

    @Test
    void TestAddNode() {

        gr.addNode(n1);
        gr.addNode(n2);

        assertEquals(gr.getNode(1), n1);
        assertEquals(gr.getNode(2), n2);

    }

    @Test
    void Testconnect() {
        gr.addNode(n1);
        gr.addNode(n2);

        gr.connect(n1.getKey(), n2.getKey(), 1);
        EdgeData e = gr.getEdge(1, 2);
        assertEquals(gr.getEdge(1, 2), e);
    }


    @Test
    void TestnodeIter() {
        gr.addNode(n1);

        Iterator<NodeData> iter = gr.nodeIter();
        assertEquals(gr.getNode(1), iter.next());

    }

    @Test
    void TestedgeIter() {
        gr.addNode(n1);
        gr.addNode(n2);

        gr.connect(n1.getKey(), n2.getKey(), 1);

        Iterator<EdgeData> iter = gr.edgeIter();

        assertEquals(gr.getEdge(1, 2), iter.next());
    }

    @Test
    void TestremoveNode() {
        gr.addNode(n1);

        gr.removeNode(1);

        assertEquals(gr.getNode(1), null);

    }

    @Test
    void TestremoveEdge() {
        gr.addNode(n1);
        gr.addNode(n2);

        gr.connect(n1.getKey(), n2.getKey(), 1);

        gr.removeEdge(1, 2);
        assertEquals(gr.getEdge(1, 2), null);
    }

    @Test
    void TestnodeSize() {
        gr.addNode(n1);
        gr.addNode(n2);

        assertEquals(gr.nodeSize(), 2);

    }

    @Test
    void TestedgeSize() {
        gr.addNode(n1);
        gr.addNode(n2);

        gr.connect(n1.getKey(), n2.getKey(), 1);

        assertEquals(gr.edgeSize(), 1);

        gr.connect(n2.getKey(), n1.getKey(), 1);

        assertEquals(gr.edgeSize(), 2);


    }


}
