
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
		//System.out.println("Done with the cross");
		
		
	}
	
	private void solveTopCorners() throws Exception{
		int[][] topMidBlue = {
				{}, // byo
				{wm,gm,wp,ym,op,yp,om}, // bow good
				{wp,gm,wm,gp,gp,op,ym,om,yp}, // bwr
				{ym,gm,yp,gp,gp,op,ym,om,yp}, // bry
				
				{gp,op,ym,om,yp}, // gyr
				{gp,gp,op,ym,om,yp}, // grw good
				{gm,op,ym,om,yp}, // gwo good
				{op,ym,om,yp}, // goy
		};
		
		char[] c1 = {'y','o','w','r'};
		char[] c2 = {'o','w','r','y'};

		for(int i = 0; i < 4; i++){
			cube.performMoves(topMidBlue[cube.findCorner(b,c1[i],c2[i])]);
			bruteForceOrientation(c1[i],c2[i]);
			cube.turnBlue(true);
		}		
	}
	
	
	
	
	private void bruteForceOrientation(char c1,char c2) {
		int[] moves = {om,gm,op,gp};
		while(!cube.blueCornerOriented(c1, c2)){
			cube.performMoves(moves);
		}
	}
	
	
	private void solveMiddleLayer() throws Exception{
		int[][] turns = {
				{}, // bw
				{}, // br
				{}, // by
				{}, // bo
				
				{}, // yb
				{ym,gp,yp,rm,yp,rp,ym,gp,gp,		om,gp,op,ym,op,yp,om}, // yr//ow
				{gm,								om,gp,op,ym,op,yp,om}, // yg/gw
				{}, // yo
				
				{}, // ob
				{om,gp,op,ym,op,yp,om,gp,			om,gp,op,ym,op,yp,om}, // oy/wg
				{gp,gp,								om,gp,op,ym,op,yp,om}, // og///
				{wm,gp,wp,om,wp,op,wm,gm,			yp,gm,ym,op,ym,om,yp}, // ow/gy
				
				{}, // wb
				{wm,gp,wp,om,wp,op,wm,				om,gp,op,ym,op,yp,om}, // wo/yr
				{gp,								om,gp,op,ym,op,yp,om}, // wg///
				{rm,gp,rp,wm,rp,wp,rm,gp,gp,		yp,gm,ym,op,ym,om,yp}, // wr/gr
				
				{}, // rb
				{rm,gp,rp,wm,rp,wp,rm,gm,			om,gp,op,ym,op,yp,om}, // rw/
				{									om,gp,op,ym,op,yp,om}, // rg/
				{ym,gp,yp,rm,yp,rp,ym,gp,			yp,gm,ym,op,ym,om,yp}, // ry/rw
				
				{gp,gp,								yp,gm,ym,op,ym,om,yp}, // gy
				{gm,								yp,gm,ym,op,ym,om,yp}, // gr
				{									yp,gm,ym,op,ym,om,yp}, // gw
				{gp,								yp,gm,ym,op,ym,om,yp}  // go
		};
		
		char[] c1 = {'y', 'o', 'w', 'r'};
		char[] c2 = {'o', 'w', 'r', 'y'};
		
		for (int i = 0; i < 4; i++){
			cube.performMoves(turns[cube.findSide(c1[i], c2[i])]);
			cube.turnMiddle(true);
		}
	}

/*	private void solveMiddleLayer(){
		solveYellowOrange();
	}
	
	private void solveYellowOrange() {
		
		int[][] yellowOrange = {
				{}, // bw
				{}, // br
				{}, // by
				{}, // bo
				
				{}, // yb
				{ym,gp,yp,rm,yp,rp,ym,gp,gp,		om,gp,op,ym,op,yp,om}, // yr//ow
				{gm,								om,gp,op,ym,op,yp,om}, // yg/gw
				{}, // yo
				
				{}, // ob
				{om,gp,op,ym,op,yp,om,gp,			om,gp,op,ym,op,yp,om}, // oy/wg
				{gp,gp,								om,gp,op,ym,op,yp,om}, // og///
				{wm,gp,wp,om,wp,op,wm,gm,			yp,gm,ym,op,ym,om,yp}, // ow/gy
				
				{}, // wb
				{wm,gp,wp,om,wp,op,wm,				om,gp,op,ym,op,yp,om}, // wo/yr
				{gp,								om,gp,op,ym,op,yp,om}, // wg///
				{rm,gp,rp,wm,rp,wp,rm,gp,gp,		yp,gm,ym,op,ym,om,yp}, // wr/gr
				
				{}, // rb
				{rm,gp,rp,wm,rp,wp,rm,gm,			om,gp,op,ym,op,yp,om}, // rw/
				{									om,gp,op,ym,op,yp,om}, // rg/
				{ym,gp,yp,rm,yp,rp,ym,gp,			yp,gm,ym,op,ym,om,yp}, // ry/rw
				
				{gp,gp,								yp,gm,ym,op,ym,om,yp}, // gy
				{gm,								yp,gm,ym,op,ym,om,yp}, // gr
				{									yp,gm,ym,op,ym,om,yp}, // gw
				{gp,								yp,gm,ym,op,ym,om,yp}  // go
		};
		cube.performMoves(yellowOrange[cube.findSide(y, o)]);
		
	}*/
	
	
	
	private void solveBottomLayer(){
		
	}
	
	
	
	
	
}
