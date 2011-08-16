import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;


public class Cube {
	
	private static String[] turns = {"yel+", "whi+", "red+", "ora+", "blu+", "gre+", "MID+", 
									 "yel-", "whi-", "red-", "ora-", "blu-", "gre-", "MID-"};
	
	private LinkedList<Integer> moves = new LinkedList<Integer>();
	
	public static final int y = 1, w = 2, b = 4, g = 8, r = 16, o = 32;
	
	private char[][] yellow = new char[3][3]; // yellow is front
	private char[][] white = new char[3][3]; // white is back
	private char[][] blue = new char[3][3]; // blue is top
	private char[][] green = new char[3][3]; // green is bottom
	private char[][] red = new char[3][3]; // red is left
	private char[][] orange = new char[3][3]; // orange is right
	
	private char[][][] faceArray = {yellow, white, blue, green, red, orange};

	
	private int turnCount = 0;
	
	private Logger outputLog = new Logger();
	private Logger turnLog = new Logger();
	private Logger cubeLog = new Logger();
	private Logger randomizeLog = new Logger();
	private Logger randomizeCubeLog = new Logger();
	private Logger errLog = new Logger();
	
	
	public Cube(){
		outputLog.enable();
	}
	
	private int getFaceInt(String f){
		if(f.equals("yellow"))
			return 0;
		else if (f.equals("white"))
			return 1;
		else if (f.equals("blue"))
			return 2;
		else if (f.equals("green"))
			return 3;
		else if (f.equals("red"))
			return 4;
		else if (f.equals("orange"))
			return 5;
		else
			new Exception("error determining face: " + f);
		
		return 10; 
	}
	
