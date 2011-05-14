import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class RubixCubeSolver {

	// flags
	private static boolean stdout = true;
	private static boolean traceSolution = true;

	private static String cF = ".*:.*";
	private static Pattern conf = Pattern.compile(cF, Pattern.CASE_INSENSITIVE);

	private static char[][] yellow = new char[3][3]; // yellow is front
	private static char[][] white = new char[3][3]; // white is back
	private static char[][] blue = new char[3][3]; // blue is top
	private static char[][] green = new char[3][3]; // green is bottom
	private static char[][] red = new char[3][3]; // red is left
	private static char[][] orange = new char[3][3]; // orange is right

	private static int turn = 0;

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			logln("usage: RubixCubeSolver <configuration file>");
		}

		File configFile = null;
		FileReader fr = null;

		try {
			configFile = new File(args[0]);
			fr = new FileReader(configFile);
		} catch (IOException e) {
			logln("cannot read source file: " + args[0]);
		}

		String line;
		LineNumberReader lineReader = new LineNumberReader(fr);

		while ((line = lineReader.readLine()) != null) {
			if (conf.matcher(line).matches()) {
				if (build(line) == false) {
					System.exit(-1);
				}
			}
		}

		checkIntegrity();

		printCube();
		boolean t = true;
		boolean f = false;

		boolean right = true;

		for (int i = 0; i < 6; i++) {
			
			if (!right) {
				turnRed(t);
				turnGreen(t);
				turnRed(f);
				turnGreen(f);
			} else {
				turnOrange(f);
				turnGreen(f);
				turnOrange(t);
				turnGreen(t);
			}
		}

		print(blue);
		print(yellow);
		print(orange);
		print(green);
		print(white);
		print(red);

	}

	private static void turnYellow(boolean clockWise) {

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

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning yellow clockwise");
				printCube();
			}

		} else {
			turnFaceCounterClock(yellow);
			replaceColor(orangec, blue, "bottom");
			replaceColorR(greenc, orange, "left");
			replaceColor(redc, green, "top");
			replaceColorR(bluec, red, "right");
			if (traceSolution) {
				logln("Turn " + ++turn
						+ "\nturning yellow counter clockwise \n");
				printCube();
			}

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

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning blue clockwise");
				printCube();
			}

		} else {
			turnFaceCounterClock(blue);
			replaceColor(orangec, white, "top");
			replaceColor(yellowc, orange, "top");
			replaceColor(redc, yellow, "top");
			replaceColor(whitec, red, "top");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning blue counter clockwise \n");
				printCube();
			}
		}

	}

	private static void turnOrange(boolean clockWise) {

		char[] bluec = deepCopy(blue, "right");
		char[] whitec = deepCopy(white, "left");
		char[] yellowc = deepCopy(yellow, "right");
		char[] greenc = deepCopy(green, "right");

		for (int i = 0; i < greenc.length; i++) {
			System.out.println(whitec[i]);
		}

		if (clockWise) {
			turnFaceClock(orange);
			replaceColor(yellowc, blue, "right");
			replaceColorR(bluec, white, "left");
			replaceColorR(whitec, green, "right");
			replaceColor(greenc, yellow, "right");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning orange clockwise \n");
				printCube();
			}

		} else {
			turnFaceCounterClock(orange);
			replaceColorR(whitec, blue, "right");
			replaceColorR(greenc, white, "left");
			replaceColor(yellowc, green, "right");
			replaceColor(bluec, yellow, "right");

			if (traceSolution) {
				logln("Turn " + ++turn
						+ "\nturning orange counter clockwise \n");
				printCube();
			}
		}

	}

	private static void replaceColorR(char[] source, char[][] target,
			String side) {
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
				logln("error determining side (replaceColor)");
			}
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
			replaceColorR(bluec, red, "left");
			replaceColor(redc, green, "bottom");
			replaceColorR(greenc, orange, "right");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning white clockwise \n");
				printCube();
			}

		} else {
			turnFaceCounterClock(white);
			replaceColorR(redc, blue, "top");
			replaceColor(greenc, red, "left");
			replaceColorR(orangec, green, "bottom");
			replaceColor(bluec, orange, "right");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning white counter clockwise \n");
				printCube();
			}
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

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning green clockwise \n");
				printCube();
			}

		} else {
			turnFaceCounterClock(green);
			replaceColor(orangec, yellow, "bottom");
			replaceColor(whitec, orange, "bottom");
			replaceColor(redc, white, "bottom");
			replaceColor(yellowc, red, "bottom");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning green counter clockwise \n");
				printCube();
			}
		}

	}

	private static void turnRed(boolean clockWise) {

		char[] bluec = deepCopy(blue, "left");
		char[] yellowc = deepCopy(yellow, "left");
		char[] greenc = deepCopy(green, "left");
		char[] whitec = deepCopy(white, "right");

		if (clockWise) {
			turnFaceClock(green);
			replaceColorR(whitec, blue, "left");
			replaceColor(bluec, yellow, "left");
			replaceColor(yellowc, green, "left");
			replaceColorR(greenc, white, "right");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning green clockwise \n");
				printCube();
			}

		} else {
			turnFaceCounterClock(green);
			replaceColor(yellowc, blue, "left");
			replaceColorR(bluec, white, "right");
			replaceColor(greenc, yellow, "left");
			replaceColorR(whitec, green, "left");

			if (traceSolution) {
				logln("Turn " + ++turn + "\nturning green counter clockwise \n");
				printCube();
			}
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
				logln("error determining side (replaceColor)");
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
				logln("error determining side (deepcopy)");
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
	private static boolean build(String line) {

		StringTokenizer st = new StringTokenizer(line, ":");

		String temp = st.nextToken().trim();
		String colors = st.nextToken().trim();

		logln(temp + ": " + colors);

		boolean result = true;

		if (temp.equals("yellow")) {
			result = buildFace(yellow, colors, temp);
		} else if (temp.equals("white")) {
			result = buildFace(white, colors, temp);
		} else if (temp.equals("blue")) {
			result = buildFace(blue, colors, temp);
		} else if (temp.equals("green")) {
			result = buildFace(green, colors, temp);
		} else if (temp.equals("red")) {
			result = buildFace(red, colors, temp);
		} else if (temp.equals("orange")) {
			result = buildFace(orange, colors, temp);
		} else {
			logln("There is no \"" + temp + "\" side");
			result = false;
		}
		return result;
	}

	// build each face.
	private static boolean buildFace(char[][] face, String colors, String color) {

		if (face[0][0] != 0) {
			logln("Already built \"" + color + "\" face");
			return false;
		}

		int ch = 0;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				face[r][c] = colors.charAt(ch);
				ch++;
			}
		}

		return true;
	}

	private static void printCube() {
		log(cubeToString());
	}

	private static String cubeToString() {
		StringBuffer result = new StringBuffer();

		String blank = "      \n      \n      \n";
		result.append(interleave(blank, faceToString(blue)));
		result.append(interleave(interleave(faceToString(red),
				faceToString(yellow)), faceToString(orange)));
		result.append(interleave(blank, faceToString(green)));
		result.append(interleave(blank, faceToString(white, true)));
		return result.toString();

	}

	private static String faceToString(char[][] face) {
		StringBuffer result = new StringBuffer();
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				result.append(face[r][c] + " ");
			}
			result.append("\n");

		}
		return result.toString();
	}

	private static String faceToString(char[][] face, boolean upsideDown) {
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

	private static void print(char[][] face) {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				log(" " + face[r][c]);
			}
			logln();
		}

	}

	private static void logln() {
		log("\n");
	}

	private static void logln(String s) {
		log(s + "\n");
	}

	private static void log(String s) {
		if (stdout)
			System.out.print(s);
	}

	private static void checkIntegrity() {
		boolean valid = validFace(blue) && validFace(yellow)
				&& validFace(green) && validFace(white) && validFace(red)
				&& validFace(orange);
		if (!valid) {
			log("Bad face.\n");
			System.exit(-1);
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

}
