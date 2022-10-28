package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int retryLimit = 3; /* if the test failed it will be reproduced at maximum 3 times */
    private int counter = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (counter < retryLimit) {
            counter++;
            return true;
        }
        return false;
    }

}

