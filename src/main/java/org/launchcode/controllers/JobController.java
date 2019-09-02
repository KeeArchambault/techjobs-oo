package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/job?id={id}", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view DONE

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
    public String add(RedirectAttributes id, Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        } else {

            int jobFormEmployerId = jobForm.getEmployerId();
            int jobFormLocationId = jobForm.getLocationId();
            int jobFormPositionTypeId = jobForm.getPositionTypeId();
            int jobFormCoreCompetencyId = jobForm.getCoreCompetencyId();
            Job job = new Job(jobForm.getName(), jobData.getEmployers().findById(jobFormEmployerId), jobData.getLocations().findById(jobFormLocationId), jobData.getPositionTypes().findById(jobFormPositionTypeId), jobData.getCoreCompetencies().findById(jobFormCoreCompetencyId));

                jobData.add(job);
                id.addAttribute("id", job.getId());

//                model.addAttribute("job", job);
//            return "job-detail";

            return "/job?id={id}";

        }
    }
}
