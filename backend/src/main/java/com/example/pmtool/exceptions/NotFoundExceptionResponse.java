package com.example.pmtool.exceptions;

public class NotFoundExceptionResponse {

    private String projectIdentifier;

    public NotFoundExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectTaskSequence() {
        return projectIdentifier;
    }

    public void setProjectTaskSequence(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
