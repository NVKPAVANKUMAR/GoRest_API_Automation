package Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Logic to retry test cases and no of retries depends on parameter from properties file.
 * This Class decides how many time failure tests need to rerun.
 *
 * @author NVK PAVANKUMAR
 * @version 1.0
 */
public class RetryFailedTestCases implements IRetryAnalyzer {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Variable to keep counter of retry
     */
    private int retryCount = 0;
    /**
     * Variable to define max retry count
     */
    private int maxRetryCount = 3;

    /**
     * This method decides how many times a test needs to be rerun. TestNg will
     * call this method every time a test fails.
     *
     * @param result -> The result of the test method that just ran.
     * @return boolean -> Returns true if the test method has to be retried, false otherwise.
     * @author NVK PAVANKUMAR
     */
    @Override
    public boolean retry(ITestResult result) {
        // We need to implement a cap, otherwise execution might go indefinite loop.
        if (retryCount < maxRetryCount) {
            logger.info("Retrying test " + result.getName() + " with status " + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCount++;
            return true;
        }
        return false;
    }

    /**
     * This method get the status name based on status code.
     *
     * @param status -> Status in integer form.
     * @return resultName -> Returns result name.
     * @author NVK PAVANKUMAR
     */
    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }
}
