package com.manjot.coronavirustracker.controllers;

import com.manjot.coronavirustracker.models.LocationStats;
import com.manjot.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats= coronaVirusDataService.getAllStats();
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        int totalReportedCasesUnformatted = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        String totalReportedCases=  myFormat.format(totalReportedCasesUnformatted);

        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";

    }
}
