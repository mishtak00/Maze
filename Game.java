import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public Maze maze;
	public PaintCanvas canvas;
	public Labels labels;
	public Timer timer = new Timer(17, new AnimationHandler()); //60 fps
	
	//levels
	public long startTime;
	public int level, levelTime;
	public int screenSize = 400; //maybe change this with levels?
	
	//dijkstra
	public Navigator navigator;
	public Graph graph;
	
	
	//start game class which starts timer, makes Frame and canvas visible 
		public void startGame() {
			
			setVisible(true);
			timer.start();
			canvas.setVisible(true);
		}
		
		public Game(int columns, int rows) {
			//System.out.print("New Game");
			int width = screenSize/columns;
			this.maze = new Maze(columns, columns, width);
			this.maze.MazeGenerator(this.maze.blocks[0][0]);
			level = 1; startTime = System.currentTimeMillis();
			navigator = new Navigator();
			graph = maze.toGraph();
		}
		
		//main class that kickstarts the game
		public static void main(String[]args) {
			System.out.println("Level 1!");
			Game game = new Game(10, 10);
			//System.out.print("Starting");
			game.initializeGraphics();
			game.startGame();
		}
		
		//AnimationHandler that updates game everytimer timer is triggered by implementing ActionListener 
		public class AnimationHandler implements ActionListener {
			public void actionPerformed(ActionEvent t) {
				levelTime = getLevelTime();
				labels.setTimeLabel(levelTime);
				
				if (hitStar()) {
					showNextHint(graph, maze.blocks[maze.starX][maze.starY], maze.blocks[maze.finishX][maze.finishY]);
				}
				
				if (levelWon()) {
					nextLevel(++level);
				}
				repaint();
			}
		}
		
		public boolean hitStar() {
			if (maze.blocks[maze.starX][maze.starY].containsPlayer) return true;
			return false;
		}
		
		public int getLevelTime() {
			long currentTime = System.currentTimeMillis();
			return (int) ((currentTime - startTime) / 1000);
		}
		
		public static int getLevelColumns(int l) {
			switch(l) {
			case 2:
				return 12;
			case 3:
				return 15;
			case 4:
				return 20;
			case 5:
				return 30;
			}
			return 25+l; //change this to gameWon() maybe?
		}
		
		public void nextLevel(int l) { System.out.println("Level "+l+"!");
			
			int columns = getLevelColumns(l); int width=screenSize/columns;
			this.maze = new Maze(columns, columns, width);
			this.maze.MazeGenerator(this.maze.blocks[0][0]);
			this.labels.setLevelLabel(l);
			this.startTime = System.currentTimeMillis();
			navigator = new Navigator();
			graph = maze.toGraph();
		}
		
		public boolean levelWon() {
			if (maze.blocks[maze.finishX][maze.finishY].containsPlayer) return true;
			return false;
		}
		
	
	public class PaintCanvas extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {		
			maze.create(g);		
		}
	}
	
	public class Labels extends JPanel {
		private static final long serialVersionUID = 1L;
		
		JLabel levelLabel, levelTimeLabel;
		
		public Labels() {
			setLayout(new BorderLayout());
			
			levelLabel = new JLabel("Level: "+level);
			levelTimeLabel = new JLabel("Time on current level: "+levelTime);
			
			add(levelLabel, BorderLayout.WEST);
			add(levelTimeLabel, BorderLayout.EAST);
		}
		
		public void setLevelLabel(int l) {
			levelLabel.setText("Level: "+l);
		}
		
		public void setTimeLabel(int t) {
			levelTimeLabel.setText("Time on current level: "+levelTime);
		}
	}
	
	//class that creates the GUI components for the game and adds keylistener to the JFrame
	public void initializeGraphics() {
		
		//System.out.print("Initializing");
		setTitle("Maze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		//this.setPreferredSize(new Dimension(400,430));
		
		canvas = new PaintCanvas();
		canvas.setPreferredSize(new Dimension(400,400));
		add(canvas, BorderLayout.CENTER);
		
		labels = new Labels();
		labels.setPreferredSize(new Dimension(400, 20));
		add(labels, BorderLayout.NORTH);
		
		addKeyListener(this);
		setResizable(false);
		setFocusable(true);
		pack();
	}
	
	//this is the player-moving method
	public void updatePlayer(String dir) {
		Block currentBlock = this.maze.blocks[maze.playerX][maze.playerY];
		
		switch(dir) {
		case "down": //if there's no wall below then playerY++
			if (!currentBlock.walls.get("bottom")) {
				this.maze.blocks[maze.playerX][++maze.playerY].containsPlayer = true;
				currentBlock.containsPlayer = false;
				currentBlock.passed = true;
			} break;
		case "up":
			if (!currentBlock.walls.get("top")) {
				this.maze.blocks[maze.playerX][--maze.playerY].containsPlayer = true;
				currentBlock.containsPlayer = false;
				currentBlock.passed = true;
			} break;
		case "right":
			if (!currentBlock.walls.get("right")) {
				this.maze.blocks[++maze.playerX][maze.playerY].containsPlayer = true;
				currentBlock.containsPlayer = false;
				currentBlock.passed = true;
			} break;
		case "left":
			if (!currentBlock.walls.get("left")) {
				this.maze.blocks[--maze.playerX][maze.playerY].containsPlayer = true;
				currentBlock.containsPlayer = false;
				currentBlock.passed = true;
			}
		}
	}
	

		public void keyPressed(KeyEvent e) {
			//System.out.println("key triggered");
			
			switch (e.getKeyCode()) {
			
			//for movement of the tiles
			case KeyEvent.VK_DOWN:
				//System.out.println("DOWN");
				updatePlayer("down");
				break;
				
			case KeyEvent.VK_UP:
				//System.out.println("UP");
				//showHintAll(graph, maze.blocks[maze.playerX][maze.playerY], maze.blocks[maze.finishX][maze.finishY]);
				updatePlayer("up");				
				break;
				
			case KeyEvent.VK_LEFT:
				//System.out.println("LEFT");
				updatePlayer("left");
				break;
				
			case KeyEvent.VK_RIGHT:
				//System.out.println("RIGHT");
				updatePlayer("right");
				break;
				
				}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
		
		public void showNextHint(Graph graph, Block start, Block end) {
			MazeHelper(graph, start, end);
			
			int randomIndex = (int)(Math.random()*navigator.path.size()-1);
			Block block = navigator.path.get(randomIndex).block;
			maze.blocks[maze.starX][maze.starY].hasHint = false;
			maze.starX = block.x; maze.starY = block.y;
			maze.blocks[maze.starX][maze.starY].hasHint = true;
		}
		
		public void showHintAll(Graph graph, Block start, Block end) {
			MazeHelper(graph, start, end);
			
			for (Vertex v : navigator.path) {
				v.block.setHighlight(true);
			}
		}
		
		public void showHint(Graph graph, Block start, Block end) {
			Vertex result = MazeHelper(graph, start, end).get(0);
			result.block.setHighlight(true);
		}
		
		public LinkedList<Vertex> MazeHelper(Graph graph, Block start, Block end) {
			Vertex a = graph.getVertex(start.x, start.y);
			Vertex b = graph.getVertex(end.x, end.y);
			
			navigator.Navigate(graph, a, b);
			
			return navigator.path;
		}
	
}