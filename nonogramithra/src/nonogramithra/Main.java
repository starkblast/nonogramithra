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
// pane ruudustiku offsetid paika
// create mode /// solve mode 
// ühekordne export, sinna üks if ümber
// export ja import 
// hey lahenduse kontrollimise jaoks teed lihtsalt mittenähtava array ja võrdled seda konstantselt in-play-arrayga
// vale start
// tra click lag???
// kahekohaliste numbrite jaoks suurem vahe
// numbrite hulk muudab offseti
// hiirele tuleks eraldi klass teha sest siin ta lihtsalt huiab või siis if lause ümber mis ei lase enne uut klikki teha

public class Main extends JPanel {
	
	public static JFrame frame; 
	public static int frameWidth = 700;
	public static int frameHeight = 400;
	public int gridWidth = 15;
	public int gridHeight = 10;
	public ArrayList<String> grid = new ArrayList<String>();
	public ArrayList<ArrayList<Integer>> gridCounts = new ArrayList<ArrayList<Integer>>();
	public boolean scaled = false;

	public int leftOffset = 120;
	public int upOffset = 120;
	public int rightOffset = 90;
	public int downOffset = 90;

	public int previousX;
	public int previousY;
	public int previousStaticX;
	public int previousStaticY;
	
	public int heightOfRow = (frameHeight-(upOffset+downOffset)) / gridHeight;
	public int widthOfRow = (frameWidth-(leftOffset+rightOffset)) / gridWidth;
	public boolean whichSizeUsing;

	public void init(Main main) {
		frame.repaint();
	}
	
	
	public void paint(Graphics g) {
		if (!scaled) {
			for (int i = 0; i < gridWidth*gridHeight; i++) {
				grid.add("0");
			}
			for (int i = 0; i < gridWidth+gridHeight; i++) {
				gridCounts.add(new ArrayList<Integer>(0));
			}
			
		scaled = true;
		}

		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.decode("#EDE6C7"));
		g.fillRect(0, 0, frameWidth, frameHeight);

		g.setColor(Color.BLACK);
		g.drawString("export", frameWidth-rightOffset, frameHeight-downOffset);

		if (heightOfRow < widthOfRow) {
			for (int i = 0; i <= gridHeight; i++) {
				g.drawLine(leftOffset, leftOffset+i*heightOfRow, gridWidth*heightOfRow+leftOffset, leftOffset+i*heightOfRow);
			}
			for (int i = 0; i <= gridWidth; i++) {
				g.drawLine(upOffset+i*heightOfRow, upOffset, upOffset+i*heightOfRow, gridHeight*heightOfRow+upOffset);	
			}
			whichSizeUsing = true;
			
		} else {
			for (int i = 0; i <= gridHeight; i++) {
				g.drawLine(leftOffset, leftOffset+i*widthOfRow, gridWidth*widthOfRow+leftOffset, leftOffset+i*widthOfRow);
			}
			for (int i = 0; i <= gridWidth; i++) {
				g.drawLine(upOffset+i*widthOfRow, upOffset, upOffset+i*widthOfRow, gridHeight*widthOfRow+upOffset);
			}
			whichSizeUsing = false;
		}
		
		
		if (grid.size() > 0) {
			for (int k = 0; k < gridWidth*gridHeight; k++) {
				if (grid.get(k) == "1") {
					g.setColor(Color.BLACK);
					if (whichSizeUsing) {
						int fillX = (k%gridWidth)*heightOfRow+leftOffset;
						int fillY = ((k-k%gridWidth)/gridWidth)*heightOfRow+upOffset;
						g.fillRect(fillX, fillY, heightOfRow, heightOfRow);
					} else {
						int fillX = (k%gridWidth)*widthOfRow+leftOffset;
						int fillY = ((k-k%gridWidth)/gridWidth)*widthOfRow+upOffset;
						g.fillRect(fillX, fillY, widthOfRow, widthOfRow);
					}
				}
			}
		}
				 
		
		int y1 = 0;
		
		while (y1 < gridHeight) {
			int asi = 0;
			ArrayList<Integer> newArray = new ArrayList<Integer>();

			if (y1*gridWidth < grid.size()) {
				for (int x1 = 0; x1 < gridWidth; x1++) {
	
					if (grid.get(y1*gridWidth + x1) == "1") {
						++asi;
						if (x1 == gridWidth-1) {
							newArray.add(asi);
							asi = 0;
						}
						else if (y1*gridWidth + x1 + 1 < grid.size()) {
							if (grid.get(y1*gridWidth + x1 + 1) != "1") {
								newArray.add(asi);
								asi = 0;
							}
						}
					}			
				}
			gridCounts.set(y1, newArray);
			asi = 0;
			}
			y1++;
		}

		int y2 = 0;
		while (y2 < gridWidth) {
			int asi = 0;
			ArrayList<Integer> newArray = new ArrayList<Integer>();
		
			for (int x2 = 0; x2 < gridHeight; x2++) {
				if (x2*gridWidth < grid.size()) {
					if (grid.get(x2*gridWidth + y2) == "1") {
						++asi;
						if (x2 == gridHeight-1) {
							newArray.add(asi);
							asi = 0;
						} else if ((x2+1)*gridWidth + y2 < grid.size()) {
							if (grid.get((x2+1)*gridWidth + y2) != "1") {
								newArray.add(asi);
								asi = 0;
							}
						}
					}
				}
			}
			gridCounts.set(y2+gridHeight, newArray);
			asi = 0;
			y2++;
		}
	
