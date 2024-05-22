package com.github.lksay3d.GHADash.service;

import com.github.lksay3d.GHADash.config.GitHubProperties;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final GitHubProperties gitHubProperties;

    @Autowired
    public GitHubService(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
    }

    public List<String> listRepositories() throws IOException {
        // Create a GitHub instance using the OAuth token
        GitHub github = new GitHubBuilder().withOAuthToken(gitHubProperties.getToken()).build();
        
        // Fetch the repositories for the authenticated user
        return github.getMyself().getAllRepositories().values().stream()
                .map(GHRepository::getFullName)
                .collect(Collectors.toList());
    }
}