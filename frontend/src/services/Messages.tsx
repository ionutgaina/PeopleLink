import { instance } from "./utils";

export const getMessages = async (roomCode: string, userName: string) => {
  const response = await instance.post(`/messages/`, {
    roomCode,
    userName,
  });

  let messages = response.data;
  return messages.map((message: any) => ({
    content: message.text,
    user: message.senderName,
    createdAt: message.timestamp,
  }));
};

export const sendMessage = async (
  roomCode: string,
  senderName: string,
  text: string,
  file: File | null
) => {
  const formData = new FormData();
  if (file) formData.append("file", file);

  formData.append("roomCode", roomCode);
  formData.append("senderName", senderName);
  formData.append("text", text);

  return instance.post(`/messages/send`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
