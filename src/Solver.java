
public class Solver {
	
	// F = front B = back L = left R = right U = up D = down
	// p = plus m = minus
	private static final int yp = 0, wp = 1, rp = 2, op = 3, bp = 4, gp = 5, 
							 ym = 6, wm = 7, rm = 8, om = 9, bm = 10, gm = 11;
	
	private static final char b = 'b', w = 'w', y = 'y', r = 'r', o = 'o', g = 'g';
	
	Cube cube = null;
	
	public Solver(Cube cube){
		this.cube = cube;
	}
	
	public Solver(){
		System.out.println("Give me a cube!");
	}
	
	public void solve() throws Exception{
		if (cube == null){
			System.out.println("What's to solve? I have no cube to work with.");
			return;
		}
		
		cube.resetMoves();
		solveTopLayer();
		solveMiddleLayer();
		solveBottomLayer();
	}
	
	
	private void solveTopLayer() throws Exception{
		solveTopCross();
		solveTopCorners();
	}
	
	private void solveTopCross() throws Exception{
		/*
		 * Start here!
		 * 
		 * using the numbers assigned to side cube positions in Cube.findSide
		 * fill in the 2d array
		 * 
		 * if a side position is impossible, or already in the correct position, leave the move list empty
		 * 
		 * Use turn/move constants defined at the top of this file
		 * 
		 * 
		 * bw means that the first color of the side piece was found on the blue face
		 * and the second color of the side piece was found on the white face
		 * so order does matter when you do cube.findSide(color1, color2)
		 */
		
		int[][] topMidBlue = {
				{wp,wp,gp,gp,yp,yp}, // bw
				{rp,rp,gp,yp,yp}, // br
				{}, // by
				{op,op,gm,yp,yp}, // bo
				
				{yp,yp,gp,op,ym,om}, // yb
				{ym,gp,op,ym,om}, // yr
				{gp,op,ym,om}, // yg
				{yp,gp,op,ym,om}, // yo
				
				{om,ym}, // ob
				{ym}, // oy
				{op,ym,om}, // og
				{op,op,ym,op,op}, // ow
				
				{wp,wp,gm,op,ym,om}, // wb
				{wm,gm,wp,op,ym,om}, // wo
				{gm,op,ym,om}, // wg
				{wp,gm,wm,op,ym,om}, // wr
				
				{rp,yp}, // rb
				{wp,gp,gp,wm,yp,yp}, // rw
				{rm,yp,rp}, // rg
				{yp}, // ry
				
				{yp,yp}, // gy
				{gp,yp,yp}, // gr
				{gp,gp,yp,yp}, // gw
				{gm,yp,yp}  // go
		};
		
		cube.performMoves(topMidBlue[cube.findSide(b, y)]);
		cube.turnBlue(true);
		cube.performMoves(topMidBlue[cube.findSide(b, o)]);
		cube.turnBlue(true);
		cube.performMoves(topMidBlue[cube.findSide(b, w)]);
		cube.turnBlue(true);
		cube.performMoves(topMidBlue[cube.findSide(b, r)]);
		cube.turnBlue(true);
		
		
		
		// do the above 3 more times for the other sides
		
	}
	
	private void solveTopCorners(){
		
	}
	
	
	
	
	private void solveMiddleLayer(){
		
	}
	
	
	
	private void solveBottomLayer(){
		
	}
	
	
}