		for (int i = 0; i < gridCounts.size(); i++) {
			for (int j = gridCounts.get(i).size()-1; j > -1; j--) {
				ArrayList<Integer> array = gridCounts.get(i);
				if (i < gridCounts.size()-gridWidth) { 
					if (whichSizeUsing) {
						g2d.drawString(Integer.toString(array.get(array.size()-1-j)), leftOffset-10 - j*13, i*heightOfRow+upOffset+heightOfRow/2+4);
					} else {
						g2d.drawString(Integer.toString(array.get(array.size()-1-j)), leftOffset-10 - j*13, i*widthOfRow+upOffset+widthOfRow/2+4);
					}
				} else {
					if (whichSizeUsing) {
						g2d.drawString(Integer.toString(array.get(array.size()-1-j)), (i-gridHeight)*heightOfRow+leftOffset+heightOfRow/2-4, upOffset-7-j*14);
					} else {
						g2d.drawString(Integer.toString(array.get(array.size()-1-j)), (i-gridHeight)*widthOfRow+leftOffset+widthOfRow/2-4, upOffset-7-j*14);
					}
				}
			}
		}
		
		frame.addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent me) { 
		    	int mouseX = me.getX();	        	
		    	int mouseY = me.getY();
		    	int xPressed, yPressed;

			    if ((mouseX > leftOffset && mouseX < leftOffset + gridWidth*heightOfRow && mouseY > upOffset && mouseY < (gridHeight * heightOfRow) + upOffset && whichSizeUsing == true) || (mouseX > leftOffset && mouseX < leftOffset + gridWidth*widthOfRow && mouseY > upOffset && mouseY < (gridHeight * widthOfRow) + upOffset && whichSizeUsing == false)) {
			    	if (whichSizeUsing) {
			    		xPressed = (int) ((mouseX - leftOffset) / heightOfRow);
			    		yPressed = (int) ((mouseY - upOffset) / heightOfRow);
			    	}
			    	else {
				    	xPressed = (int) ((mouseX - leftOffset) / widthOfRow);
				    	yPressed = (int) ((mouseY - upOffset) / widthOfRow);
			    	}
			    	if (previousStaticX != xPressed || previousStaticY != yPressed) {
			    	if(grid.size() > 0) {
				    	if (grid.get(yPressed*gridWidth + xPressed) == "0") {
				    		grid.set(yPressed*gridWidth + xPressed, "1");
				    	} else {
				    		grid.set(yPressed*gridWidth + xPressed, "0");
				    	}
			    	}
			    	}
			    	frame.repaint();

				    previousStaticX = xPressed;
				    previousStaticY = yPressed;
			    	
		    	} else if (Math.abs(frameWidth-rightOffset-mouseX) < 20 && Math.abs(frameHeight-downOffset-mouseY) < 20) {
		    		exportGrid();
		    	}
		    } });
		frame.addMouseMotionListener(new MouseAdapter() {public void mouseDragged(MouseEvent me) {
			int mouseX = me.getX();
			int mouseY = me.getY();
			if ((mouseX > leftOffset && mouseX < leftOffset + gridWidth*heightOfRow && mouseY > upOffset && mouseY < (gridHeight * heightOfRow) + upOffset && whichSizeUsing == true) || (mouseX > leftOffset && mouseX < leftOffset + gridWidth*widthOfRow && mouseY > upOffset && mouseY < (gridHeight * widthOfRow) + upOffset && whichSizeUsing == false)) {
		    	int xPressed, yPressed;
		    	if (whichSizeUsing) {
		    		xPressed = (int) ((mouseX - leftOffset) / heightOfRow);
		    		yPressed = (int) ((mouseY - upOffset) / heightOfRow);
		    	}
		    	else {
			    	xPressed = (int) ((mouseX - leftOffset) / widthOfRow);
			    	yPressed = (int) ((mouseY - upOffset) / widthOfRow);
		    	}
		    	if (previousX != xPressed || previousY != yPressed) {
			    	if (grid.size() > 0) {
				    	if (grid.get(yPressed*gridWidth + xPressed) == "0") {
				    		grid.set(yPressed*gridWidth + xPressed, "1");
				    	} else {
				    		grid.set(yPressed*gridWidth + xPressed, "0");
				    	}
			    	}
		    	}
		    	if (previousX != xPressed || previousY != yPressed) {
		    		frame.repaint();
		    	}
		    	previousX = xPressed;
		    	previousY = yPressed;
	    	}
		} });
	}
	
	public void exportGrid() {
		String outstring = "0X";
		for(int i = 0; i < gridCounts.size(); ++i) {
			for (int j = 0; j < gridCounts.get(i).size(); ++j) {
				ArrayList<Integer> array = gridCounts.get(i);
				outstring += Integer.toString(array.get(j));
				outstring += "C";
			}
			if (i < gridCounts.size()-gridHeight) {
				outstring += "A";
			} else {
				outstring += "B";
			}
		}
		System.out.println(outstring);
		//System.out.println(Integer.toString(Integer.parseInt(Integer.toString(Integer.decode(outstring)), 12), 20));
	}
	
	public void importGrid(String instring) {
		ArrayList<ArrayList<Integer>> importedGridCounts;
		for (int i = 0; i < instring.length(); i++) {
			// if a siis on järgmine rida
			// kui on b siis on veerg
			// kui on c siis on vahe
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		frame = new JFrame("nonogramithra");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Main());

		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(frameWidth, frameHeight);

		frame.setVisible(true);
		frame.getContentPane().addMouseListener(null);
		Main main = new Main();

		main.init(main);
		
		/*while (true) {
			frame.repaint();
			Thread.sleep(50);
		}*/
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
