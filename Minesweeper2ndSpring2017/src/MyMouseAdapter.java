import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component b = e.getComponent();
			while (!(b instanceof JFrame)) {
				b = b.getParent();
				if (b == null) {
					return;
				}
			}
			JFrame myFrame2 = (JFrame) b;
			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
			Insets myInsets2 = myFrame2.getInsets();
			int x2 = myInsets2.left;
			int y2 = myInsets2.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel2.x = x3;
			myPanel2.y = y3;
			myPanel2.mouseDownGridX = myPanel2.getGridX(x3, y3);
			myPanel2.mouseDownGridY = myPanel2.getGridY(x3, y3);
			myPanel2.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX == 0) || (gridY == 0)) {
							
							//when pressing the gray buttons on top
							
						} else {
							//On the grid other than on the left column and on the top row:
							//Method checks if the button pressed is a mine or not. 
							if  (myPanel.flagged[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == false){
								if (myPanel.setMine[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == true){	
									for (int i=1; i < 10;i++)
										for (int j=1; j < 10;j++){
											myPanel.revealed[i][j] = true;
											if (myPanel.setMine[i][j] == true){
												myPanel.colorArray[i][j] = Color.BLACK;
												myPanel.repaint();
											}
										//Insert method to check this surrounding tile for mines and show 
										//the amount this tile surrounds here. Delete this comment once you're done.
										}
									}
								else{
								}
									int isMine = 0;
									boolean danger = false;
									myPanel.revealed[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = true;
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.GRAY;
									if (myPanel.mouseDownGridX == 1 && myPanel.mouseDownGridY == 1){
											if(myPanel.setMine[x][y] == true){
												isMine++;
												danger = true;
											}
											if(myPanel.setMine[x][y+1] == true){
												isMine++;
												danger = true;
											}
											if(myPanel.setMine[x+1][y] == true){
												isMine++;
												danger = true;
											}
											if(myPanel.setMine[x+1][y+1] == true){
												isMine++;
												danger = true;
											}
											if (isMine > 0){
												
												myPanel.colorArray[x][y] = Color.GRAY;
												isMine = 0;
											}
									}
									else if ((x >= 2 && x <=9) || (y >= 2 && y <= 9)){
												if(myPanel.setMine[x-1][y-1] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x][y-1] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x+1][y-1] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x-1][y] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x+1][y] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x-1][y+1] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x][y+1] == false){
													isMine++;
													danger = true;
												}
												if(myPanel.setMine[x+1][y+1] == false){
													isMine++;
													danger = true;
												}
												if(isMine > 0){
													//myPanel.detect[x][y] = ;
													myPanel.colorArray[x][y] = Color.GRAY;
													isMine=0;
													}
												else{
													myPanel.colorArray[x][y] = Color.GRAY;
													isMine = 0;
												}
										
									
								}
							}
						}
						
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component b = e.getComponent();
			while (!(b instanceof JFrame)) {
				b = b.getParent();
				if (b == null) {
					return;
				}
			}
			JFrame myFrame2 = (JFrame) b;
			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
			Insets myInsets2 = myFrame2.getInsets();
			int x2 = myInsets2.left;
			int y2 = myInsets2.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel2.x = x3;
			myPanel2.y = y3;
			int gridX1 = myPanel2.getGridX(x3, y3);
			int gridY1 = myPanel2.getGridY(x3, y3);
			myPanel2.repaint();if ((myPanel2.mouseDownGridX == -1) || (myPanel2.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX1 == -1) || (gridY1 == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel2.mouseDownGridX != gridX1) || (myPanel2.mouseDownGridY != gridY1)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX1 == 0) || (gridY1 == 0)) {
							
							//when pressing the gray buttons on top
							
						} else {
							//On the grid other than on the left column and on the top row:
							//Method checks if the button pressed is a mine or not. 
							if (myPanel2.revealed[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == false){
								if(myPanel2.flagged[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == false){
									myPanel2.flagged[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = true;
									myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.RED;
								}
								else{
									myPanel2.flagged[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = false;
									myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.WHITE;
								}
							}
						}
						
					}
				}
			}
			myPanel2.repaint();
			break;
			} //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
	}
}

