
public class Solver {
	
	// F = front B = back L = left R = right U = up D = down
	// p = plus m = minus
	private static final int Fp = 0, Bp = 1, Lp = 2, Rp = 3, Up = 4, Dp = 5, Fm = 6, Bm = 7, Lm = 8, Rm = 9, Um = 10, Dm = 11;
	
	private static final char b = 'b', w = 'w', y = 'y', r = 'r', o = 'o', g = 'g';
	
	Cube cube = null;
	
	public Solver(Cube cube){
		this.cube = cube;
	}
	
	public Solver(){
		System.out.println("Give me a cube!");
	}
	
	public void solve(){
		if (cube == null){
			System.out.println("What's to solve? I have no cube to work with.");
			return;
		}
		
		cube.resetMoves();
		solveTopLayer();
		solveMiddleLayer();
		solveBottomLayer();
	}
	
	
	private void solveTopLayer(){
		solveTopCross();
		solveTopCorners();
	}
	
	private void solveTopCross(){
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
				{}, // bw
				{}, // br
				{}, // by
				{}, // bo
				{}, // yb
				{}, // yr
				{}, // yg
				{}, // yo
				{}, // ob
				{}, // oy
				{}, // og
				{}, // ow
				{}, // wb
				{}, // wo
				{}, // wg
				{}, // wr
				{}, // rb
				{}, // rw
				{}, // rg
				{}, // ry
				{}, // gy
				{}, // gr
				{}, // gw
				{}  // go
		};
		
		cube.performMoves(topMidBlue[cube.findSide(b, w)]);
		
		
		
		// do the above 3 more times for the other sides
		
	}
	
	private void solveTopCorners(){
		
	}
	
	
	
	
	private void solveMiddleLayer(){
		
	}
	
	
	
	private void solveBottomLayer(){
		
	}
	
	
}