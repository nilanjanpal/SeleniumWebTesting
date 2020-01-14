package customLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Logger{

  private static List<String[]> m_output = new Vector();
  
  public static List<String> getOutput(String className){
    System.out.println(className);
    List<String> outputLogs = new ArrayList<String>();
    for (String[] output : m_output) {
      if(output[0].equals(className)) {
        outputLogs.add(output[1]);
      }
    }
    return outputLogs;
  }
  
  public static synchronized void log(String className, String stringLog) {
    if(!stringLog.isEmpty()) {
      String[] logValues = {className, stringLog};
      m_output.add(logValues);
    }
  }

}
