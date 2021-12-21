package api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class graph implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> nodes; // all nodes in graph <key,node>
    private ArrayList<EdgeData> edgesList; // all edges in graph <edge>
    private int mc;

    public graph() {
        this.nodes = new HashMap<Integer, NodeData>();
        this.edgesList = new ArrayList<EdgeData>();
        this.mc = 0;
    }


    public graph(graph g) { // deep copy

        this.nodes = new HashMap<Integer, NodeData>();
        Iterator<NodeData> iter1 = g.nodeIter(); // deep copy nodes
        while (iter1.hasNext()) {
            Node curr = (Node) iter1.next();
            this.nodes.put(curr.getKey(), curr);
        }

        this.edgesList = new ArrayList<EdgeData>();
        Iterator<EdgeData> iter2 = g.edgeIter(); // deep copy edges
        while (iter2.hasNext()) {
            Edge curr = (Edge) iter2.next();
            this.edgesList.add(curr);
        }

        this.mc = g.mc;
    }


    @Override
    public NodeData getNode(int key) {
        if (!nodes.containsKey(key))
            return null; // node not in graph -- none
        else
            return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {

        return ((Node) this.nodes.get(src)).getEdges().get(dest); //return null if no edge
    }

    @Override
    public void addNode(NodeData n) {
        if (n == null) {
            return;
        }
        this.nodes.put(n.getKey(), n); //add to graph nodes hashmap
        this.mc = mc + 1;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src == dest) {
            throw new RuntimeException("no edge to itself");
        }
        if (this.nodes.get(src) == null) {
            throw new RuntimeException("src node doesnt exist");
        } else if (this.nodes.get(dest) == null) {
            throw new RuntimeException("dest node doesnt exist");
        } else if (w < 0) {
            throw new RuntimeException("weight is negative");
        } else {

            // create edge and connect nodes

            Node SrcNode = (Node) this.nodes.get(src); // src node
            EdgeData edge = new Edge(src, dest, w); // create new edge
            SrcNode.getEdges().put(dest, edge);   // add dest node and put new edge in neighbour hashmap
            edgesList.add(edge); // add edge to graph edges
            this.mc = mc + 1;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return this.nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return edgesList.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Iterator<EdgeData> iter = this.edgeIter();
        while (iter.hasNext()) {
            Edge curr = (Edge) iter.next();
            if (curr.getSrc() == node_id) {
                HashMap<Integer, EdgeData> n = ((Node) this.nodes.get(node_id)).getEdges();
                return n.values().iterator();
            }
        }
        return null;
    }

    @Override
    public NodeData removeNode(int key) {

        if (this.nodes.get(key) == null) {
            throw new RuntimeException("given node doesnt exist");
        }

        // delete edges

        Node node = (Node) this.nodes.get(key);
        Iterator<NodeData> iter = this.nodeIter();

        while (iter.hasNext()) {
            Node curr = (Node) iter.next();
            if (curr.getEdges().get(key) != null) {

                this.removeEdge(node.getKey(), curr.getKey()); //remove edges from neighbour hashmap in node from this node to dest
                this.removeEdge(curr.getKey(), node.getKey()); //remove edges from neighbour hashmap in node from dest to this node

                this.mc = mc + 1;
            }
        }

        // delete node
        this.mc = mc + 1;
        return this.nodes.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (src == dest) {
            throw new RuntimeException("no edge to itself");
        }
        if (this.nodes.get(src) == null) {
            throw new RuntimeException("src node doesnt exist");
        } else if (this.nodes.get(dest) == null) {
            throw new RuntimeException("dest node doesnt exist");
        } else {

            Node SrcNode = (Node) this.nodes.get(src);
            this.edgesList.remove(SrcNode.getEdges().get(dest)); // remove edges from graph edges array
            this.mc = mc + 1;
            return SrcNode.getEdges().remove(dest); //remove edge from nodes neighbours hashmap
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgesList.size();
    }

    @Override
    public int getMC() {
        return mc;
    }

    public double maxX() {
        double x = 0;
        Iterator<NodeData> iter = nodeIter();
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n.getLocation().x() > x) {
               x = n.getLocation().x();
            }
        }
        return x;
    }

    public double maxY() {
        double y = 0;
        Iterator<NodeData> iter = nodeIter();
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n.getLocation().y() > y) {
                y = n.getLocation().y();
            }
        }
        return y;
    }

    public double minX() {
        double x = Double.MAX_VALUE;
        Iterator<NodeData> iter = nodeIter();
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n.getLocation().x() < x) {
                x = n.getLocation().x();
            }
        }
        return x;
    }

    public double minY() {
        double y = Double.MAX_VALUE;
        Iterator<NodeData> iter = nodeIter();
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n.getLocation().y() < y) {
                y = n.getLocation().y();
            }
        }
        return y;
    }

}

