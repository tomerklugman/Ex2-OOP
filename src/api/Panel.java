package api;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Panel extends JPanel {

    public graph graph;
    public Algo Algo;

    public int height;
    public int width;

    public double maxX;
    public double minX;
    public double maxY;
    public double minY;

    public Panel(Algo algo, Dimension ScreenSize) {
        this.Algo = algo;
        this.graph = (graph) Algo.getGraph();

        //window size
        this.height = ScreenSize.height / 3;
        this.width = ScreenSize.width / 3;
        this.setPreferredSize(new Dimension(ScreenSize.width / 2, ScreenSize.height / 2));

        this.maxX = graph.maxX();
        this.minX = graph.minX();
        this.maxY = graph.maxY();
        this.minY = graph.minY();
    }

    @Override
    public void paint(Graphics g) {

        // draw nodes
        Iterator<NodeData> iter1 = graph.nodeIter();
        while (iter1.hasNext()) {
            Node src = (Node) iter1.next(); // src node

            double x1 = (src.getLocation().x() - minX) * (width / (maxX - minX)) + 30; // get x and create some space
            double y1 = (src.getLocation().y() - minY) * (height / (maxY - minY)) + 30; // get y

            g.fillOval((int) x1, (int) y1,10,10);
            g.setColor(Color.white);
            g.drawString("" + src.getKey(), (int) x1, (int) y1);
            g.setFont((new Font("Arial", Font.PLAIN, 15)));
            // draw edges

            Iterator<EdgeData> iter2 = graph.edgeIter(src.getKey());

            if (src.getKey() < this.Algo.getGraph().nodeSize() - 1) {
                while (iter2.hasNext()) {
                    Edge edge = (Edge) iter2.next();
                    Node dest = (Node) Algo.getGraph().getNode(edge.getDest()); // dest node

                    double x2 = (dest.getLocation().x() - minX) * (width / (maxX - minX)) + 30;
                    double y2 = (dest.getLocation().y() - minY) * (height / (maxY - minY)) + 30;

                    g.setColor(Color.green);
                    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

                }
            }
            //draw edge of new node
            if (src.getKey() == this.Algo.getGraph().nodeSize()) {

                Iterator<EdgeData> iter3 = this.Algo.getGraph().edgeIter(src.getKey());
                while (iter3.hasNext()) {
                    Edge edge = (Edge) iter3.next();
                    Node dest = (Node) Algo.getGraph().getNode(edge.getDest()); // dest node

                    double x2 = (dest.getLocation().x() - minX) * (width / (maxX - minX)) + 30;
                    double y2 = (dest.getLocation().y() - minY) * (height / (maxY - minY)) + 30;

                    g.setColor(Color.green);
                    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                }
            }
        }
    }
}