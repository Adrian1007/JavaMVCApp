package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adi on 06.05.2015.
 */

@MyController(urlPath = "/jobs")
public class JobsController {

    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllJobs() {
        String allJobs = "getAllJobs";
        return allJobs;
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneJobs() {
        String oneJobs = "getOneJobs";
        return oneJobs;
    }
}
