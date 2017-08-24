package pl.novaris.resumebuilder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ResourceBundle;

@ConfigurationProperties("storage")
public class StorageConfig {

    /**
     * Folder location for storing files
     */

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");

    private String location = "upload-dir";

    private String targetFilename = resourceBundle.getString("target.filename");

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTargetFilename() {
        return targetFilename;
    }

    public void setTargetFilename(String targetFilename) {
        this.targetFilename = targetFilename;
    }
}
