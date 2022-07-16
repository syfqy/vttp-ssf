package vttp.ssf.day3.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static void createDir(String path) {
        File dir = new File(path);
        boolean isCreated = dir.mkdirs();
        logger.info("dir created > " + isCreated);
        if(isCreated) {
            String osName = System.getProperty("os.name");
            if(!osName.contains("Windows")) {
                try {
                    String perm = "rwxrwx---";
                    Set<PosixFilePermission> permission = PosixFilePermissions.fromString(perm);
                    Files.setPosixFilePermissions(dir.toPath(), permission);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
        
    }
    

}
