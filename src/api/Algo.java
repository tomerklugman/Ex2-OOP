package api;

import java.io.*;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Algo implements DirectedWeightedGraphAlgorithms {
    /*
        public static void main(String[] args) {

            graph gr = new graph();
            DirectedWeightedGraphAlgorithms ag0 = new Algo();
            ag0.init(gr);

            Geo g = new Geo(0, 0, 0);
            Node n0 = new Node(0, g);
            Node n1 = new Node(1, g);
            Node n2 = new Node(2, g);
            Node n3 = new Node(3, g);
            Node n4 = new Node(4, g);

            gr.addNode(n0);
            gr.addNode(n1);
            gr.connect(n0.getKey(), n1.getKey(), 100);
            gr.connect(n1.getKey(), n0.getKey(), 100);
            gr.addNode(n2);
            gr.connect(n1.getKey(), n2.getKey(), 5);
            gr.connect(n2.getKey(), n1.getKey(), 5);
            gr.addNode(n3);
            gr.connect(n2.getKey(), n3.getKey(), 1000000);
            gr.connect(n3.getKey(), n2.getKey(), 1000000);
            gr.addNode(n4);
            gr.connect(n0.getKey(), n4.getKey(), 10000);
            gr.connect(n4.getKey(), n0.getKey(), 10000);


            System.out.println(ag0.isConnected());
            System.out.println(ag0.shortestPath(0, 1));
            System.out.println(ag0.shortestPathDist(0, 4));
            System.out.println(ag0.center());
            System.out.println(ag0.tsp(ag0.shortestPath(0, 1)));

            graph gr3 = new graph();
            DirectedWeightedGraphAlgorithms ag1 = new Algo();
            ag1.init(gr3);
            ag1.load("C:\\Users\\TomerPC\\Desktop\\Ex2\\data\\G1.json");
            System.out.println(ag1.isConnected());
            System.out.println(ag1.shortestPath(8, 13));
            System.out.println(ag1.tsp(ag1.shortestPath(8, 13)));
            System.out.println(ag1.shortestPathDist(8, 13));
            System.out.println(ag1.center());
            System.out.println(ag1.getGraph().getNode(1).getLocation().z());
            // ag0.save("a");


        }
    */
    private DirectedWeightedGraph graphAlgo;

    public Algo() {
        this.graphAlgo = new graph();
    }

    public Algo(graph graph) {
        init(graph);

    }


    @Override
    public void init(DirectedWeightedGraph g) {
        this.graphAlgo = (graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graphAlgo;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new graph((graph) this.graphAlgo); // deep copy in graph constructor class
    }


    @Override
    public boolean isConnected() {
        resetNodes();
        if (graphAlgo.nodeSize() == 1) {
            return true;
        }
        int count = 1;    // +1 for first node
        Queue<NodeData> queue = new LinkedList<NodeData>();
        Iterator<EdgeData> iter1 = graphAlgo.edgeIter();
        // get first node to add to queue
        EdgeData startNode = iter1.next();
        queue.add(this.graphAlgo.getNode(startNode.getSrc()));


        while (!queue.isEmpty()) {
            NodeData nodeCheck = queue.remove();
            nodeCheck.setInfo("visited"); // set connected nodes to first node as visited
            Iterator<EdgeData> itr = this.graphAlgo.edgeIter(nodeCheck.getKey()); // all neighbors of nodes in queue
            while (itr.hasNext()) {
                EdgeData neighbor = itr.next();
                NodeData neighborNode = this.graphAlgo.getNode(neighbor.getDest());
                if (neighborNode.getInfo().equals("not visited")) {
                    queue.add(neighborNode);
                    neighborNode.setInfo("visited");      // change visited node to "visited"
                    count++; // if the count of visited nodes equal to nodes in graph then all connected
                }
            }
        }
        resetNodes(); // reset for djs algo
        if (graphAlgo.nodeSize() == count)
            return true;
        else
            return false;


    }

    public void resetNodes() { // reset nodes to not visited and weight 0 after algos
        Iterator<NodeData> iter = graphAlgo.nodeIter();
        while (iter.hasNext()) {
            Node resetNode = (Node) iter.next();
            resetNode.setInfo("not visited");
            resetNode.setWeight(0.0);

        }
    }

    public void setVisited(List<NodeData> cities) {
        Iterator<NodeData> iter = cities.iterator();
        while (iter.hasNext()) {
            Node resetNode = (Node) iter.next();
            resetNode.setInfo("visited");
            resetNode.setWeight(0.0);
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        resetNodes();
        if (this.graphAlgo.getNode(src) == null || this.graphAlgo.getNode(dest) == null) {
            return -1;
        }
        LinkedList<NodeData> shortestPath = new LinkedList<NodeData>();
        shortestPath = (LinkedList<NodeData>) shortestPath(src, dest);

        if (shortestPath.size() > 0) {
            return shortestPath.get(shortestPath.size() - 1).getWeight();
        }
        return -1;
    }


    public HashMap<NodeData, NodeData> djs(int src) {  // Dijkstra's Algorithm

        PriorityQueue<NodeData> queue = new PriorityQueue<>();
        HashMap<NodeData, NodeData> parent = new HashMap<>();  // parent from where we visited node:parent
        queue.add(graphAlgo.getNode(src));
        while (!queue.isEmpty()) {
            NodeData curr = queue.remove();    // remove v0   and set to visited
            if (curr.getInfo().equals("not visited")) {
                curr.setInfo("visited");
                Iterator<EdgeData> itr = this.graphAlgo.edgeIter(curr.getKey()); // get v0 neighbors
                while (itr.hasNext()) {
                    EdgeData neighbor = itr.next();
                    NodeData neighborNode = this.graphAlgo.getNode(neighbor.getDest());
                    if (neighborNode.getInfo().equals("not visited")) {
                        neighborNode.setWeight(graphAlgo.getEdge(curr.getKey(), neighborNode.getKey()).getWeight() + curr.getWeight()); // sum nodes distance-edges and update at this neighbor
                        parent.put(neighborNode, curr); // update parent of this node
                        queue.add(neighborNode); // add to priority queue
                    }
                }
            }
        }
        return parent;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        resetNodes();
        LinkedList<NodeData> shortestPath = new LinkedList<>(); // save path
        HashMap<NodeData, NodeData> parent = new HashMap<>();  // get from djs algo
        parent = djs(src);
        NodeData node = graphAlgo.getNode(dest);
        while (node.getKey() != src) { // we return back to src from dest from the parent of each node
            shortestPath.addFirst(node);
            node = parent.get(node);
        }
        shortestPath.addFirst(node);    // add src node
        return shortestPath;
    }

    @Override
    public NodeData center() {
        if (!isConnected()) {
            return null;
        }

        NodeData center = new Node(); // node with min distance to all other nodes
        center.setWeight(Double.MAX_VALUE);

        // lets find each node to max distance to other nodes
        for (int Node = 0; Node < graphAlgo.nodeSize(); Node++) { // get first node
            Double OldMaxDist = Double.MIN_VALUE; // reset max dist from other node
            for (int CmpNode = 0; CmpNode < graphAlgo.nodeSize(); CmpNode++) { // compare to other nodes
                if (Node != CmpNode) {
                    Double NewMaxDist = shortestPathDist(graphAlgo.getNode(Node).getKey(), graphAlgo.getNode(CmpNode).getKey()); // get distance
                    if (NewMaxDist > OldMaxDist) {
                        OldMaxDist = NewMaxDist;
                    }
                }
            }
            //set center node
            if (center.getWeight() > OldMaxDist) {
                center = new Node((Node) graphAlgo.getNode(Node));
                center.setWeight(OldMaxDist);
            }
        }
        return center;
    }


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

        ArrayList<NodeData> arrTarget = new ArrayList<NodeData>(cities);
        List<NodeData> Tsplist = new ArrayList<NodeData>();

        setVisited(cities); //reset this list of cities
        Iterator<NodeData> iter = arrTarget.iterator();
        NodeData srcKey = iter.next();
        NodeData destKey;

        while (iter.hasNext()) {
            destKey = iter.next();

            Node dest = (Node) this.graphAlgo.getNode(destKey.getKey());

            if (dest.getInfo() != "not visited") {

                Tsplist.addAll(shortestPath(srcKey.getKey(), destKey.getKey())); // add all to the tsp list

                srcKey = destKey;
                // delete duplicate
                if (iter.hasNext()) {
                    Tsplist.remove(Tsplist.size() - 1);
                }
            } else {
                Tsplist.add((Node) this.graphAlgo.getNode(srcKey.getKey()));
            }
        }
        return Tsplist;
    }


    @Override
    public boolean save(String file) {

        JsonObject graph = new JsonObject();
        JsonArray nodes = new JsonArray();
        JsonArray edges = new JsonArray();

        // saves nodes
        Iterator<NodeData> nodeiter = graphAlgo.nodeIter();
        while (nodeiter.hasNext()) {
            Node node = (Node) nodeiter.next();
            JsonObject jsonNodes = new JsonObject();
            jsonNodes.addProperty("pos", node.getLocation().x() + "," + node.getLocation().y() + "," + node.getLocation().z());
            jsonNodes.addProperty("id", node.getKey());
            nodes.add(jsonNodes);

            // save edges
            Iterator<EdgeData> edgeiter = graphAlgo.edgeIter(node.getKey());
            while (edgeiter.hasNext()) {
                Edge edge = (Edge) edgeiter.next();
                JsonObject jsonEdges = new JsonObject();
                jsonEdges.addProperty("src", edge.getSrc());
                jsonEdges.addProperty("w", edge.getWeight());
                jsonEdges.addProperty("dest", edge.getDest());
                edges.add(jsonEdges);
            }
        }
        // add to json graph
        graph.add("Edges", edges);
        graph.add("Nodes", nodes);
        try {
            FileWriter fileWrite = new FileWriter(file);
            fileWrite.write(String.valueOf(graph));
            fileWrite.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {

            FileReader jsonFile = new FileReader(file);
            JsonElement elements = JsonParser.parseReader(jsonFile);
            JsonObject graphLOAD = elements.getAsJsonObject();

            JsonArray nodes = graphLOAD.getAsJsonArray("Nodes");
            JsonArray edges = graphLOAD.getAsJsonArray("Edges");
            DirectedWeightedGraph graph = new graph();

            // load nodes
            for (JsonElement node : nodes) {
                JsonObject jsonNode = node.getAsJsonObject();
                //Geo location
                String positionString = jsonNode.get("pos").getAsString();
                String[] splitGeo = positionString.split(",");
                double x = Double.parseDouble(splitGeo[0]);
                double y = Double.parseDouble(splitGeo[1]);
                double z = Double.parseDouble(splitGeo[2]);

                //add node
                Node n = new Node(jsonNode.get("id").getAsInt(), new Geo(x, y, z));
                graph.addNode(n);
            }
            // load edges
            for (JsonElement edge : edges) {
                JsonObject jsonEdge = edge.getAsJsonObject();
                // connect edges
                int src = jsonEdge.get("src").getAsInt();
                int dest = jsonEdge.get("dest").getAsInt();
                double w = jsonEdge.get("w").getAsDouble();
                graph.connect(src, dest, w);
            }

            init(graph);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }
}
