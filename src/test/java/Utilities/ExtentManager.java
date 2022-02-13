package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    private static String reportFileName = "Test-Automaton-Report_" + timestamp + ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "test-report";
    private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;
    static final Logger logger = LoggerFactory.getLogger(ExtentManager.class);

    public static String getReportPath(){
        return reportFileLocation;
    }
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    /**
     * Create an extent report instance
     *
     * @return ExtentReports
     */
    public static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);

        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle(reportFileName);
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName(reportFileName);
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Username", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("AUT", "QA");
        extent.setSystemInfo("Java", System.getProperty("java.version"));

        return extent;
    }

    /**
     * Create the report path
     *
     * @param path - path to store the extent report
     * @return
     */
    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                logger.info("Directory: " + path + " is created!");
                return reportFileLocation;
            } else {
                logger.info("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            logger.info("Directory already exists: " + path);
        }
        return reportFileLocation;
    }

}
