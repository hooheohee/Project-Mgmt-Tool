import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTask/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTask/UpdateProjectTask";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/addproject" component={AddProject} />
          <Route exact path="/updateproject/:id" component={UpdateProject} />
          <Route exact path="/projectboard/:id" component={ProjectBoard} />
          <Route exact path="/addprojecttask/:id" component={AddProjectTask} />
          <Route
            exact
            path="/updateprojecttask/:backlog_id/:pt_id"
            component={UpdateProjectTask}
          />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
