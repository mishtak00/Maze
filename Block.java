import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Block {
		
		HashMap<String, Boolean> walls;
		ArrayList<Block> unvisited_nieghbours;
		boolean visited, pushed = false;  
		Maze maze;
		int x, y, width;
		
		//for UI
		boolean containsPlayer, passed, isFinish;
		
		//for dijkstra
		boolean hasHint, highlight;

		public Block(int x, int y, int width, Maze maze) {
			this.x = x;
			this.y = y;
			this.maze = maze;
			this.width = width;
			this.walls = new HashMap<String, Boolean>();
			this.unvisited_nieghbours = new ArrayList<Block>();
			addWalls(this.walls);
			highlight = false;
		}
		
		public boolean hasTwoWalls() {
			int wallNumber=0;
			if (walls.get("top")) wallNumber++;
			if (walls.get("bottom")) wallNumber++;
			if (walls.get("left")) wallNumber++;
			if (walls.get("right")) wallNumber++;
			if (wallNumber>=2) return true;
			return false;
		}
		
		public void addAdjacent(int x, int y, String direction) {
			//System.out.println(x + " " + y);
			
			if((x >= 0 && y >= 0 && x < this.maze.columns && y < this.maze.rows)) {

				//System.out.println("checking visited : " + this.maze.blocks[x][y].visited);
				if(!this.maze.blocks[x][y].visited) {
					//System.out.println("adding once" + this.maze.blocks[x][y].visited);
					unvisited_nieghbours.add(this.maze.blocks[x][y]);
				}
				
			}
			
		}
		    
		public void adjacenctVisitCheck() {
			
			this.addAdjacent(this.x, this.y-1, "top");
			this.addAdjacent(this.x+1, this.y, "right");
			this.addAdjacent(this.x, this.y+1, "bottom");
			this.addAdjacent(this.x-1, this.y, "left");

		}
		
		public Block randomNeighbour() {
			
			if(unvisited_nieghbours.size()>0) {
				Random random = new Random();
				int index_random = (int) Math.floor(random.nextInt(unvisited_nieghbours.size()) + 0);
		
				return unvisited_nieghbours.get(index_random);
				
			} return null;
			
		}
		
		public void addWalls(HashMap<String, Boolean> wall) {
			wall.put("top", true);
			wall.put("bottom", true);
			wall.put("left", true);
			wall.put("right", true);
		}
		
		public void draw(Graphics g) {
			int x = this.x*this.width;
			int y = this.y*this.width;
			
			if(this.visited) {
				g.setColor(Color.WHITE);
				g.fillRect(x, y, this.width, this.width);
			}
			
			if (isFinish) { //System.out.println("block ("+x+","+y+") isFinish");
				g.setColor(Color.RED);
				g.fillRect(x, y, width, width);
			}
			
			if (passed) {
				g.setColor(Color.CYAN);
				g.fillRect(x, y, width, width);
			}
			
			if (highlight) {
				g.setColor(Color.GREEN);
				g.fillRect(x, y, width, width);
			}
			
			if (hasHint) {
				try {
					BufferedImage starIcon = ImageIO.read(new File("star_icon.png"));
					if (!isFinish) g.drawImage(starIcon.getScaledInstance(width, width, Image.SCALE_SMOOTH), x, y, null);
				} catch (IOException e) {
					System.out.println("No star_icon.png was found!");
				}
			}
			
			if (containsPlayer) { //System.out.println("block ("+x+","+y+") containsPlayer");
				g.setColor(Color.BLUE);
				g.fillRect(x, y, width, width);
			}
			
			g.setColor(Color.BLACK);
			 
			if(walls.get("top")) {
			g.drawLine(x, y, x + this.width, y);
			}
			
			if(walls.get("right")) {
			g.drawLine(x+ this.width,y, x + this.width, y+ this.width);
			}
			
			if(walls.get("bottom")) {
			g.drawLine(x+ this.width,y+ this.width, x, y+ this.width);
			}
			
			if(walls.get("left")) {
			g.drawLine(x, y + this.width, x, y);
			}
		}

		

		public boolean isPushed() {
			return pushed;
		}

		public void setPushed(boolean pushed) {
			this.pushed = pushed;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}
		
		public boolean isHighlight() {
			return highlight;
		}

		public void setHighlight(boolean highlight) {
			this.highlight = highlight;
		}

}