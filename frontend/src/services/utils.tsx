import axios from "axios";

export const instance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const getRoomUsers = async (sender: string, receiver: string) => {
  const roomcode = [sender, receiver].sort().join("");
  console.log(roomcode);
  return roomcode;
};
