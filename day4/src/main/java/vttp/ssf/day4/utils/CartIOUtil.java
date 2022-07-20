package vttp.ssf.day4.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartIOUtil {

    private static final Logger logger = LoggerFactory.getLogger(CartIOUtil.class);

    public static void createDir(File userCartFile) {

        File cartFilesDir = userCartFile.getParentFile();

        if (!userCartFile.exists()) {
            cartFilesDir.mkdirs();
            try {
                userCartFile.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public static void writeToFile(File userCartFile, List<String> itemStringList) {

        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(userCartFile);
            pw = new PrintWriter(fw);
            for (String itemString : itemStringList) {
                pw.println(itemString);
            }
            pw.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

}