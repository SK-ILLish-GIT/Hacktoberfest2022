//1.     Initialize the distance from the source node S to all other nodes as infinite (999999999999) and to itself as 0.
//2.    Insert an object of < node, distance > for source i.e < S, 0 > in a priority Queue
//       where the priority of the elements in the queue is based on the length of the distance.
//3.    While the Priority Queue is not empty do
//4.       object_at_top = PriorityQueue. peek();
//         Remove the object from the top of the Priority Queue.
//         current_source_node = object_at_top . node.
//5.       For every adjacent_node to current_source_node do
//6.            If ( distance [ adjacent_node ] > length_of_path_to_adjacent_node_from_source + distance [ current_source_node ] ) then
//7.                 distance [ adjacent_node ] = length_of_path_to_adjacent_node_from_source + distance [ current_source_node ]
//8.                 Update the Priority Queue with the new object < adjacent_node, distance [ adjacent_node ] >
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Comparator;

class NodeDist {

    int node; // Adjacent node
    long dist; // Distance to adjacent node

    NodeDist (int node, long dist) {
        this.node = node;
        this.dist = dist;
    }
}

class Dijkstras {

    void ShortestPath (int source_node, int node_count, List<List<NodeDist>> graph) {

        // Assume that the distance from the source_node to other nodes is infinite 
        // in the beginnging, i.e initialize the distance list to a max value
        Long INF = (long) 999999999;
        List<Long> dist = new ArrayList<Long>(Collections.nCopies(node_count, INF));

        // Distance from the source vertex to itself is 0
        dist.set(source_node, (long) 0); // (Node, Dist)

        // Comparator lambda function that enables the priority queue to store the nodes
        // based on the distance in the ascending order.
        Comparator<NodeDist> NodeDistComparator = (obj1, obj2) -> {
            if (obj1.dist < obj2.dist)
                return 1;
            if (obj1.dist > obj2.dist)
                return -1; 
            return 0;
        };

        // Priority queue stores the object node-distance into the queue with 
        // the smallest distance node at the top.
        PriorityQueue<NodeDist> pq = new PriorityQueue<>(NodeDistComparator);

        pq.add(new NodeDist(source_node, 0));

        while (!pq.isEmpty()) {

            NodeDist obj = pq.peek();
            pq.remove();

            int current_source = obj.node;

            for (NodeDist obj_node_dist : graph.get(current_source)) {

                int adj_node = obj_node_dist.node;
                long length_to_adjnode = obj_node_dist.dist;

                // Edge relaxation 
                if (dist.get(adj_node) > length_to_adjnode + dist.get(current_source)) {

                    // If the distance to the adjacent node is not INF, means the object <node, dist> is in the priority queue.
                    // Remove the object before updating it in the priority queue.
                    if (dist.get(adj_node) != INF) {
                        pq.remove(new NodeDist(adj_node, dist.get(adj_node)));
                    }
                    dist.set(adj_node, length_to_adjnode + dist.get(current_source));
                    pq.add(new NodeDist(adj_node, dist.get(adj_node)));
                }
            }
        }

        for (int i=0; i<node_count; i++)
            System.out.println("Source Node(" + source_node + ") -> Destination Node(" + i + ") : " + dist.get(i));
    }

    public static void main(String args[]) {

        int node_count = 6;
        List<List<NodeDist>> graph = new ArrayList<>(node_count);

        for(int i=0; i<node_count; i++) {
            graph.add(new ArrayList<>());
        }

        // Node 0: <1,5> <2,1> <3,4>
        Collections.addAll(graph.get(0), new NodeDist(1, 5), new NodeDist(2, 1), new NodeDist(3, 4));

        // Node 1: <0,5> <2,3> <4,8>
        Collections.addAll(graph.get(1), new NodeDist(0, 5), new NodeDist(2, 3), new NodeDist(4, 8));

        // Node 2: <0,1> <1,3> <3,2> <4,1>
        Collections.addAll(graph.get(2), new NodeDist(0, 1), new NodeDist(1, 3), new NodeDist(3, 2), new NodeDist(4, 1));

        // Node 3: <0,4> <2,2> <4,2> <5,1>
        Collections.addAll(graph.get(3), new NodeDist(0, 4), new NodeDist(2, 2), new NodeDist(4, 2), new NodeDist(5, 1));

        // Node 4: <1,8> <2,1> <3,2> <5,3>
        Collections.addAll(graph.get(4), new NodeDist(1, 8), new NodeDist(2, 1), new NodeDist(3, 2), new NodeDist(5, 3));

        // Node 5: <3,1> <4,3> 
        Collections.addAll(graph.get(5), new NodeDist(3, 1), new NodeDist(4, 3));

        int source_node = 0;
        Dijkstras d = new Dijkstras();
        d.ShortestPath(source_node, node_count, graph);

        System.out.println();
        source_node = 5;
        d.ShortestPath(source_node, node_count, graph);
    }   
}
