package listener;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import driverManager.Driver;
import utils.FileUtility;

public class TestCaseListener implements ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    FileUtility.cleanDirectory("screenshots");
    Driver.setDriver();
    
  }

  @Override
  public void onFinish(ISuite suite) {
    Driver.clearDriver();   
    
  }



}
