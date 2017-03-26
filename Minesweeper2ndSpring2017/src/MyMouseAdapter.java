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
			//Do nothing
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
							if(myPanel.mouseDownGridX > 0 && myPanel.mouseDownGridX <= 9){
								for (int i=1; i < 10; i++){
									Color newColor = null;
									
									Boolean match = true;
									while(match == true){
									switch (generator.nextInt(10)) {
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.MAGENTA;
										break;
									case 2:
										newColor = Color.BLACK;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 5:
										newColor = Color.RED;
										break;
									case 6:
										newColor = Color.CYAN;
										break;
									case 7:
										newColor = Color.ORANGE;
										break;
									case 8:
										newColor = Color.GREEN;
										break;
									case 9:
										newColor = Color.PINK;
										break;
									}
									
									if (i>1){
									for (int j=1; j < i; j++){
										if (!newColor.equals(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY+j])){
												match = false;
											}
										else{
											match = true;
											break;
										}
										}
									}
									else
										match = false;
									
									}
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY+i] = newColor;
									
									myPanel.repaint();
								
								}
							}
							//when pressing the grey buttons on the side aligned with the grid
						if(myPanel.mouseDownGridY > 0 && myPanel.mouseDownGridY <=9){
							for (int i=1; i < 10; i++){
								Color newColor = null;
								
								Boolean match = true;
								while(match == true){
								switch (generator.nextInt(10)) {
								case 0:
									newColor = Color.YELLOW;
									break;
								case 1:
									newColor = Color.MAGENTA;
									break;
								case 2:
									newColor = Color.BLACK;
									break;
								case 3:
									newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 4:
									newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 5:
									newColor = Color.RED;
									break;
								case 6:
									newColor = Color.CYAN;
									break;
								case 7:
									newColor = Color.ORANGE;
									break;
								case 8:
									newColor = Color.GREEN;
									break;
								case 9:
									newColor = Color.PINK;
									break;
								}
								
								if (i>1){
								for (int j=1; j < i; j++){
									if (!newColor.equals(myPanel.colorArray[myPanel.mouseDownGridX+j][myPanel.mouseDownGridY])){
											match = false;
										}
									else{
										match = true;
										break;
									}
									}
								}
								else
									match = false;
								
								}
								myPanel.colorArray[myPanel.mouseDownGridX+i][myPanel.mouseDownGridY] = newColor;
								
								myPanel.repaint();
							
							}
						}
						//when pressing the grey button on the top left corner of the grid
						if(myPanel.mouseDownGridX==0 && myPanel.mouseDownGridY==0){
							for (int i=1; i < 10; i++)
							for (int j=i; j <= i; j++){
								Color newColor = null;
								
								Boolean match = true;
								while(match == true){
								switch (generator.nextInt(10)) {
								case 0:
									newColor = Color.YELLOW;
									break;
								case 1:
									newColor = Color.MAGENTA;
									break;
								case 2:
									newColor = Color.BLACK;
									break;
								case 3:
									newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 4:
									newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 5:
									newColor = Color.RED;
									break;
								case 6:
									newColor = Color.CYAN;
									break;
								case 7:
									newColor = Color.ORANGE;
									break;
								case 8:
									newColor = Color.GREEN;
									break;
								case 9:
									newColor = Color.PINK;
									break;
								}
								
								if (i>1 && j>1){
									for(int t=1; t<i; t++){
											for (int k=t; k <= t; k++){
													if (!newColor.equals(myPanel.colorArray[myPanel.mouseDownGridX+t][myPanel.mouseDownGridY+k])){
														match = false;
														}
													else{
														match = true;
														break;
													}
									
											}
											if(match == true){
												break;
											}
									}
								}
								else
									match = false;
								
								}
								myPanel.colorArray[myPanel.mouseDownGridX+i][myPanel.mouseDownGridY+j] = newColor;
								
								myPanel.repaint();
							
							}
						}
						//when pressing the bottom-left gray button
						if(myPanel.mouseDownGridX == 0 && myPanel.mouseDownGridY == 10){
							for (int i=4; i < 7; i++)
							for (int j=4; j < 7; j++){
								Color newColor = null;
								
								Boolean match = true;
								while(match == true){
								switch (generator.nextInt(10)) {
								case 0:
									newColor = Color.YELLOW;
									break;
								case 1:
									newColor = Color.MAGENTA;
									break;
								case 2:
									newColor = Color.BLACK;
									break;
								case 3:
									newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 4:
									newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 5:
									newColor = Color.RED;
									break;
								case 6:
									newColor = Color.CYAN;
									break;
								case 7:
									newColor = Color.ORANGE;
									break;
								case 8:
									newColor = Color.GREEN;
									break;
								case 9:
									newColor = Color.PINK;
									break;
								}
								
								if (i>4){
									for(int k=4; k <= i;k++){
										for (int t=4; t <= j; t++){
											if (!newColor.equals(myPanel.colorArray[i][j])){
												match = false;
											}
											else{
												match = true;
												break;
											}
										}
										if(match == true)
											break;
									}
								}
								else
									match = false;
								
								}
								myPanel.colorArray[i][j] = newColor;
								
								myPanel.repaint();
							
							}
						}
						
						} else {
							//On the grid other than on the left column and on the top row:
							//Put for loops, possible if statements, 
							Color newColor = null;
							Color lastColor = myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY];
							Boolean match = true;
							while(match == true){
							switch (generator.nextInt(5)) {
							case 0:
								newColor = Color.YELLOW;
								break;
							case 1:
								newColor = Color.MAGENTA;
								break;
							case 2:
								newColor = Color.BLACK;
								break;
							case 3:
								newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
								break;
							case 4:
								newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
								break;
							}
							if (!newColor.equals(lastColor)){
							match = false;
							}
							
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							}
							myPanel.repaint();
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

}