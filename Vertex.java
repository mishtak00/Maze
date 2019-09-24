import java.util.ArrayList;

//Vertex class that defines a vertex and implements Comparable
	public class Vertex implements Comparable<Vertex> {
		
		//Declares foundational variables that are to be defined for every vertex
		public Block block;
		public boolean visited;
		public Vertex closest;
		public double distance;
		
		//HashMap that holds all neighbouring vertices with vertex name as key and the vertex itself as value
		public ArrayList<Vertex>neighbours; 
		
		
		//Constructor for Vertex which takes in name, and location.
		public Vertex(Block block) {
			//Sets name and location as per input and visited as false and intializes neighbours hashmap
			this.block = block;
			visited = false; 
			neighbours = new ArrayList<Vertex>();
		}
		
		//returns 1 if the two Vertex are the same; else returns -1
		//Determines two vertices to be equal if both are in the same location
		@Override
		public int compareTo(Vertex other) {
			
			if(other.block.x == this.block.x && other.block.y == this.block.y) {
				return 0;
			
			}
			
			return -1;
		}
		
		//addNeighbour that adds vertex to the neighbours hashmap to keep track of adjacent vertices
		public boolean addNeighbour(Vertex end) {
			
			if(!(this.neighbours.contains(end))) {
				//System.out.println("contains: " + this.neighbours.contains(end));
				this.neighbours.add(end);
				return true;
			}
			return false;
			
		}

		
		//Standard computer generated getters and setters for all variables;
		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public Vertex getClosest() {
			return closest;
		}

		public void setClosest(Vertex closest) {
			this.closest = closest;
		}

		public Block getBlock() {
			return block;
		}

		public ArrayList<Vertex> getNeighbours() {
			return neighbours;
		}

		public void setBlock(Block block) {
			this.block = block;
		}

		public void setNeighbours(ArrayList<Vertex> neighbours) {
			this.neighbours = neighbours;
		}

		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}
		
		
		
	}