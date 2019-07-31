import { SET_CURRENT_USER } from "../actions/types";

const initialState = {
  user: {},
  validToken: false
};

const booleanActionPayload = payload => {
  if (payload) return true;
  else return false;
};

export default function(state = initialState, action) {
  switch (action.type) {
    default:
      return state;
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload),
        user: action.payload
      };
  }
}
