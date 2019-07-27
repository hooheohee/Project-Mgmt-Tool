import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getProjectTask } from "../../../actions/backlogActions";

class UpdateProjectTask extends Component {
  constructor() {
    super();

    this.state = {
      id: "",
      summary: "",
      projectSequence: "",
      acceptanceCriteria: "",
      priority: "",
      dueDate: "",
      projectIdentifier: "",
      status: "",
      created_At: ""
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    const {
      id,
      summary,
      projectSequence,
      acceptanceCriteria,
      priority,
      dueDate,
      projectIdentifier,
      status,
      created_At
    } = nextProps.project_task;
    this.setState({
      id,
      summary,
      projectSequence,
      acceptanceCriteria,
      priority,
      dueDate,
      projectIdentifier,
      status,
      created_At
    });
  }

  componentDidMount() {
    const { backlog_id, pt_id } = this.props.match.params;
    this.props.getProjectTask(backlog_id, pt_id, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updateProjectTask = {
      id: this.state.id,
      summary: this.state.summary,
      projectSequence: this.state.projectSequence,
      projectIdentifier: this.state.projectIdentifier,
      acceptanceCriteria: this.state.acceptanceCriteria,
      priority: this.state.priority,
      dueDate: this.state.dueDate,
      created_At: this.state.created_At,
      status: this.state.status
    };
    console.log(updateProjectTask);
  }

  render() {
    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link
                to={`/projectboard/${this.state.projectIdentifier}`}
                className="btn btn-outline-secondary"
              >
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <hr />
              <p className="lead text-center">
                Project Name: {this.state.projectIdentifier} | Project Task ID:{" "}
                {this.state.projectSequence}
              </p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    name="summary"
                    placeholder="Project Task Summary"
                    value={this.state.summary}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Acceptance Criteria"
                    name="acceptanceCriteria"
                    value={
                      this.state.acceptanceCriteria !== null
                        ? this.state.acceptanceCriteria
                        : ""
                    }
                    onChange={this.onChange}
                  />
                </div>
                <h6>Due Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="dueDate"
                    value={
                      this.state.dueDate !== null ? this.state.dueDate : ""
                    }
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value={0}>Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                </div>

                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="status"
                    value={this.state.status}
                    onChange={this.onChange}
                  >
                    <option value="">Select Status</option>
                    <option value="TO DO">TO DO</option>
                    <option value="IN PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                </div>
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  project_task: state.backlog.project_task
});

UpdateProjectTask.propTypes = {
  project_task: PropTypes.object.isRequired,
  getProjectTask: PropTypes.func.isRequired
};

export default connect(
  mapStateToProps,
  { getProjectTask }
)(UpdateProjectTask);
