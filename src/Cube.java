import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;


public class Cube {
	
	private static String[] turns = {"F+", "B+", "L+", "R+", "U+", "D+", "F-", "B-", "L-", "R-", "U-", "D-"};
	
	private LinkedList<Integer> moves = new LinkedList<Integer>();
	
	public static final int y = 0, w = 1, b = 2, g = 3, r = 4, o = 5;
	
	private char[][] yellow = new char[3][3]; // yellow is front
	private char[][] white = new char[3][3]; // white is back
	private char[][] blue = new char[3][3]; // blue is top
	private char[][] green = new char[3][3]; // green is bottom
	private char[][] red = new char[3][3]; // red is left
	private char[][] orange = new char[3][3]; // orange is right
	
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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(yellow);
			replaceColor(orangec, blue, "bottom");
			replaceColorR(greenc, orange, "left");
			replaceColor(redc, green, "top");
			replaceColorR(bluec, red, "right");
			turnLog.println("Turn " + ++turnCount+ "\nturning yellow counter clockwise \n");
			cubeLog.println(cubeToString());

		}
		
		moves.add(colorNum + (clockWise ? 0 : 6));

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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(blue);
			replaceColor(orangec, white, "top");
			replaceColor(yellowc, orange, "top");
			replaceColor(redc, yellow, "top");
			replaceColor(whitec, red, "top");

			turnLog.println("Turn " + ++turnCount+ "\nturning blue counter clockwise \n");
			cubeLog.println(cubeToString());
		}

		moves.add(colorNum + (clockWise ? 0 : 6));

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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(orange);
			replaceColorR(whitec, blue, "right");
			replaceColorR(greenc, white, "left");
			replaceColor(yellowc, green, "right");
			replaceColor(bluec, yellow, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning orange counter clockwise \n");
			cubeLog.println(cubeToString());
		}

		moves.add(colorNum + (clockWise ? 0 : 6));
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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(white);
			replaceColorR(redc, blue, "top");
			replaceColor(greenc, red, "left");
			replaceColorR(orangec, green, "bottom");
			replaceColor(bluec, orange, "right");

			turnLog.println("Turn " + ++turnCount+ "\nturning white counter clockwise \n");
			cubeLog.println(cubeToString());
		}
		
		moves.add(colorNum + (clockWise ? 0 : 6));

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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(green);
			replaceColor(orangec, yellow, "bottom");
			replaceColor(whitec, orange, "bottom");
			replaceColor(redc, white, "bottom");
			replaceColor(yellowc, red, "bottom");

			turnLog.println("Turn " + ++turnCount+ "\nturning green counter clockwise \n");
			cubeLog.println(cubeToString());
		}
		
		moves.add(colorNum + (clockWise ? 0 : 6));

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
			cubeLog.println(cubeToString());

		} else {
			turnFaceCounterClock(red);
			replaceColor(yellowc, blue, "left");
			replaceColorR(bluec, white, "right");
			replaceColor(greenc, yellow, "left");
			replaceColorR(whitec, green, "left");

			turnLog.println("Turn " + ++turnCount+ "\nturning red counter clockwise \n");
			cubeLog.println(cubeToString());
		}
		
		moves.add(colorNum + (clockWise ? 0 : 6));

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
			else if (side.equals("bottom")) {
				target[2][i] = source[i];
			} else if (side.equals("left")) {
				target[i][0] = source[i];
			} else if (side.equals("right")) {
				target[i][2] = source[i];
			} else {
				throw new Exception("error determining side (replaceColor)");
			}
		}
	}
	
	private void replaceColorR(char[] source, char[][] target, String side) throws Exception {
		for (int i = 0; i < 3; i++) {
			if (side.equals("top"))
				target[0][2 - i] = source[i];
			else if (side.equals("bottom")) {
				target[2][2 - i] = source[i];
			} else if (side.equals("left")) {
				target[2 - i][0] = source[i];
			} else if (side.equals("right")) {
				target[2 - i][2] = source[i];
			} else {
				throw new Exception("error determining side (replaceColor)");
			}
		}

	}
	
	private static char[] deepCopy(char[][] face, String side) throws Exception {

		char[] result = new char[3];

		for (int i = 0; i < 3; i++) {
			if (side.equals("top"))
				result[i] = face[0][i];
			else if (side.equals("bottom")) {
				result[i] = face[2][i];
			} else if (side.equals("left")) {
				result[i] = face[i][0];
			} else if (side.equals("right")) {
				result[i] = face[i][2];
			} else {
				throw new Exception("error determining side (deepcopy)");
			}
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

		faceName = faceName.toLowerCase();
		
		char[][] face;

		if (faceName.equals("white")){
			face = white;
		} 
		else if (faceName.equals("blue")){
			face = blue;
		}
		else if (faceName.equals("yellow")){
			face = yellow;
		}
		else if (faceName.equals("red")){
			face = red;
		}
		else if (faceName.equals("orange")){
			face = orange;
		}
		else if (faceName.equals("green")){
			face = green;
		}
		else
			throw new Exception(faceName + " is not a valid face color.");
		
		
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
						turnYellow(false);
						break;
					case 7:
						turnWhite(false);
						break;
					case 8:
						turnRed(false);
						break;
					case 9:
						turnOrange(false);
						break;
					case 10:
						turnBlue(false);
						break;
					case 11:
						turnGreen(false);
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
	
	
}
