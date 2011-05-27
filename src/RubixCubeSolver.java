import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.Random;

public class RubixCubeSolver {

	// flags
	private static boolean stdout = true;
	private static boolean traceSolution = true;

	private static String cF = ".*:.*";
	private static Pattern conf = Pattern.compile(cF, Pattern.CASE_INSENSITIVE);

	
	private static StringBuffer moves = new StringBuffer();

	public static void main(String[] args) throws Exception {

		Cube cube = new Cube();
		cube.addOutputStream(System.out);
		//cube.enableTurnLog();
		//cube.enableCubeLog();
		//cube.enableCubeRandomizeLog();
		//cube.enableRandomizeLog();
		
		if (args.length != 1) {
			System.out.println("usage: RubixCubeSolver <configuration file>");
		}

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
				build(line, cube);
			}
		}

		cube.checkIntegrity();

		testSolver(cube, 1000);
		

	}


	private static void testSolver(Cube cube, int scrambles) throws Exception{
		long totalTurns = 0;
		for(int i = 0; i < scrambles; i++){
			//System.out.println("Scrambling...");
			cube.scramble();
			//cube.printCube();
			//System.out.println("Solving...");
			Solver sol = new Solver(cube);
			sol.solve();
			//cube.printCube();
			if (!(cube.topSolved() && cube.yoSideSolved()))
				break;
			totalTurns += cube.getTurnCount();
		}
		if (!(cube.topSolved() && cube.yoSideSolved())){
			System.out.println("Failed to solve!");
		} else {
			System.out.println("Finished! No problems.");
			System.out.println("Scrambles solved: " + scrambles);
			System.out.println("Average amt. of turns to solve the top and y/o side: " + totalTurns/scrambles);
		}
	}




	// parse config file with StringTokenizer
	private static void build(String line, Cube cube) {

		StringTokenizer st = new StringTokenizer(line, ":");

		String temp = st.nextToken().trim();
		String colors = st.nextToken().trim();

		System.out.println(temp + ": " + colors);

		
		try {
			cube.buildFace(temp, colors);
		} catch (Exception e) {
			e.toString();
			System.exit(-1);
		}
		
	}



	
	

}
