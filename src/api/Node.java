package api;


import java.util.HashMap;

public class Node implements NodeData, Comparable<NodeData> {


    @Override
    public String toString() {
        return key +"-->";
    }

    private int key;
    private GeoLocation geo;
    private double weight;
    private String info;
    private int tag;
    private HashMap<Integer, EdgeData> NeighbourEdges; // this node connected edges

    // <dest key, the connected edge(this node key-src, dest ,weight)>

    public Node() {
        this.key = 0;
        this.geo = null;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
        this.NeighbourEdges = new HashMap<Integer, EdgeData>();
    }

    public Node(int key, Geo geo) {
        this.key = key;
        this.geo = geo;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
        this.NeighbourEdges = new HashMap<Integer, EdgeData>();
    }

    public Node(Node n) {
        this.key = n.key;
        this.geo = n.geo;
        this.weight = n.weight;
        this.info = n.info;
        this.tag = n.tag;
        this.NeighbourEdges = n.NeighbourEdges;
    }

    @Override
    public int compareTo(NodeData node) { // compare for the algos
        return Double.compare(this.getWeight(), node.getWeight());
    }

    public HashMap<Integer, EdgeData> getEdges() { // return all connected nodes to this node
        return this.NeighbourEdges;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geo = p;

    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
