package com.github.lksay3d.GHADash.controller;

import com.github.lksay3d.GHADash.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class DashboardController {

    private final GitHubService gitHubService;

    @Autowired
    public DashboardController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) throws IOException {
        model.addAttribute("repositories", gitHubService.listRepositoriesWithActions());
        return "template";
    }
}