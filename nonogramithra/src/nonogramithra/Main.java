package nonogramithra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JPanel {
	
	public static JFrame frame; 
	public static int frameWidth = 600;
	public static int frameHeight = 400;
	

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.decode("#EDE6C7"));
		g.fillRect(0, 0, frameWidth, frameHeight);
        System.out.println("k");
	}
	
	private void init(Main cl8ass) {
		while (true) { 
		cl8ass.repaint();
        System.out.println("l");

		}
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
