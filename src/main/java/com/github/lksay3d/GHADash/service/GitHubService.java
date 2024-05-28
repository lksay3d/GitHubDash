package com.github.lksay3d.GHADash.service;

import com.github.lksay3d.GHADash.config.GitHubProperties;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final GitHubProperties gitHubProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
        this.restTemplate = new RestTemplate();
    }

    public List<String> listRepositoriesWithActions() throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(gitHubProperties.getToken()).build();
        return github.getMyself().getAllRepositories().values().stream()
                .filter(this::hasGitHubActionsWithDeploymentLogs)
                .map(GHRepository::getFullName)
                .collect(Collectors.toList());
    }

    private boolean hasGitHubActionsWithDeploymentLogs(GHRepository repository) {
        String workflowsUrl = String.format("https://api.github.com/repos/%s/actions/workflows", repository.getFullName());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + gitHubProperties.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(workflowsUrl, HttpMethod.GET, entity, Map.class);
            List<Map<String, Object>> workflows = (List<Map<String, Object>>) response.getBody().get("workflows");

            if (workflows != null) {
                for (Map<String, Object> workflow : workflows) {
                    String runsUrl = workflow.get("url").toString().replace("/actions/workflows/", "/actions/runs/");
                    ResponseEntity<Map> runsResponse = restTemplate.exchange(runsUrl, HttpMethod.GET, entity, Map.class);
                    List<Map<String, Object>> runs = (List<Map<String, Object>>) runsResponse.getBody().get("workflow_runs");

                    if (runs != null) {
                        for (Map<String, Object> run : runs) {
                            if (run.get("conclusion") != null && run.get("logs_url") != null) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}