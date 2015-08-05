package com.experiments.rxjava.retrofit.data;

import java.util.List;

/**
 * Created by thimes on 2/24/15.
 */
public class Issue {
    int id;
    String url;
    String html_url;
    int number;
    String state;
    String title;
    String body;
    User user;
    List<GithubLabel> labels;
    User assignee;
    Milestone milestone;
    int comments;
    PullRequest pull_request;
    String closed_at;
    String created_at;
    String updated_at;
    User closed_by;
}
