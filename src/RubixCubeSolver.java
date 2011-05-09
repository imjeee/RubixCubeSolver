import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class RubixCubeSolver {

	static String cF = ".*:.*";
	static Pattern conf = Pattern.compile(cF, Pattern.CASE_INSENSITIVE);

	static char[][] yellow = new char[3][3];
	static char[][] white = new char[3][3];
	static char[][] blue = new char[3][3];
	static char[][] green = new char[3][3];
	static char[][] red = new char[3][3];
	static char[][] orange = new char[3][3];
	
	static ArrayList<char[][]> yellowNei = new ArrayList<char[][]>();
	static ArrayList<char[][]> blueNei = new ArrayList<char[][]>();
	static ArrayList<char[][]> orangeNei = new ArrayList<char[][]>();
	static ArrayList<char[][]> redNei = new ArrayList<char[][]>();
	static ArrayList<char[][]> whiteNei = new ArrayList<char[][]>();
	static ArrayList<char[][]> GreenNei = new ArrayList<char[][]>();





	public static void main(String[] args) throws Exception {

		
		if (args.length != 1) {
			System.out.println("usage: RubixCubeSolver <configuration file>");
		}

		//
		File configFile = null;
		FileReader fr = null;

		try {
			configFile = new File(args[0]);
			fr = new FileReader(configFile);
		} catch (IOException e) {
			System.out.println("cannot read source file: " + args[0]);
		}

		String line;
		LineNumberReader lineReader = new LineNumberReader(fr);

		while ((line = lineReader.readLine()) != null) {
			if (conf.matcher(line).matches()) {
				build(line);
			}
		}
		
		constructFaceNeighbors();

		 
		printEverything();
		turnYellow(false);
		printEverything();

	}

	private static void constructFaceNeighbors() {

		yellowNei.add(blue);
		yellowNei.add(orange);
		yellowNei.add(green);
		yellowNei.add(red);
		
		blueNei.add(white);
		blueNei.add(orange);
		blueNei.add(yellow);
		blueNei.add(red);
		
		orangeNei.add(blue);
		orangeNei.add(white);
		orangeNei.add(green);
		orangeNei.add(yellow);

		redNei.add(blue);
		redNei.add(yellow);
		redNei.add(green);
		redNei.add(white);

		whiteNei.add(green);
		whiteNei.add(orange);
		whiteNei.add(blue);
		whiteNei.add(red);
		
		GreenNei.add(yellow);
		GreenNei.add(orange);
		GreenNei.add(white);
		GreenNei.add(red);	
	}

	private static void turnYellow(boolean clockWise) {


		char[] copyBlueBottom = deepCopy(blue, 2, true);
		char[] copyGreenTop = deepCopy(green, 0, true);
		char[] copyOrangeLeft = deepCopy(orange, 0, false);
		char[] copyRedRight = deepCopy(red, 2, false);

		if(clockWise){
			turnFaceClock(yellow);
			replaceColor(blue, copyRedRight, 2, true);
			replaceColor(orange, copyBlueBottom, 0, false);
			replaceColor(green, copyOrangeLeft, 0, true);
			replaceColor(red, copyGreenTop, 2, false);
		} else {
			turnFaceCounterClock(yellow);
			replaceColor(blue, copyOrangeLeft, 2, true);
			replaceColor(orange, copyGreenTop, 0, false);
			replaceColor(green, copyRedRight, 0, true);
			replaceColor(red, copyBlueBottom, 2, false);
		}
	
	}

	private static void turnFaceCounterClock(char[][] yellow2) {
		// TODO Auto-generated method stub
		
	}

	private static void replaceColor(char[][] target, char[] source, int j,
			boolean replaceRow) {

		for (int i = 0; i < 3; i++) {
			if (replaceRow) {
				target[j][i] = source[i];
			} else {
				target[i][j] = source[i];
			}
		}
	}

	private static char[] deepCopy(char[][] face, int cr, boolean copyRow) {

		char[] result = new char[3];

		for (int i = 0; i < 3; i++) {
			if (copyRow) {
				result[i] = face[cr][i];
			} else {
				result[i] = face[i][cr];
			}
		}

		return result;
	}

	private static void turnFaceClock(char[][] face) {

		char[][] tempFace = deepCopyFace(face);

		face[0][0] = tempFace[2][0];
		face[0][1] = tempFace[1][0];
		face[0][2] = tempFace[0][0];

		face[1][0] = tempFace[2][1];
		face[1][2] = tempFace[0][1];

		face[2][0] = tempFace[0][2];
		face[2][1] = tempFace[1][2];
		face[2][2] = tempFace[2][2];
	}

	private static char[][] deepCopyFace(char[][] face) {

		char[][] copy = new char[3][3];
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				copy[r][c] = face[r][c];
			}
		}
		return copy;
	}

	// parse config file with StringTokenizer
	private static void build(String line) {

		StringTokenizer st = new StringTokenizer(line, ":");

		String temp = st.nextToken().trim();
		String colors = st.nextToken().trim();

		System.out.println(temp + ": " + colors);

		if (temp.equals("yellow")) {
			buildFace(yellow, colors);
		} else if (temp.equals("white")) {
			buildFace(white, colors);
		} else if (temp.equals("blue")) {
			buildFace(blue, colors);
		} else if (temp.equals("green")) {
			buildFace(green, colors);
		} else if (temp.equals("red")) {
			buildFace(red, colors);
		} else if (temp.equals("orange")) {
			buildFace(orange, colors);
		} else {
			System.out.println("what side again? " + temp);
		}

	}

	// build each face.
	private static void buildFace(char[][] face, String colors) {

		int ch = 0;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				face[r][c] = colors.charAt(ch);
				ch++;
			}
		}

	}

	// print a side
	private static void printBlue() {
		System.out.println("blue: ");
		print(blue);
	}

	private static void printGreen() {
		System.out.println("green: ");
		print(green);
	}

	private static void printRed() {
		System.out.println("red: ");
		print(red);
	}

	private static void printOrange() {
		System.out.println("orange: ");
		print(orange);
	}

	private static void printYellow() {
		System.out.println("yellow: ");
		print(yellow);
	}

	private static void printWhite() {
		System.out.println("white: ");
		print(white);
	}

	private static void printEverything() {
		System.out.println("print EVERYTHING: ");
		printBlue();
		printGreen();
		printRed();
		printOrange();
		printYellow();
		printWhite();
		System.out.println();
	}

	private static void print(char[][] face) {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				System.out.print(face[r][c]);
			}
			System.out.println();
		}

	}

}
