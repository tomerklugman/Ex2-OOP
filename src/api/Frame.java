package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Frame extends JFrame implements ActionListener {

    Algo Algo;
    Panel panel;

    JMenuItem save = new JMenuItem("save");
    JMenuItem load = new JMenuItem("load");

    JMenuItem addNode = new JMenuItem("addNode");
    JMenuItem removeNode = new JMenuItem("removeNode");
    JMenuItem removeEdge = new JMenuItem("removeEdge");
    JMenuItem connect = new JMenuItem("connect");


    JMenuItem isConnected = new JMenuItem("isConnected");
    JMenuItem shortestPathDist = new JMenuItem("shortestPathDist");
    JMenuItem shortestPath = new JMenuItem("shortestPath");
    JMenuItem center = new JMenuItem("center");
    JMenuItem tsp = new JMenuItem("tsp");


    public Frame(Algo algo) {
        this.Algo = algo;
        panel = new Panel(Algo, Toolkit.getDefaultToolkit().getScreenSize());
        this.add(panel);

        this.setBackground(Color.black);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();


    }

    public void topMenu() {

        JMenuBar top = new JMenuBar();
        JMenu file = new JMenu("file");
        JMenu editGraph = new JMenu("editGraph");
        JMenu Algos = new JMenu("Algos");
        top.add(file);
        top.add(editGraph);
        top.add(Algos);
        this.setJMenuBar(top);

        //file
        file.add(save);
        file.add(load);
        save.addActionListener(this);
        load.addActionListener(this);

        //edit graph
        editGraph.add(addNode);
        editGraph.add(removeNode);
        editGraph.add(removeEdge);
        editGraph.add(connect);
        addNode.addActionListener(this);
        removeNode.addActionListener(this);
        removeEdge.addActionListener(this);
        connect.addActionListener(this);

        //algos
        Algos.add(isConnected);
        Algos.add(shortestPathDist);
        Algos.add(shortestPath);
        Algos.add(center);
        Algos.add(tsp);
        isConnected.addActionListener(this);
        shortestPathDist.addActionListener(this);
        shortestPath.addActionListener(this);
        center.addActionListener(this);
        tsp.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == save) {
            try {
                String save = JOptionPane.showInputDialog("input graph name - will be saved in local dir");
                this.Algo.save(save);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }
        } else if (e.getSource() == load) {

            try {
                String load = JOptionPane.showInputDialog("input graph directory like C:\\G1.json");
                Algo.load(load);
                panel.graph = (graph) Algo.getGraph();
                repaint();
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == addNode) {
            try {
                String name = JOptionPane.showInputDialog(this, "please enter node location (x,y) like 1,2");
                String[] split = name.split(",");
                Node n = new Node(this.Algo.getGraph().nodeSize(), new Geo(Double.parseDouble(split[0]), Double.parseDouble(split[1]), 0));
                this.Algo.getGraph().addNode(n);
                repaint();
                JOptionPane.showMessageDialog(this, "node id is:" + (this.Algo.getGraph().nodeSize() - 1));
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == removeNode) {
            try {
                String name = JOptionPane.showInputDialog("insert node id");
                int remove = Integer.parseInt(name);
                repaint();
                Algo.getGraph().removeNode(remove);

                JOptionPane.showMessageDialog(this, "node " + remove + " removed!");
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == removeEdge) {
            try {
                String name = JOptionPane.showInputDialog(this, "enter src and dest like 1,2");
                String[] split = name.split(",");
                int src = Integer.parseInt(split[0]);
                int dest = Integer.parseInt(split[1]);
                Algo.getGraph().removeEdge(src, dest);
                Algo.getGraph().removeEdge(dest, src);
                repaint();
                JOptionPane.showMessageDialog(this, "edge from " + src + "to" + dest + " removed!");
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == connect) {
            try {
                String name = JOptionPane.showInputDialog(this, "please enter (src,dest,weight) like (1,2,3)");
                String[] split = name.split(",");
                int src_node_index = Integer.parseInt(split[0]);
                int dest_node_index = Integer.parseInt(split[1]);
                double weight = Double.parseDouble(split[2]);
                Algo.getGraph().connect(src_node_index, dest_node_index, weight);
                Algo.getGraph().connect(dest_node_index, src_node_index, weight);
                repaint();
                JOptionPane.showMessageDialog(this, src_node_index + " now connected with " + dest_node_index + " with weight of: " + weight);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == isConnected) {
            try {
                boolean connected = Algo.isConnected();
                repaint();
                JOptionPane.showMessageDialog(this, connected);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == shortestPathDist) {
            try {
                String name = JOptionPane.showInputDialog(this, "enter src and dest like 1,2");
                String[] split = name.split(",");

                int src = Integer.parseInt(split[0]);
                int dest = Integer.parseInt(split[1]);

                double dist = Algo.shortestPathDist(src, dest);
                JOptionPane.showMessageDialog(this, dist);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == shortestPath) {
            try {
                String name = JOptionPane.showInputDialog(this, "enter src and dest like 1,2");
                String[] split = name.split(",");

                int src = Integer.parseInt(split[0]);
                int dest = Integer.parseInt(split[1]);

                List<NodeData> list = Algo.shortestPath(src, dest);
                repaint();
                JOptionPane.showMessageDialog(this, list.toString());
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == center) {
            try {
                Node node = (Node) Algo.center();
                int center = node.getKey();
                repaint();
                JOptionPane.showMessageDialog(this, center);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }

        } else if (e.getSource() == tsp) {
            try {
                String name = JOptionPane.showInputDialog(this, "enter a list of nodes like 1,2,3,4");
                String[] split = name.split(",");

                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = 0; i < split.length; i++) {
                    list.add(Integer.parseInt(split[i]));
                }
                ArrayList<NodeData> tspList = new ArrayList<NodeData>();
                for (int i = 0; i < list.size(); i++) {
                    tspList.add(this.Algo.getGraph().getNode(list.get(i)));
                }
                ArrayList<NodeData> tsp = (ArrayList<NodeData>) this.Algo.tsp(tspList);
                repaint();
                JOptionPane.showMessageDialog(this, tsp.toString());
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, "failed message: " + err.getMessage());
            }
        }
    }
}


