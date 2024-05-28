import { instance } from "./utils"

export const getMessages = async (roomCode: string) => {
  const response = await instance.get(`/messages/${roomCode}`);
  return response.data;
}