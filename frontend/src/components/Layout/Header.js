import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";

class Header extends Component {
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  render() {
    let userIfAuthenticated;
    let { user, validToken } = this.props.security;
    if (validToken && user) {
      userIfAuthenticated = (
        <div className="collapse navbar-collapse" id="mobile-nav">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/dashboard">
                Dashboard
              </Link>
            </li>
          </ul>

          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to="/dashboard" className="nav-link ">
                <i className="fas fa-user-circle mr-1"> {user.fullname}</i>
              </Link>
            </li>
            <li className="nav-item">
              <Link
                onClick={this.logout.bind(this)}
                className="nav-link"
                to="/logout"
              >
                Logout
              </Link>
            </li>
          </ul>
        </div>
      );
    } else {
      userIfAuthenticated = (
        <div>
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to="/register" className="nav-link">
                Register
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/login" className="nav-link">
                Login
              </Link>
            </li>
          </ul>
        </div>
      );
    }
    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link className="navbar-brand" to="/">
            Project Management Tool
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {userIfAuthenticated}
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  security: PropTypes.object.isRequired,
  logout: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout }
)(Header);