	void turnYellow(boolean clockWise) throws Exception{
		int colorNum = 0;
		
		char[] bluec = deepCopy(blue, "bottom");
		char[] orangec = deepCopy(orange, "left");
		char[] greenc = deepCopy(green, "top");
		char[] redc = deepCopy(red, "right");

		if (clockWise) {
			turnFaceClock(yellow);
			replaceColorR(redc, blue, "bottom");
			replaceColor(bluec, orange, "left");
			replaceColorR(orangec, green, "top");
			replaceColor(greenc, red, "right");

			turnLog.println("Turn " + ++turnCount + "\nturning yellow clockwise");

		} else {
			turnFaceCounterClock(yellow);
			replaceColor(orangec, blue, "bottom");
			replaceColorR(greenc, orange, "left");
			replaceColor(redc, green, "top");
			replaceColorR(bluec, red, "right");
			turnLog.println("Turn " + ++turnCount+ "\nturning yellow counter clockwise \n");

		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}

	void turnBlue(boolean clockWise) throws Exception {

		int colorNum = 4;
		
		char[] whitec = deepCopy(white, "top");
		char[] orangec = deepCopy(orange, "top");
		char[] yellowc = deepCopy(yellow, "top");
		char[] redc = deepCopy(red, "top");

		if (clockWise) {
			turnFaceClock(blue);
			replaceColor(redc, white, "top");
			replaceColor(whitec, orange, "top");
			replaceColor(orangec, yellow, "top");
			replaceColor(yellowc, red, "top");

			turnLog.println("Turn " + ++turnCount + "\nturning blue clockwise");

		} else {
			turnFaceCounterClock(blue);
			replaceColor(orangec, white, "top");
			replaceColor(yellowc, orange, "top");
			replaceColor(redc, yellow, "top");
			replaceColor(whitec, red, "top");

			turnLog.println("Turn " + ++turnCount+ "\nturning blue counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}

	void turnOrange(boolean clockWise) throws Exception {

		int colorNum = 3;
		
		char[] bluec = deepCopy(blue, "right");
		char[] whitec = deepCopy(white, "left");
		char[] yellowc = deepCopy(yellow, "right");
		char[] greenc = deepCopy(green, "right");


		if (clockWise) {
			turnFaceClock(orange);
			replaceColor(yellowc, blue, "right");
			replaceColorR(bluec, white, "left");
			replaceColorR(whitec, green, "right");
			replaceColor(greenc, yellow, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning orange clockwise \n");

		} else {
			turnFaceCounterClock(orange);
			replaceColorR(whitec, blue, "right");
			replaceColorR(greenc, white, "left");
			replaceColor(yellowc, green, "right");
			replaceColor(bluec, yellow, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning orange counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));
	}
	
	void turnWhite(boolean clockWise) throws Exception {

		int colorNum = 1;
		
		char[] orangec = deepCopy(orange, "right");
		char[] bluec = deepCopy(blue, "top");
		char[] redc = deepCopy(red, "left");
		char[] greenc = deepCopy(green, "bottom");

		if (clockWise) {
			turnFaceClock(white);
			replaceColor(orangec, blue, "top");
			replaceColorR(bluec, red, "left");
			replaceColor(redc, green, "bottom");
			replaceColorR(greenc, orange, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning white clockwise \n");

		} else {
			turnFaceCounterClock(white);
			replaceColorR(redc, blue, "top");
			replaceColor(greenc, red, "left");
			replaceColorR(orangec, green, "bottom");
			replaceColor(bluec, orange, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning white counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}

	void turnGreen(boolean clockWise) throws Exception {

		int colorNum = 5;
		
		char[] yellowc = deepCopy(yellow, "bottom");
		char[] orangec = deepCopy(orange, "bottom");
		char[] whitec = deepCopy(white, "bottom");
		char[] redc = deepCopy(red, "bottom");

		if (clockWise) {
			turnFaceClock(green);
			replaceColor(redc, yellow, "bottom");
			replaceColor(yellowc, orange, "bottom");
			replaceColor(orangec, white, "bottom");
			replaceColor(whitec, red, "bottom");

			turnLog.println("Turn " + ++turnCount+ "\nturning green clockwise \n");

		} else {
			turnFaceCounterClock(green);
			replaceColor(orangec, yellow, "bottom");
			replaceColor(whitec, orange, "bottom");
			replaceColor(redc, white, "bottom");
			replaceColor(yellowc, red, "bottom");

			turnLog.println("Turn " + ++turnCount+ "\nturning green counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}

	void turnRed(boolean clockWise) throws Exception {

		int colorNum = 2;
		
		char[] bluec = deepCopy(blue, "left");
		char[] yellowc = deepCopy(yellow, "left");
		char[] greenc = deepCopy(green, "left");
		char[] whitec = deepCopy(white, "right");

		if (clockWise) {
			turnFaceClock(red);
			replaceColorR(whitec, blue, "left");
			replaceColor(bluec, yellow, "left");
			replaceColor(yellowc, green, "left");
			replaceColorR(greenc, white, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning red clockwise \n");

		} else {
			turnFaceCounterClock(red);
			replaceColor(yellowc, blue, "left");
			replaceColorR(bluec, white, "right");
			replaceColor(greenc, yellow, "left");
			replaceColorR(whitec, green, "left");

			turnLog.println("Turn " + ++turnCount+ "\nturning red counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}
	
	void turnMiddle(boolean clockWise) throws Exception {

		int colorNum = 6;
		
		char[] whitec = deepCopy(white, "midrow");
		char[] orangec = deepCopy(orange, "midrow");
		char[] yellowc = deepCopy(yellow, "midrow");
		char[] redc = deepCopy(red, "midrow");

		if (clockWise) {
			replaceColor(redc, white, "midrow");
			replaceColor(whitec, orange, "midrow");
			replaceColor(orangec, yellow, "midrow");
			replaceColor(yellowc, red, "midrow");

			turnLog.println("Turn " + ++turnCount + "\nturning middle clockwise");

		} else {
			replaceColor(orangec, white, "midrow");
			replaceColor(yellowc, orange, "midrow");
			replaceColor(redc, yellow, "midrow");
			replaceColor(whitec, red, "midrow");

			turnLog.println("Turn " + ++turnCount+ "\nturning middle counter clockwise \n");
		}
		cubeLog.println(cubeToString());
		moves.add(colorNum + (clockWise ? 0 : 7));

	}
	
	
	private void turnFaceClock(char[][] face) {

		char[][] tempFace = deepCopyFace(face);

		face[0][0] = tempFace[2][0];
		face[0][1] = tempFace[1][0];
		face[0][2] = tempFace[0][0];

		face[1][0] = tempFace[2][1];
		face[1][2] = tempFace[0][1];

		face[2][0] = tempFace[2][2];
		face[2][1] = tempFace[1][2];
		face[2][2] = tempFace[0][2];
	}

	private void turnFaceCounterClock(char[][] face) {

		char[][] tempFace = deepCopyFace(face);

		face[0][0] = tempFace[0][2];
		face[0][1] = tempFace[1][2];
		face[0][2] = tempFace[2][2];

		face[1][0] = tempFace[0][1];
		face[1][2] = tempFace[2][1];

		face[2][0] = tempFace[0][0];
		face[2][1] = tempFace[1][0];
		face[2][2] = tempFace[2][0];
	}
	
	private void replaceColor(char[] source, char[][] target, String side) throws Exception {

		for (int i = 0; i < 3; i++) {
			if (side.equals("top"))
				target[0][i] = source[i];
			else if (side.equals("bottom"))
				target[2][i] = source[i];
			else if (side.equals("left"))
				target[i][0] = source[i];
			else if (side.equals("right"))
				target[i][2] = source[i];
			else if (side.equals("midrow"))
				target[1][i] = source[i];
			else
				throw new Exception("error determining side (replaceColor)");
			
		}
	}
	
	private void replaceColorR(char[] source, char[][] target, String side) throws Exception {
		for (int i = 0; i < 3; i++) {
			if (side.equals("top"))
				target[0][2 - i] = source[i];
			else if (side.equals("bottom"))
				target[2][2 - i] = source[i];
			else if (side.equals("left"))
				target[2 - i][0] = source[i];
			else if (side.equals("right"))
				target[2 - i][2] = source[i];
			else if (side.equals("midrow"))
				target[1][2-i] = source[i];
			else
				throw new Exception("error determining side (replaceColor)");
			
		}

	}
	
	private static char[] deepCopy(char[][] face, String side) throws Exception {

		char[] result = new char[3];

		for (int i = 0; i < 3; i++) {
			if (side.equals("top"))
				result[i] = face[0][i];
			else if (side.equals("bottom"))
				result[i] = face[2][i];
			else if (side.equals("left"))
				result[i] = face[i][0];
			else if (side.equals("right"))
				result[i] = face[i][2];
			else if (side.equals("midrow"))
				result[i] = face[1][i];
			else
				throw new Exception("error determining side (deepcopy)");
			
		}

		return result;
	}
	
	private char[][] deepCopyFace(char[][] face) {

		char[][] copy = new char[3][3];
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				copy[r][c] = face[r][c];
			}
		}
		return copy;
	}
	
	
	
	// build each face.
	public void buildFace(String faceName, String colors) throws Exception {

		String faceN = faceName.toLowerCase();
//		int faceInt = Integer.parseInt(faceName);
//		System.out.println(faceName);
		//	private char[][][] faceArray = {yellow, white, blue, green, red, orange};

		//int f = face(faceName);

		char[][] face = faceArray[getFaceInt(faceN)];
		
		System.out.println(face);
		
		int ch = 0;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				face[r][c] = colors.charAt(ch);
				ch++;
			}
		}

	}
	
	
	
	private String faceToString(char[][] face) {
		StringBuffer result = new StringBuffer();
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				result.append(face[r][c] + " ");
			}
			result.append("\n");

		}
		return result.toString();
	}

	private String faceToString(char[][] face, boolean upsideDown) {
		if (upsideDown = false)
			return faceToString(face);
		char[][] newFace = deepCopyFace(face);
		char temp = newFace[1][0];
		newFace[1][0] = newFace[1][2];
		newFace[1][2] = temp;

		for (int i = 0; i < 3; i++) {
			temp = newFace[0][i];
			newFace[0][i] = newFace[2][2 - i];
			newFace[2][2 - i] = temp;
		}

		return faceToString(newFace);

	}
	
	private String cubeToString() {
		StringBuffer result = new StringBuffer();

		String blank = "      \n      \n      \n";
		result.append(interleave(blank, faceToString(blue)));
		result.append(interleave(interleave(faceToString(red), faceToString(yellow)), faceToString(orange)));
		result.append(interleave(blank, faceToString(green)));
		result.append(interleave(blank, faceToString(white, true)));
		return result.toString();

	}
	
	
	private static String interleave(String x, String y) {
		String[] xtokens = x.split("\n");
		String[] ytokens = y.split("\n");

		int max = xtokens.length > ytokens.length ? xtokens.length
				: ytokens.length;

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < max; i++) {
			result.append(xtokens[i] + ytokens[i] + "\n");
		}

		return result.toString();
	}
	
	void checkIntegrity() throws Exception {
		boolean valid = validFace(blue) && validFace(yellow)
				&& validFace(green) && validFace(white) && validFace(red)
				&& validFace(orange);
		if (!valid) {
			throw new Exception("Invalid face found.");
		}
	}
	
	private static boolean validFace(char[][] face) {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (!goodColor(face[r][c]))
					return false;
			}
		}

		return true;
	}
	
	private static boolean goodColor(char c) {
		return c == 'r' | c == 'g' | c == 'b' | c == 'o' | c == 'y' | c == 'w';
	}
	
	void scramble(){
		try {
			Random r = new Random();
			
			Logger t1 = turnLog;
			Logger t2 = cubeLog;
			turnLog = randomizeLog;
			cubeLog = randomizeCubeLog;
			
			
			
			int turns = 20 + r.nextInt(21);
			while (turns-- > 0){
				int face = r.nextInt(6);
				boolean dir = r.nextInt() % 2 == 0;
				switch(face){
					case 0:
						turnYellow(dir);
						break;
					case 1:
						turnBlue(dir);
						break;
					case 2:
						turnOrange(dir);
						break;
					case 3:
						turnWhite(dir);
						break;
					case 4:
						turnGreen(dir);
						break;
					case 5:
						turnRed(dir);
						break;
				}
				
			}
			
			turnLog = t1;
			cubeLog = t2;
			
		} catch (Exception e){
			errLog.println(e.toString());
			System.exit(-1);
		}
		
		
	}
	
	public void addOutputStream(PrintStream p){
		turnLog.add(p);
		cubeLog.add(p);
		randomizeLog.add(p);
		randomizeCubeLog.add(p);
		outputLog.add(p);
	}
	
	public void addOutputStream(PrintWriter p){
		turnLog.add(p);
		cubeLog.add(p);
		randomizeLog.add(p);
		randomizeCubeLog.add(p);
		outputLog.add(p);
	}
	
	public void addErrStream(PrintStream p){
		errLog.add(p);
	}
	
	public void addErrStream(PrintWriter p){
		errLog.add(p);
	}
	
	public void enableTurnLog(){
		turnLog.enable();
	}
	
	public void disableTurnLog(){
		turnLog.disable();
	}
	
	public void enableCubeLog(){
		cubeLog.enable();
	}
	
	public void disableCubeLog(){
		cubeLog.disable();
	}
	
	public void enableRandomizeLog(){
		randomizeLog.enable();
	}
	
	public void disableRandomizeLog(){
		randomizeLog.disable();
	}
	
	public void enableCubeRandomizeLog(){
		randomizeCubeLog.enable();
	}
	
	public void disableCubeRandomizeLog(){
		randomizeCubeLog.disable();
	}
	
	public void enableErrLog(){
		errLog.enable();
	}
	
	public void disableErrLog(){
		errLog.disable();
	}
	
	public void printMoves(int nl, int space){
		Iterator<Integer> it = moves.iterator();
		pmh2(it, nl, space, 0, 0);
		outputLog.println();
	}
	
	private void pmh2(Iterator<Integer> it, int nl, int space, int newlinec, int spacec){
		if (it.hasNext()){
			outputLog.print(turns[it.next()] + " ");
			newlinec++;
			spacec++;
			if (spacec == space){
				outputLog.print("\t");
				spacec = 0;
			}
			if (newlinec == nl){
				outputLog.println();
				newlinec = 0;
			}
			pmh2(it, nl, space, newlinec, spacec);
		}
	}
	
	public void printMoves(){
		Iterator<Integer> it = moves.iterator();
		pmh(it);
		outputLog.println();
	}
	
	private void pmh(Iterator<Integer> it){
		if (it.hasNext()){
			outputLog.print(turns[it.next()] + " ");
			pmh(it);
		}
	}
	
	public void printMovesBackwards(){
		Iterator<Integer> it = moves.iterator();
		pmbh(it);
		outputLog.println();
	}
	
	private void pmbh(Iterator<Integer> it){
		if (it.hasNext()){
			Integer i = it.next();
			pmbh(it);
			outputLog.print(turns[(i + 6)%12] + " ");
		}
	}
	
	public void printCube(){
		outputLog.print(cubeToString());
	}
	
	public void resetMoves(){
		turnCount = 0;
		moves = new LinkedList<Integer>();
	}
	
	public int topMid(char[][] face){
		return face[0][1];
	}
	
	public int leftMid(char[][] face){
		return face[1][0];
	}
	
	public int rightMid(char[][] face){
		return face[1][2];
	}
	
	public int bottomMid(char[][] face){
		return face[2][1];
	}
	
	public int findSide(char c1, char c2){
		if (topMid(blue) == c1 && topMid(white) == c2)
			return 0;
		else if (leftMid(blue) == c1 && topMid(red) == c2)
			return 1;
		else if (bottomMid(blue) == c1 && topMid(yellow) == c2)
			return 2;
		else if (rightMid(blue) == c1 && topMid(orange) == c2)
			return 3;
		
		else if (topMid(yellow) == c1 && bottomMid(blue) == c2)
			return 4;
		else if (leftMid(yellow) == c1 && rightMid(red) == c2)
			return 5;
		else if (bottomMid(yellow) == c1 && topMid(green) == c2)
			return 6;
		else if (rightMid(yellow) == c1 && leftMid(orange) == c2)
			return 7;
		
		else if (topMid(orange) == c1 && rightMid(blue) == c2)
			return 8;
		else if (leftMid(orange) == c1 && rightMid(yellow) == c2)
			return 9;
		else if (bottomMid(orange) == c1 && rightMid(green) == c2)
			return 10;
		else if (rightMid(orange) == c1 && leftMid(white) == c2)
			return 11;
		
		else if (topMid(white) == c1 && topMid(blue) == c2)
			return 12;
		else if (leftMid(white) == c1 && rightMid(orange) == c2)
			return 13;
		else if (bottomMid(white) == c1 && bottomMid(green) == c2)
			return 14;
		else if (rightMid(white) == c1 && leftMid(red) == c2)
			return 15;
		
		else if (topMid(red) == c1 && leftMid(blue) == c2)
			return 16;
		else if (leftMid(red) == c1 && rightMid(white) == c2)
			return 17;
		else if (bottomMid(red) == c1 && leftMid(green) == c2)
			return 18;
		else if (rightMid(red) == c1 && leftMid(yellow) == c2)
			return 19;
		
		else if (topMid(green) == c1 && bottomMid(yellow) == c2)
			return 20;
		else if (leftMid(green) == c1 && bottomMid(red) == c2)
			return 21;
		else if (bottomMid(green) == c1 && bottomMid(white) == c2)
			return 22;
		else if (rightMid(green) == c1 && bottomMid(orange) == c2)
			return 23;
		
		else {
			errLog.println("Side doesn't exist: " + c1 + " and " + c2);
			System.exit(-1);
			return 0;
		}
		
		
	}

	public void performMoves(int[] moves) {
		try{
			for(int i : moves){
				switch(i){
					case 0:
						turnYellow(true);
						break;
					case 1:
						turnWhite(true);
						break;
					case 2:
						turnRed(true);
						break;
					case 3:
						turnOrange(true);
						break;
					case 4:
						turnBlue(true);
						break;
					case 5:
						turnGreen(true);
						break;
					case 6:
						turnMiddle(true);
						break;
					case 7:
						turnYellow(false);
						break;
					case 8:
						turnWhite(false);
						break;
					case 9:
						turnRed(false);
						break;
					case 10:
						turnOrange(false);
						break;
					case 11:
						turnBlue(false);
						break;
					case 12:
						turnGreen(false);
						break;
					case 13:
						turnMiddle(false);
						break;
					default:
						errLog.println("Invalid move number: " + i);
						System.exit(-1);
						
				}
			}
		} catch (Exception e){
			errLog.println(e.toString());
			System.exit(-1);
		}
	}
	
	public boolean topSolved(){
		return topCrossSolved() && topCornersSolved();
	}
	
	public boolean topCornersSolved(){
		return topRight(blue) == 'b' && topRight(orange) == 'o' && topLeft(white) == 'w'
			&& topLeft(blue) == 'b' && topRight(white) == 'w' && topLeft(red) == 'r'
			&& bottomLeft(blue) == 'b' && topRight(red) == 'r' && topLeft(yellow) == 'y'
			&& bottomRight(blue) == 'b' && topRight(yellow) == 'y' && topLeft(orange) == 'o';
	}
	
	public boolean topCrossSolved(){
		return topMid(red) == 'r' && topMid(white) == 'w' && topMid(yellow) == 'y' && topMid(orange) == 'o' 
			&& topMid(blue) == 'b' && leftMid(blue) == 'b' && rightMid(blue) == 'b' && bottomMid(blue) == 'b';
	}
	
	public int getTurnCount(){
		return turnCount;
	}
	
	
	public int translateColor(char c){
		switch(c){
			case 'y':
				return y;
			case 'w':
				return w;
			case 'b':
				return b;
			case 'g':
				return g;
			case 'r':
				return r;
			case 'o':
				return o;
			default:
				return -1;
		}
	}
	
	public char bottomLeft(char[][] face){
		return face[2][0];
	}
	
	public char bottomRight(char[][] face){
		return face[2][2];
	}
	
	public char topRight(char[][] face){
		return face[0][2];
	}
	
	public char topLeft(char[][] face){
		return face[0][0];
	}
	

	public int findCorner(char c1, char c2, char c3){
		int corner = translateColor(c1) | translateColor(c2) | translateColor(c3);
		
		if ((translateColor(bottomRight(blue)) | translateColor(topRight(yellow)) | translateColor(topLeft(orange))) == corner)
			return 0;
		else if ((translateColor(topRight(blue)) | translateColor(topRight(orange)) | translateColor(topLeft(white))) == corner)
			return 1;
		else if ((translateColor(topLeft(blue)) | translateColor(topRight(white)) | translateColor(topLeft(red))) == corner)
			return 2;
		else if ((translateColor(bottomLeft(blue)) | translateColor(topRight(red)) | translateColor(topLeft(yellow))) == corner)
			return 3;

		else if ((translateColor(topLeft(green)) | translateColor(bottomLeft(yellow)) | translateColor(bottomRight(red))) == corner)
			return 4;
		else if ((translateColor(bottomLeft(green)) | translateColor(bottomLeft(red)) | translateColor(bottomRight(white))) == corner)
			return 5;
		else if ((translateColor(bottomRight(green)) | translateColor(bottomLeft(white)) | translateColor(bottomRight(orange))) == corner)
			return 6;
		else if ((translateColor(topRight(green)) | translateColor(bottomLeft(orange)) | translateColor(bottomRight(yellow))) == corner)
			return 7;
		else
			return -1;
	}
	
	
	
	public boolean blueCornerOriented(char c1, char c2){
		return ((translateColor(topRight(yellow)) | translateColor(topLeft(orange))) == (translateColor(c1) | translateColor(c2))) && (bottomRight(blue) == 'b');
	}
	
	public boolean middleSolved(){
		return yoSideSolved() & owSideSolved() & wrSideSolved() & rySideSolved();
	}
	
	public boolean yoSideSolved(){
		return rightMid(yellow) == 'y' && leftMid(orange) == 'o';
	}
	
	public boolean owSideSolved(){
		return rightMid(orange) == 'o' && leftMid(white) == 'w';
	}
	
	public boolean wrSideSolved(){
		return rightMid(white) == 'w' && leftMid(red) == 'r';
	}
	
	public boolean rySideSolved(){
		return rightMid(red) == 'r' && leftMid(yellow) == 'y';
	}
	
	public int find2CorrectGreen() throws Exception{
		int state = -1;
			
			
		int c1= bottomMid(white);
		int c2 = bottomMid(orange);
		int c3 = bottomMid(yellow);
		int c4 = bottomMid(red);
		
		if(c1 == 'w' & c2 == 'o'){
			state = 0;
		} else if(c1 == 'o' & c2 == 'y'){
			state = 1;
		} else if(c1 == 'y' & c2 == 'r'){
			state = 2;
		} else if(c1 == 'r' & c2 == 'w'){
			state = 3;
		} else if(c1 == 'w' & c3 == 'y'){
			state = 4;
		} else if(c1 == 'o' & c3 == 'r'){
			state = 5;
		}
		
		if(c1 == 'w' & c2 == 'o' & c3 == 'y' & c4 == 'r'){
			state = 6;
		}
		

		
		return state;
	}
	
	public int greenCrossState(){
		int state = 0;
		
		state |= bottomMid(yellow) == 'y' ? 1 : 0;
		state <<= 1;
		state |= bottomMid(orange) == 'o' ? 1 : 0;
		state <<= 1;
		state |= bottomMid(white) == 'w' ? 1 : 0;
		state <<= 1;
		state |= bottomMid(red) == 'r' ? 1 : 0;
		state <<= 1;
		state |= rightMid(green) == 'g' ? 1 : 0;
		state <<= 1;
		state |= bottomMid(green) == 'g' ? 1 : 0;
		state <<= 1;
		state |= leftMid(green) == 'g' ? 1 : 0;
		state <<= 1;
		state |= topMid(green) == 'g' ? 1 : 0;
		
		int case8 = 0xff; // 1111 1111
		int case7 = 0x0f; // 	  1111
		int case6 = 0x0a; // 	  1010
		int case5 = 0x05; //	  0101
		int case4 = 0x03; //      0011
		int case3 = 0x06; //	  0110
		int case2 = 0x0c; //	  1100
		int case1 = 0x09; //	  1001
		
		if ((state & case8) == case8)
			return 8;
		else if ((state & case7) == case7)
			return 7;
		else if ((state & case6) == case6)
			return 6;
		else if ((state & case5) == case5)
			return 5;
		else if ((state & case4) == case4)
			return 4;
		else if ((state & case3) == case3)
			return 3;
		else if ((state & case2) == case2)
			return 2;
		else if ((state & case1) == case1)
			return 1;
		else
			return 0;
	}
	
	public int greenCornerPosition(){
		
		boolean yo = (translateColor(bottomRight(yellow)) | translateColor(bottomLeft(orange)) | translateColor(topRight(green))) == (y | o | g);
		boolean ow = (translateColor(bottomRight(orange)) | translateColor(bottomLeft(white)) | translateColor(bottomRight(green))) == (o | w | g);
		boolean wr = (translateColor(bottomRight(white)) | translateColor(bottomLeft(red)) | translateColor(bottomLeft(green))) == (w | r | g);
		boolean ry = (translateColor(bottomRight(red)) | translateColor(bottomLeft(yellow)) | translateColor(topLeft(green))) == (r | y | g);
		
		if (yo && ow && wr && ry)
			return 5;
		else if (yo)
			return 1;
		else if (ow)
			return 2;
		else if (wr)
			return 3;
		else if (ry)
			return 4;
		else
			return 0;
		
	}


	public boolean cornergreen() {
		return bottomRight(green) == 'g';
	}
	
	
	public boolean solved(){
		return topSolved() && middleSolved() && (greenCornerPosition() == 5) && (topLeft(blue) == 'b') && (topRight(blue) == 'b') && (bottomLeft(blue) == 'b') && (bottomRight(blue) == 'b');
	}
	
}
