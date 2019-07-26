package com.example.pmtool.web;

import com.example.pmtool.domain.ProjectTask;
import com.example.pmtool.services.MapValidationErrorService;
import com.example.pmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) return errorMap;
        ProjectTask pt = projectTaskService.addProjectTask(backlog_id, projectTask);
        return new ResponseEntity<>(pt, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
        return projectTaskService.findBacklogById(backlog_id.toUpperCase());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        ProjectTask projectTask = projectTaskService.findPTbyProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PutMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) return errorMap;
        ProjectTask pt = projectTaskService.updateProjectTaskBySequence(projectTask, backlog_id, pt_id);
        return new ResponseEntity<>(pt, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        projectTaskService.deletePTbyProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<String>("Project task '" + backlog_id.toUpperCase() + " / " + pt_id.toUpperCase() + "' has been deleted.", HttpStatus.OK);
    }
}
