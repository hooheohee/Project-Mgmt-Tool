package com.example.pmtool.exceptions;

public class NotFoundExceptionResponse {

    private String projectTaskSequence;

    public NotFoundExceptionResponse(String projectIdentifier) {
        this.projectTaskSequence = projectIdentifier;
    }

    public String getProjectTaskSequence() {
        return projectTaskSequence;
    }

    public void setProjectTaskSequence(String projectIdentifier) {
        this.projectTaskSequence = projectIdentifier;
    }
}
