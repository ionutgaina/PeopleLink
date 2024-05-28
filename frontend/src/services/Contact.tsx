import { instance } from "./utils";

export const getContacts = async (username: string) => {
  const response = await instance.get(`/contacts/${username}`);

  let contacts = response.data;

  let users = contacts.map((contact: any) => {
    return {
      username: username === contact.sender ? contact.receiver : contact.sender,
      sender: contact.sender,
      status: contact.status,
      roomCode: contact.roomCode,
    };
  });

  return users;
};

export const addContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/", {
    sender,
    receiver,
  });
};

export const getPendingContact = async (username: string) => {
  return instance.get(`/contacts/pending/${username}`);
};

export const acceptContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/accept", {
    sender,
    receiver,
  });
};

export const rejectContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/reject", {
    sender,
    receiver,
  });
};

export const sentContacts = async (username: string) => {
  return instance.get(`/contacts/sent/${username}`);
};

export const cancelContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/cancel", {
    sender,
    receiver,
  });
};

export const blockContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/block", {
    sender,
    receiver,
  });
};

export const unblockContact = async (sender: string, receiver: string) => {
  return instance.post("/contacts/unblock", {
    sender,
    receiver,
  });
};
