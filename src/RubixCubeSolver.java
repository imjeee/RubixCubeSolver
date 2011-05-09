import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class RubixCubeSolver {

	static String cF = ".*:.*";
	static Pattern conf = Pattern.compile(cF, Pattern.CASE_INSENSITIVE);

	static char[][] front = new char[3][3];
	static char[][] back = new char[3][3];
	static char[][] top = new char[3][3];
	static char[][] bottom = new char[3][3];
	static char[][] left = new char[3][3];
	static char[][] right = new char[3][3];

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

		/*
		 * top:ggggggggg 
		 * bottom:bbbbbbbbb 
		 * front:rrrrrrrrr 
		 * back:ooooooooo
		 * left:yyyyyyyyy 
		 * right:wwwwwwwww
		 */
		printTop();
		printBottom();
		printLeft();
		printRight();
		printFront();
		printBack();

	}



	// parse config file with StringTokenizer
	private static void build(String line) {

		StringTokenizer st = new StringTokenizer(line, ":");

		String temp = st.nextToken();
		String color = st.nextToken();

		System.out.println(temp + ": " + color);

		if (temp.equals("front")) {
			buildFace(front, color);
		} else if (temp.equals("back")) {
			buildFace(back, color);
		} else if (temp.equals("top")) {
			buildFace(top, color);
		} else if (temp.equals("bottom")) {
			buildFace(bottom, color);
		} else if (temp.equals("left")) {
			buildFace(left, color);
		} else if (temp.equals("right")) {
			buildFace(right, color);
		} else {
			System.out.println("what side again? " + temp);
		}

	}

	// build each face.
	private static void buildFace(char[][] face, String color) {

		int ch = 0;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				face[r][c] = color.charAt(ch);
				ch++;
			}
		}

	}

	//print a side
	private static void printTop() {
		System.out.println("top: ");
		print(top);
	}

	private static void printBottom() {
		System.out.println("bottom: ");
		print(bottom);
	}

	private static void printLeft() {
		System.out.println("left: ");
		print(left);
	}

	private static void printRight() {
		System.out.println("right: ");
		print(right);
	}

	private static void printFront() {
		System.out.println("front: ");
		print(front);
	}

	private static void printBack() {
		System.out.println("back: ");
		print(back);
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
