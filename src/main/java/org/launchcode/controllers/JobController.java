package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/detail?id={id}", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "redirect: /add";
        } else {

            int jobFormEmployerId = jobForm.getEmployerId();
            int jobFormLocationId = jobForm.getLocationId();
            int jobFormPositionTypeId = jobForm.getPositionTypeId();
            int jobFormCoreCompetencyId = jobForm.getCoreCompetencyId();
                Job newJob = new Job(jobForm.getName(), jobData.getEmployers().findById(jobFormEmployerId), jobData.getLocations().findById(jobFormLocationId), jobData.getPositionTypes().findById(jobFormPositionTypeId), jobData.getCoreCompetencies().findById(jobFormCoreCompetencyId));
                int id = newJob.getId();
                jobData.add(newJob);
            return "redirect: /detail?id={id}";

        }
    }
}
