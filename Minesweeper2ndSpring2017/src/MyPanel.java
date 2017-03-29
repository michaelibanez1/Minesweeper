import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 10;
	private static final int TOTAL_ROWS = 11;   //Last row has only one cell
	public int columns = TOTAL_COLUMNS;
	public int rows = TOTAL_ROWS;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public int mine = 10; //Number of mines - 1. 
	public int isMineOrNot; //RNG variable to determine if the tile will have a mine or not. CRR.
	public int isMine; //Variable used to trigger a tile if it is not a mine and no mines surround it. CRR
	public int notMine = 0;
	public Boolean[][] setMine = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS]; //Array that contains variable for mines. CRR
	public Boolean[][] flagged = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS]; //Array to toggle a flag (if the player suspects that tile to be a mine). CRR
	public Boolean[][] revealed = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS];
	public Integer[][] detect = new Integer[TOTAL_COLUMNS][TOTAL_ROWS];
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //Top row
			colorArray[x][0] = Color.LIGHT_GRAY;
		}
		for (int y = 0; y < TOTAL_ROWS; y++) {   //Left column
			colorArray[0][y] = Color.LIGHT_GRAY;
		}
		
		//Array that sets the grid and puts a Boolean Variable for mines, flags, 
		//and determining if the tile is revealed or not on each tile. CRR
		for (int x = 1; x < TOTAL_COLUMNS; x++) {   
			for (int y = 1; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
				setMine[x][y] = false;
				flagged[x][y] = false;
				revealed[x][y] = false;
				detect[x][y] = 0;
			}
		}
		/* if(revealed[x][y] == true)
		 * g.setColor(Color.BLACK)
		 * g.setFont("Tahoma", Font.BOLD, 30)
		 * g.drawString(Interger.toString([][], x, y))*/
		
		
		//Sets the mines. CRR
		while(mine > 0){
			for (int x = 1; x < TOTAL_COLUMNS; x++) {   
				for (int y = 1; y < TOTAL_ROWS; y++) {
					if (mine == 0)
						break;
					if(setMine[x][y] == false){
						isMineOrNot = new Random().nextInt(8);
						if (isMineOrNot == 0){
							setMine[x][y] = true;
							mine--;
						}
						else {
							setMine[x][y] = false;
							notMine++;
						}
					}
					
				}
				if (mine == 0)
					break;
			}
		}
		//Checks for mines to determine if it is surrounded or not. 
		//Only covers anything not on the first row, first column, last row, and last column. CRR
		for (int x = 2; x < TOTAL_COLUMNS - 1; x++) {   //The rest of the grid
			for (int y = 2; y < TOTAL_ROWS - 1; y++) {
					if(setMine[x-1][y-1] == true)
						isMine++;
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y+1] == true)
						isMine++;
					if(setMine[x][y-1] == true)
						isMine++;
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if(setMine[x+1][y-1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y+1] == true)
						isMine++;
					if(isMine > 0){
						detect[x][y] = isMine;
						isMine=0;
					}
			}
		}
		//Checks the first row. CRR
		for (int y = 1; y <=1; y++){
			for (int x=1; x < TOTAL_COLUMNS; x++){
				if(x == 1){
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y+1] == true)
						isMine++;
					if (isMine > 0){
						detect[x][y] = isMine;
						isMine = 0;
					}
				}
				else if (x == TOTAL_COLUMNS - 1){
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y+1] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if (isMine > 0){
						detect[x][y] = isMine;
						isMine = 0;
					}
				}
				else {
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y+1] == true)
						isMine++;
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y+1] == true)
						isMine++;
					if(isMine > 0){
						detect[x][y] = isMine;
						isMine=0;
						}
				}
			}
		}
		//Checks the first column. CRR
		for (int x = 1; x <=1; x++){
			for (int y=2; y < TOTAL_ROWS-1; y++){
				if (y < TOTAL_ROWS-1){
					if(setMine[x][y-1] == true)
						isMine++;
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if(setMine[x+1][y-1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y+1] == true)
						isMine++;
					if(isMine > 0){
						detect[x][y] = isMine;
						isMine=0;
					}
				}					
			}	
		}
		//Checks the last row. CRR
		for (int y = 9; y <=9; y++){
			for (int x=1; x < TOTAL_COLUMNS; x++){
				if(x == 1){
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y-1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y-1] == true)
						isMine++;
					if (isMine > 0){
						detect[x][y] = isMine;
						isMine = 0;
					}
				}
				else if (x == TOTAL_COLUMNS - 1){
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y-1] == true)
						isMine++;
					if(setMine[x][y-1] == true)
						isMine++;
					if (isMine > 0){
						detect[x][y] = isMine;
						isMine = 0;
					}
				}
				else {
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y-1] == true)
						isMine++;
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y-1] == true)
						isMine++;
					if(setMine[x+1][y] == true)
						isMine++;
					if(setMine[x+1][y-1] == true)
						isMine++;
					if(isMine > 0){
						detect[x][y] = isMine;
						isMine = 0;
						}
				}
			}
		}
		//Checks the last column. CRR
		for (int x = 9; x <=9; x++){
			for (int y=2; y < TOTAL_ROWS-1; y++){
				if (y < TOTAL_ROWS-1){
					if(setMine[x][y-1] == true)
						isMine++;
					if(setMine[x][y] == true)
						isMine++;
					if(setMine[x][y+1] == true)
						isMine++;
					if(setMine[x-1][y-1] == true)
						isMine++;
					if(setMine[x-1][y] == true)
						isMine++;
					if(setMine[x-1][y+1] == true)
						isMine++;
					if(isMine > 0){
						detect[x][y] = isMine;
						isMine=0;
					}
				}					
			}	
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		//Draw an additional cell at the bottom left
		g.drawRect(x1 + GRID_X, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)), INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS - 1)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}
		
	}
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	
	
}
