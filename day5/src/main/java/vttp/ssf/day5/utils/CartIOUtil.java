package vttp.ssf.day5.utils;

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

import vttp.ssf.day5.models.Item;

public class CartIOUtil {

    private static final Logger logger = LoggerFactory.getLogger(CartIOUtil.class);

    public static List<Item> convertStringsToItems(List<String> itemStringList) {
        List<Map> cartData = new LinkedList<Map>();
        List<Item> itemList = new LinkedList<Item>();

        for (String itemData : itemStringList) {
            Map<String, String> itemAttrMap = new HashMap<>();
            String[] itemSplitComma = itemData.split(",");
            for (String item :itemSplitComma) {
                String[] itemSplitColon = item.split(":");
                itemAttrMap.put(itemSplitColon[0].trim(), itemSplitColon[1].trim());
            }
            cartData.add(itemAttrMap);
        }

        for (Map<String, String> itemMap : cartData) {
            Item item = new Item(itemMap.get("name"), Integer.parseInt(itemMap.get("qty")));
            itemList.add(item);
        }

        return itemList;

    }

    public static Item convertStrToItem(String itemStr) {
        Map<String, String> itemAttrMap = new HashMap<>();
        String[] itemSplitComma = itemStr.split(",");
        for (String item :itemSplitComma) {
            String[] itemSplitColon = item.split(":");
            itemAttrMap.put(itemSplitColon[0].trim(), itemSplitColon[1].trim());
        }
        return new Item(itemAttrMap.get("name"), Integer.parseInt(itemAttrMap.get("qty")));
    }

}