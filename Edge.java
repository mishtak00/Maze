

//Edge class that defines an edge and implements Comparable
	public class Edge implements Comparable<Edge> {
		
		//Declares foundational variables that are to be defined for every edge
		public Vertex start, end;
		public double distance = 1;
		
		//Constructor for Edge which takes in name, and start and end vertices.
		public Edge(Vertex start, Vertex end) {
			//Sets name and start and end as per input, calculates distance between points and sets it to distance
			//and adds start and end as each others' neighbours
			this.start = start;
			this.end = end;
			
			this.start.addNeighbour(end);
			this.end.addNeighbour(start);
		}
		
		
		//if two edges are the same, returns 1 otherwise return -1
		//determines equality with same originating from same vertices (start and end don't matter) 
		//and having same distance
		@Override
		public int compareTo(Edge other) {

			if((this.start.equals(other.start) && this.end.equals(other.end))|| (this.start.equals(other.end) && this.end.equals(other.start))) {
			
				return 1;

			}
			
			return -1;
		}
		

		//Standard computer generated getters and setters for all variables;

		public Vertex getStart() {
			return start;
		}

		public Vertex getEnd() {
			return end;
		}

		public void setStart(Vertex start) {
			this.start = start;
		}

		public void setEnd(Vertex end) {
			this.end = end;
		}
		
		

	}