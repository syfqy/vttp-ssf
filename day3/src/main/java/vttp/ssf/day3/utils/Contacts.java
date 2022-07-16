package vttp.ssf.day3.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import vttp.ssf.day3.models.Contact;


@Component("contacts")
public class Contacts {

    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    public void saveContact(Contact c, Model model, ApplicationArguments appArgs, String defaultDataDir) {

        String cFileName = c.getId();
        String cName = c.getName();
        String cEmail = c.getEmail();
        int cPhoneNum = c.getPhoneNum();

        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(getDataDir(appArgs, defaultDataDir) + "/"  + cFileName);
            pw = new PrintWriter(fw);
            pw.println(cName);
            pw.println(cEmail);
            pw.println(cPhoneNum);
            pw.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        model.addAttribute("contact", c);

    }

    public void getContactById(String contactId, Model model, ApplicationArguments appArgs, String defaultDataDir) {

        Contact c = new Contact();

        try {
            Path cFilePath = new File(getDataDir(appArgs, defaultDataDir) + "/" + contactId).toPath();
            Charset cs = Charset.forName("UTF-8");
            List<String> cInfoList = Files.readAllLines(cFilePath, cs);

            c.setId(contactId);
            c.setName(cInfoList.get(0));
            c.setEmail(cInfoList.get(1));
            c.setPhoneNum(Integer.parseInt(cInfoList.get(2)));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact info not found");
        }

        model.addAttribute("contact", c);

    }

    public String getDataDir(ApplicationArguments appArgs, String defaultDataDir) {
        String dataDir = "";
        List<String> argVals = null;
        String[] argValsArr = null;
        Set<String> argNamesSet = appArgs.getOptionNames();
        String[] argNamesArr = argNamesSet.toArray(new String[argNamesSet.size()]);

        if (argNamesArr.length > 0) {
            argVals = appArgs.getOptionValues(argNamesArr[0]);
            argValsArr = argVals.toArray(new String[argVals.size()]);
            dataDir = argValsArr[0];
        } else {
            dataDir = defaultDataDir;
        }

        return dataDir;
    }

}
