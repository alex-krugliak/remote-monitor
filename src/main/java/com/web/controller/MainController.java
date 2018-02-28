package com.web.controller;

import com.web.exception.ApplicationException;
import com.web.service.ArchiveService;
import com.web.service.imp.BundlesServiceImpl;
import com.web.wrapper.response.DataCurrentWrapper;
import com.web.wrapper.response.StatisticWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created on 26.10.15.
 */

@Controller
public class MainController extends BaseController {

    @Autowired
    private ArchiveService archiveService;

    @RequestMapping({"/", "/home"})
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/getBundles")
    @ResponseBody
    private Map<String, String> getBundles() {
        return BundlesServiceImpl.getAllBundles("/label_ru.properties");
    }


    @RequestMapping(value = "/data/current", method = RequestMethod.GET)
    @ResponseBody
    private DataCurrentWrapper getCurrentData() throws ApplicationException {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        return archiveService.getAllCurrentViewData();
    }

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    @ResponseBody
    private List<StatisticWrapper> getStatisticData(@RequestParam(required = true) String lineName,
                                                    @RequestParam(required = true) Long dateFrom, @RequestParam(required = true) Long dateTo) throws ApplicationException {
        return archiveService.getStatistic(lineName, dateFrom, dateTo);
    }
}
