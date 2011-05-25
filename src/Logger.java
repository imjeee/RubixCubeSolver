import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.LinkedHashSet;


public class Logger {
	
	public static Logger nullLog = new Logger();

	private Set<PrintStream> pStreams = new LinkedHashSet<PrintStream>();
	private Set<PrintWriter> pWriters = new LinkedHashSet<PrintWriter>();
	
	private boolean enabled = false;
	
	public boolean add(PrintStream p){
		return pStreams.add(p);
	}
	
	public boolean remove(PrintStream p){
		return pStreams.remove(p);
	}
	
	public boolean add(PrintWriter p){
		return pWriters.add(p);
	}
	
	public boolean remove(PrintWriter p){
		return pWriters.remove(p);
	}
	
	public void print(String p){
		if (enabled){
			for(PrintStream ps : pStreams)
				ps.print(p);
			for(PrintWriter pw : pWriters)
				pw.print(pw);
		}
	}
	
	public void println(String p){
		if (enabled){
			for(PrintStream ps : pStreams)
				ps.println(p);
			for(PrintWriter pw : pWriters)
				pw.println(pw);
		}
	}
	
	public void println(){
		if (enabled){
			for(PrintStream ps : pStreams)
				ps.println();
			for(PrintWriter pw : pWriters)
				pw.println();
		}
	}
	
	
	public void enable(){
		enabled = true;
	}

	public void disable(){
		enabled = false;
	}
}
