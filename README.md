
# Ex2-OOP
# weighted directed graph


- creation of a graph data structure which contains: 
- NodeData[key,Info("visited","not visited") , each node contains a hashmap of its neighbor nodes 
- weighted directed graph each graph contains a hashmap(key:node) of the nodes in it and an array of edges.
- each graph has implemented algorithms used by bfs and Dijkstra's algorithms: 
1) copy() - deep copy of this graph
1000 nodes- 151 ms
10000 nodes- 493 ms
2) isConnected() - checks if all nodes in this graph are connected
1000 nodes- 202 ms
10000 nodes-1 sec 226 ms
3) shortestPath(src,dest) - find the shortest path from source node to destination node  
1000 nodes- 219 ms
10000 nodes-1 sec 287 ms   
4) shortestPathDist(src,dest) - find the length of the path from source node to destination node
1000 nodes- 221 ms
10000 nodes-1 sec 278 ms  
5) tsp(List<NodeData> cities) - find the cheapest weight from a chosen list of nodes
1000 nodes- 237 ms
10000 nodes-2 sec 21 ms  
6) save(),load() - save and load graph from file - load and then save test
1000 nodes- 296 ms
10000 nodes-1 sec 602 ms  
-------------

### helpful Links

https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF 

https://www.youtube.com/watch?v=s-CYnVz-uh4 

https://steemit.com/graphs/@drifter1/programming-java-graph-shortest-path-algorithm-dijkstra

https://www.programiz.com/dsa/graph-bfs

https://www.geeksforgeeks.org/traveling-salesman-problem-tsp-implementation/

----
### UML
![alt text](https://i.imgur.com/OwsvaHE.png)
## how to run
open cmd in jar folder and choose a json file insted of G1.json (G1-G3)
```sh
java -jar Ex2.jar G1.json
```

