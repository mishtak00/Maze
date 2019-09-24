import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;


//Navigator class that basically is set to find the shortest path between two vertices
public class Navigator {
	
	//Declares variables that are needed in methods defiend in this class
	Vertex current, start, closest;
	LinkedList<Vertex> path;
	PriorityQueue<Vertex> queue = new PriorityQueue<>(30, new Minimum()); 
	double distance;
	
	
	//Navigate method that takes in a graph and the origin and destination
	public void Navigate(Graph graph, Vertex origin, Vertex destination) {
	
		//Calls Djikstra method to find the shortest path 
		Djikstra(graph, origin, destination);
		
		//Gets the distance from origin to destination
		this.distance = 1;
		
		//initiates path and sets current vertex as destination
		path = new LinkedList<Vertex>();
		
		current = destination;
	
		//basically backtracks from destination to origin taking the shortest path 
		//adds each vertex into path
		while(!(current.equals(origin))) {
		
			path.add(current);
			closest = current.closest;
			current = closest;
			
		}
		
		//adds origin into the path
		path.add(current);
		
		path = reverse(path);
	}
	
	//Djikstra method to find the shortest path 
	public void Djikstra(Graph graph, Vertex origin, Vertex destination) {
		
		//sets all vertices distance to -1 and visited as false;
		for(Vertex v : graph.vertices) {
			v.setDistance(-1);
			v.setVisited(false);
		}
		
		//sets origins distance as 0 and visited as true
		origin.setDistance(0);
		origin.setVisited(true);
		
		//initiates a priority Queue with comparator that returns the vertex with the smallest distance value
		queue = new PriorityQueue<>(30, new Minimum()); 
		
		
		//adds all of origins' neighbours' to the que and sets origin as their closest vertex
		for(Vertex v : origin.neighbours) {
			queue.add(v);
			v.closest = origin;
		}
		
		//Runs while queue is not empty or destination has not been encountered
		while(!queue.isEmpty() || current.equals(destination)) {
			
			//polls queue to set current to the closest vertex and sets its visited to true
			current = queue.poll();
			current.setVisited(true);
			
			//runs through all of currents' neighbours 
			for(Vertex v : current.neighbours) {
				
				//if v has not been visited
				if(!v.visited) {
					
					//adds v to queue if its not already there
					if(!queue.contains(v)) {
						queue.offer(v);
					}
					//finds the distance between v and current
					double length = 1;
					
					//if distance from origin to v is shorter than previously set distance
					//changes distance and sets closest to current
					if(v.distance == -1) {
						v.distance = length + current.distance;
						v.closest = current;
					} else if (v.distance > length + current.distance) {
						v.distance = length + current.distance;
						v.closest = current;
					}
					
				}
				
			}
			
		}	
		
	}
	
	public int findDistance() {
		return 1;
	}


	//Comparator to set PriorityQueue's priority inversely i.e. smaller is higher priority based on distance values
	public class Minimum implements Comparator<Vertex>{
				
			@Override
			public int compare(Vertex a, Vertex b) {
				
				
				if (a.getDistance() > b.getDistance()) {
					return 1;
					}
					
				if (a.getDistance() < b.getDistance()) {
					return -1;
				}
					
				return 0;
			}
		}
	
	public static LinkedList<Vertex> reverse(LinkedList<Vertex> vl) {
		for (int i=0; i<vl.size()/2; i++) {
			Vertex dummy = vl.get(vl.size()-i-1);
			vl.set(vl.size()-i-1, vl.get(i));
			vl.set(i, dummy);
		}
		return vl;
	}

}