package vttp.ssf.day2.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vttp.ssf.day2.exceptions.NumberGeneratorException;
import vttp.ssf.day2.model.NumberGenerator;

@Controller
public class NumberGeneratorController {
    private Logger logger = LoggerFactory.getLogger(NumberGeneratorController.class);

    @GetMapping("/")
    public String showNumGenerateForm(Model model) {
        NumberGenerator ng = new NumberGenerator();
        ng.setNumsToGenerate(1);
        model.addAttribute("numberGenerator", ng);
        return "generatePage";
    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute NumberGenerator ng, Model model) {
        int maxGenNo = 31;
        String[] imgNums = new String[maxGenNo];
        int numsToGenerate = ng.getNumsToGenerate();
        System.out.println(ng.getNumsToGenerate());

        // if (numsToGenerate < 0 || numsToGenerate > 30) {
        //     throw new NumberGeneratorException();
        // }

        // input img file names into arr
        for (int i = 0; i < maxGenNo; i++) {
            imgNums[i] = "number" + i + ".jpg";
        }

        List<String> selectedImgs = new ArrayList<String>();
        Random random = new Random();
        Set<Integer> uniqueResults = new LinkedHashSet<Integer>();
        while (uniqueResults.size() < numsToGenerate) {
            Integer newRandomNum = random.nextInt(maxGenNo);
            uniqueResults.add(newRandomNum);
        }

        Iterator<Integer> it = uniqueResults.iterator();
        Integer currInt = null;
        while (it.hasNext()) {
            currInt = it.next().intValue();
            selectedImgs.add(imgNums[currInt]);
        }

        model.addAttribute("resultImgs", selectedImgs.toArray());
        model.addAttribute("numsToGenerate",numsToGenerate);
        return "results";
    }

}
