package com.example.ResumeReviewerApplication.ResumeController;

import com.example.ResumeReviewerApplication.ResumeService.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/review")
    public String reviewResume(@RequestBody String resumeText) {
        return resumeService.reviewWithAI(resumeText);
    }

}
