import java.util.ArrayList;

//Graph class that defines a Graph 
	public class Graph {

		//Declares foundational variables that are to be defined for every graph
		public String name;
		
		//ArrayLists that holds all vertices and edges 
		ArrayList<Vertex> vertices;
		ArrayList<Edge> edges;
		
		
		//Constructor for Edge which takes in a name 
		public Graph(String name) {
			//Sets name as per input, and initializes HashMaps tracking vertices and edges in the graph
			this.name = name;
			this.vertices = new ArrayList<Vertex>();
			this.edges = new ArrayList<Edge>();
		}
		
		//Constructor for Edge which takes in a name and HashMaps for vertices and edges
		public Graph(String name, ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
			//Sets name, vertices and edges as per input
			this.name = name;
			this.vertices = new ArrayList<Vertex>();
			this.edges = new ArrayList<Edge>();
		}
		
		//Method that adds a vertex to the graph if the graph doesn't contain the same vertex already and returns true if sucess
		public boolean addVertex(Vertex vertex) {
			
			if(!(this.vertices.contains(vertex))) {
				//sets the vertex's name as key and the vertex as the value
				this.vertices.add(vertex);
				return true;
			}
			return false;
			 
		}
		
		
		//Method that adds a edge to the graph if the graph doesn't contain the same vertex already and returns true if success
		public boolean addEdge(Edge edge) {
			
			if(!this.edges.contains(edge)) {
				//sets the edge's name as key and the edge as the value
				this.edges.add(edge);
				
				//adds the edges vertices to the graph
				this.addVertex(edge.getStart());
				this.addVertex(edge.getEnd());
		
				return true;
				
			} else {
				
				return false;
			}
			
		}
		
		public Vertex getVertex(int x, int y) {
			
			Block current;
			
			for(int i = 0; i < vertices.size(); i++) {
				current = vertices.get(i).block;
				
				if(current.x == x && current.y == y) {
					return vertices.get(i);
				}
				
			}
			
			return null;
			
		}
		


	}