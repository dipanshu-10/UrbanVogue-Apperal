import axios from "axios";

const API_URL = "http://localhost:8090/auth";

export const registerUser = async (data) => {
  return axios.post(`${API_URL}/register`, data);
};

export const loginUser = async (data) => {
  return axios.post(`${API_URL}/login`, data);
};