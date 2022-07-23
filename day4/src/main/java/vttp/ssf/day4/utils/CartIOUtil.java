package vttp.ssf.day4.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartIOUtil {

    private static final Logger logger = LoggerFactory.getLogger(CartIOUtil.class);

    // FIXME: Check user cart file is a file and not dir in case of empty user
    public static void createDir(File userCartFile) {

        File cartFilesDir = userCartFile.getParentFile();

        if (!userCartFile.exists()) {
            cartFilesDir.mkdirs();
            try {
                userCartFile.createNewFile();
                logger.info("Created new user cart file at: " + userCartFile.toString());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public static void writeItemsToFile(File userCartFile, List<String> itemStringList) {

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

    public static List<Map> readItemsFromFile(File userCartFile) {
        List<Map> cartData = new LinkedList<Map>();
        try {
            List<String> itemStrArr = Files.readAllLines(userCartFile.toPath());
            for (String itemData : itemStrArr) {
                Map<String, String> itemAttrMap = new HashMap<>();
                String[] itemSplitComma = itemData.split(",");
                for (String item :itemSplitComma) {
                    String[] itemSplitColon = item.split(":");
                    itemAttrMap.put(itemSplitColon[0].trim(), itemSplitColon[1].trim());
                }
                cartData.add(itemAttrMap);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    return cartData;

    }

}