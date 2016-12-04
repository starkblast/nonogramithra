package nonogramithra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;



// TODO
// make grid squares
// make grid size relative to amount of numbers
// make array that holds coloured squares 
// find positions of squares

public class Main extends JPanel {
	
	public static JFrame frame; 
	public static int frameWidth = 600;
	public static int frameHeight = 400;
	public int gridWidth = 10;
	public int gridHeight = 5;
	public ArrayList<ArrayList> grid = new ArrayList<ArrayList>();
	public boolean scaled = false;
	

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.decode("#EDE6C7"));
		g.fillRect(0, 0, frameWidth, frameHeight);
		
		g.setColor(Color.BLACK);
		int leftOffset = 50;
		int upOffset = 50;
		int rightOffset = 50;
		int downOffset = 50;

		int heightOfRow = (frameHeight-(upOffset+downOffset)) / gridHeight;
		int widthOfRow = (frameWidth-(leftOffset+rightOffset)) / gridWidth;
		for (int i = 0; i <= gridHeight; i++) {
			g.drawLine(leftOffset, leftOffset+i*heightOfRow, frameWidth-rightOffset, leftOffset+i*heightOfRow);
		}
		for (int i = 0; i <= gridWidth; i++) {
			g.drawLine(upOffset+i*widthOfRow, upOffset, upOffset+i*widthOfRow, frameHeight-downOffset);
		}
	}
	
	private void init(Main main) {
		main.repaint();
		if (!scaled) {
			for (int j = 0; j < gridHeight; j++) {
				ArrayList<Integer> row = new ArrayList<Integer>();
				for (int i = 0; i < gridWidth; i++) {
					row.add(0);
				}
				grid.add(row);
			}

			System.out.println(grid);
			scaled = true;
		}
		addMouseListener(new MouseAdapter() { 
	        public void mousePressed(MouseEvent me) { 
	        	int mouseX = me.getX();	        	
	        	int mouseY = me.getY();
	        	
	        	
	        	main.repaint();
	        } 
	    }); 
	}
		

	

    

	
	public static void main(String[] args) {
		frame = new JFrame("nonogramithra");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Main());

		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(frameWidth, frameHeight);

		frame.setVisible(true);
		frame.getContentPane().addMouseListener(null);
		Main main = new Main();
		main.init(main);
	}

}
