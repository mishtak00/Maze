import java.awt.Graphics;
import java.util.Stack;

public class Maze {

	int columns, rows;
	Block[][] blocks;
	//add the shit you removed here
	Stack<Block> backtrackPath;
	
	//for UI
	int playerX, playerY;
	int finishX, finishY;
	int starX, starY;
	
	public Maze(int columns, int rows, int width) {
		this.columns = columns;
		this.rows = rows;
		this.blocks = new Block[this.rows][this.columns];
		this.backtrackPath = new Stack<>();
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y ++) {
				//System.out.println("Making Block");
				Block current = new Block(x,y, width, this);
				blocks[x][y] = current;
			}
		}
		
		//initilize player
		putPlayer();
		
		//initialize finish
		putFinish();
		//System.out.println("finishX="+finishX);
		//System.out.println("finishY="+finishY);
		
		//initialize star
		putStar();
	}
	
	public void putPlayer() {
		playerX = (int)(Math.random()*columns); //System.out.println("playerX="+playerX);
		playerY = (int)(Math.random()*rows); //System.out.println("playerY="+playerY);
		blocks[playerX][playerY].containsPlayer = true;
	}
	
	public void putFinish() {
		while (true) {
			finishX = (int)(Math.random()*columns);
			finishY = (int)(Math.random()*rows);
			if (finishX!=playerX && finishY!=playerY) {
				//if start and finish coordinates are more than or equals to 7 diagonal blocks away, then accept those coords
				if (Math.pow(Math.pow((finishX-playerX),2)+Math.pow((finishY-playerY), 2), 0.5) >= 7) {
					break;
				}
			}
		}
		blocks[finishX][finishY].isFinish = true;
	}
	
	public void putStar() {
		while (true) {
			starX = (int)(Math.random()*columns);
			starY = (int)(Math.random()*rows);
			if (starX!=playerX && starY!=playerY && starX!=finishX && starY!=finishY) {
				//if start and finish coordinates are more than or equals to 7 diagonal blocks away, then accept those coords
				double aerialDist = Math.pow(Math.pow((starX-playerX),2)+Math.pow((starY-playerY), 2), 0.5);
				if (aerialDist >= 3 && aerialDist <= 7) {
					break;
				}
			}
		}
		blocks[starX][starY].hasHint = true;
	}
	
	public void create(Graphics g) {
		
		//create Maze
		for (int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y ++) {
				blocks[x][y].draw(g);
			}
		}
		
	}
	
	//DRAW METHOD FOR PLAYER DEAL WITH THIS
	public void drawPlayer(Graphics g, int x, int y, int width, int height) {
		
		g.drawRect(x, y, width, height);
		
	}
	
	public boolean hasUnvisited() {
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y ++) {
				if(!this.blocks[x][y].visited) {
					return true;
					}
				}
			}
		
		return false;
	}
	
	
	public void MazeGenerator(Block current) {
		current.setVisited(true);
		
		current.adjacenctVisitCheck();
		Block neighbour = current.randomNeighbour();
		
		try {
			
		while(!neighbour.equals(null)) {
			if(neighbour.visited == true) {
				break;
			}
			//System.out.println("visted  " + neighbour.visited);
			neighbour.setVisited(true);
			
			if(!backtrackPath.contains(current) && current.pushed == false) {
				//System.out.println("pushing " + neighbour.visited);
				current.setPushed(true);
			backtrackPath.push(current);
			}
			
			removeWall(current, neighbour);
			current = neighbour;
			
			current.adjacenctVisitCheck();
			neighbour = current.randomNeighbour();
			
		}
		
		} catch (NullPointerException e) {
			//System.out.println("No More Neighbours");
			
		}
		
		if(!backtrackPath.isEmpty()) {
			//System.out.println("popping");
			Block holder = backtrackPath.pop();
			MazeGenerator(holder);
		}
	}
	
	public void removeWall(Block current, Block neighbour) {
		 
		int pos_x = current.x - neighbour.x;
		int pos_y = current.y - 	neighbour.y;	
		
		if(pos_x == 1) {
			current.walls.replace("left", false);
			neighbour.walls.replace("right", false);
		} else if (pos_x == -1) {
			current.walls.replace("right", false);
			neighbour.walls.replace("left", false);
		} else if (pos_y == 1) {
			current.walls.replace("top", false);
			neighbour.walls.replace("bottom", false);
		} else if (pos_y == -1) {
			current.walls.replace("bottom", false);
			neighbour.walls.replace("top", false);
		}
			
	}
	
	public Graph toGraph() {
		
		Graph graph = new Graph("Maze Graph");
		
		for(int x = 0; x < this.rows; x++) {
			for(int y = 0; y < this.columns; y ++) {
				Vertex current = new Vertex(this.blocks[x][y]);
				graph.addVertex(current);
				}
			}
		
		for(Vertex vertex: graph.vertices) {
			Block block = vertex.block;
			
			Vertex neighbour;
			Edge edge;
			
			int x, y;
			
			
			//System.out.println("Dealing with Vertex: " + block.x + " " + block.y);
			if(!block.walls.get("top")) {
				x= block.x;
				y= block.y-1;
				//System.out.println("Dealing with top");
				if((x >= 0 && y >= 0 && x < block.maze.columns && y < block.maze.rows)) {
				neighbour = graph.getVertex(x, y);
				edge = new Edge(vertex, neighbour);
				
				graph.edges.add(edge);
				}
			}
			

			if(!block.walls.get("right")) {
				x= block.x+1;
				y= block.y;
				//System.out.println("Dealing with right");
				if((x >= 0 && y >= 0 && x < block.maze.columns && y < block.maze.rows)) {
				neighbour = graph.getVertex(block.x+1, block.y);
				edge = new Edge(vertex, neighbour);
				
				graph.edges.add(edge);
				}
			}
			

			if(!block.walls.get("bottom")) {
				x= block.x;
				y= block.y+1;
				//System.out.println("Dealing with bottom");
				if((x >= 0 && y >= 0 && x < block.maze.columns && y < block.maze.rows)) {
				neighbour = graph.getVertex(block.x, block.y+1);
				edge = new Edge(vertex, neighbour);
				
				graph.edges.add(edge);
				}
			}
			
			if(!block.walls.get("left")) {
				x= block.x-1;
				y= block.y;
				//System.out.println("Dealing with left");
				if((x >= 0 && y >= 0 && x < block.maze.columns && y < block.maze.rows)) {
				neighbour = graph.getVertex(block.x-1, block.y);
				edge = new Edge(vertex, neighbour);
				
				graph.edges.add(edge);
				}
			}
		}
		
		return graph;
		
	}
	
}