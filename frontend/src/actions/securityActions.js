import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "../actions/types";
import { setJwt } from "../securityUtils/setJwt";
import jwt_decode from "jwt-decode";

export const createUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const login = loginRequest => async dispatch => {
  try {
    const res = await axios.post("/api/users/login", loginRequest);
    const { token } = res.data;
    localStorage.setItem("jwt", token);
    setJwt(token);
    const decoded = jwt_decode(token);
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwt");
  setJwt(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
