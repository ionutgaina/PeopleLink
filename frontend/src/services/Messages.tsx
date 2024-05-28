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
  text: string
) => {
  return instance.post(`/messages/send`, {
    roomCode,
    senderName,
    text,
  });
};

export const sendFile = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file);

  return instance.post(`/messages/sendFile`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
