import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTask/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTask/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserMangement/Register";
import Login from "./components/UserMangement/Login";
import jwt_decode from "jwt-decode";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoute from "./securityUtils/SecureRoute";
import { setJwt } from "./securityUtils/setJwt";

const jwt = localStorage.getItem("jwt");
if (jwt) {
  setJwt(jwt);
  const decoded = jwt_decode(jwt);
  const currentTime = Date.now() / 1000;
  if (decoded.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/login";
  }
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded
  });
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          {
            // Public routes
          }
          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          {
            // Private routes
          }
          <Switch>
            <SecuredRoute exact path="/dashboard" component={Dashboard} />
            <SecuredRoute exact path="/addproject" component={AddProject} />
            <SecuredRoute
              exact
              path="/updateproject/:id"
              component={UpdateProject}
            />
            <SecuredRoute
              exact
              path="/projectboard/:id"
              component={ProjectBoard}
            />
            <SecuredRoute
              exact
              path="/addprojecttask/:id"
              component={AddProjectTask}
            />
            <SecuredRoute
              exact
              path="/updateprojecttask/:backlog_id/:pt_id"
              component={UpdateProjectTask}
            />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
