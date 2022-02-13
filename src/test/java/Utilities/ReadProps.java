package Utilities;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProps {
    public static String getValue(String propName) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\Data\\APIConfiguration.properties");
        properties.load(fis);
        return properties.getProperty(propName);
    }

    public static void UpdatePropertyFile() throws ConfigurationException {
        Parameters params = new Parameters();
        File propertiesFile = new File(System.getProperty("user.dir") + "\\src\\test\\java\\Data\\APIConfiguration.properties");
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params
                                .fileBased()
                                .setFile(propertiesFile)
                        );
        Configuration config = builder.getConfiguration();
        config.setProperty("name", "name" + APIServiceUtils.timestamp);
        config.setProperty("email", "name" + APIServiceUtils.timestamp + "@test.com");
        builder.save();
    }
}


