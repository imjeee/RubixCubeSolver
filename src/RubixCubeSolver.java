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

	static char[][] yellow = new char[3][3]; // yellow is front
	static char[][] white = new char[3][3]; // white is back
	static char[][] blue = new char[3][3]; // blue is top
	static char[][] green = new char[3][3]; // green is bottom
	static char[][] red = new char[3][3]; // red is left
	static char[][] orange = new char[3][3]; // orange is right

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

		// printEverything();
		// turnYellow(true);
		// turnBlue(true);
		// turnOrange(true);
		printEverything();

	}

	private static void turnYellow(boolean clockWise) {

		char[] bluec = deepCopy(blue, "bottom");
		char[] orangec = deepCopy(orange, "left");
		char[] greenc = deepCopy(green, "top");
		char[] redc = deepCopy(red, "right");

		if (clockWise) {
			turnFaceClock(yellow);
			replaceColor(redc, blue, "bottom");
			replaceColor(bluec, orange, "left");
			replaceColor(orangec, green, "top");
			replaceColor(greenc, red, "right");

			System.out.println("turn yellow clockwise");

		} else {
			turnFaceCounterClock(yellow);
			replaceColor(orangec, blue, "bottom");
			replaceColor(greenc, orange, "left");
			replaceColor(redc, green, "top");
			replaceColor(bluec, red, "right");
			System.out.println("turn yellow counter clockwise \n");

		}

	}

	private static void turnBlue(boolean clockWise) {

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

			System.out.println("turn blue clockwise");

		} else {
			turnFaceCounterClock(blue);
			replaceColor(orangec, white, "top");
			replaceColor(yellowc, orange, "top");
			replaceColor(redc, yellow, "top");
			replaceColor(whitec, red, "top");

			System.out.println("turn blue counter clockwise \n");
		}

	}

	private static void turnOrange(boolean clockWise) {

		char[] bluec = deepCopy(blue, "right");
		char[] whitec = deepCopy(white, "right");
		char[] yellowc = deepCopy(yellow, "right");
		char[] greenc = deepCopy(green, "left");

		if (clockWise) {
			turnFaceClock(orange);
			replaceColor(yellowc, blue, "right");
			replaceColor(bluec, white, "left");
			replaceColor(whitec, green, "right");
			replaceColor(greenc, yellow, "right");

			System.out.println("turn orange clockwise \n");

		} else {
			turnFaceCounterClock(orange);
			replaceColor(whitec, blue, "right");
			replaceColor(greenc, white, "left");
			replaceColor(yellowc, green, "right");
			replaceColor(bluec, yellow, "right");

			System.out.println("turn orange counter clockwise \n");
		}

	}

	private static void turnWhite(boolean clockWise) {

		char[] orangec = deepCopy(orange, "right");
		char[] bluec = deepCopy(blue, "top");
		char[] redc = deepCopy(red, "left");
		char[] greenc = deepCopy(green, "bottom");

		if (clockWise) {
			turnFaceClock(white);
			replaceColor(orangec, blue, "top");
			replaceColor(bluec, red, "left");
			replaceColor(redc, green, "bottom");
			replaceColor(greenc, orange, "right");

			System.out.println("turn white clockwise \n");

		} else {
			turnFaceCounterClock(white);
			replaceColor(redc, blue, "top");
			replaceColor(greenc, red, "left");
			replaceColor(orangec, green, "bottom");
			replaceColor(bluec, orange, "right");

			System.out.println("turn white counter clockwise \n");
		}

	}

	private static void turnGreen(boolean clockWise) {

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

			System.out.println("turn green clockwise \n");

		} else {
			turnFaceCounterClock(green);
			replaceColor(orangec, yellow, "bottom");
			replaceColor(whitec, orange, "bottom");
			replaceColor(redc, white, "bottom");
			replaceColor(yellowc, red, "bottom");

			System.out.println("turn green counter clockwise \n");
		}

	}

	private static void turnRed(boolean clockWise) {

		char[] bluec = deepCopy(blue, "left");
		char[] yellowc = deepCopy(yellow, "left");
		char[] greenc = deepCopy(green, "left");
		char[] whitec = deepCopy(white, "right");

		if (clockWise) {
			turnFaceClock(green);
			replaceColor(whitec, blue, "left");
			replaceColor(bluec, yellow, "left");
			replaceColor(yellowc, green, "left");
			replaceColor(greenc, white, "right");

			System.out.println("turn green clockwise \n");

		} else {
			turnFaceCounterClock(green);
			replaceColor(yellowc, blue, "left");
			replaceColor(bluec, white, "right");
			replaceColor(greenc, yellow, "left");
			replaceColor(whitec, green, "left");

			System.out.println("turn green counter clockwise \n");
		}

	}

	private static void turnFaceClock(char[][] face) {

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

	private static void turnFaceCounterClock(char[][] face) {

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

	private static void replaceColor(char[] source, char[][] target, String side) {

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
				System.out.println("error determining side (replaceColor)");
			}
		}
	}

	private static char[] deepCopy(char[][] face, String side) {

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
				System.out.println("error determining side (deepcopy)");
			}
		}

		return result;
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
				System.out.print(" " + face[r][c]);
			}
			System.out.println();
		}

	}

}
