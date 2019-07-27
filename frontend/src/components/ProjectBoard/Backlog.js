import React, { Component } from "react";
import ProjectTask from "./ProjectTask/ProjectTask";

class Backlog extends Component {
  render() {
    const { project_tasks } = this.props;
    const tasks = project_tasks.map(task => (
      <ProjectTask key={task.id} project_task={task} />
    ));
    let todo = tasks.filter(task => task.props.project_task.status === "TO DO");
    let inprogress = tasks.filter(
      task => task.props.project_task.status === "IN PROGRESS"
    );
    let done = tasks.filter(task => task.props.project_task.status === "DONE");

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-warning text-black">
                <h3>TO DO</h3>
              </div>
            </div>
            {todo}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inprogress}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {done}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
