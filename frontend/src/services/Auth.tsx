import { instance } from "./utils";

export const login = async (username: string, password: string) => {
  return instance.post("/user/login", {
    username,
    password,
  });
};

export const register = async (username: string, password: string) => {
  return instance.post("/user/register", {
    username,
    password,
  });
};
