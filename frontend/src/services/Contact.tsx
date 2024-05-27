import { instance } from "./utils";

export const getContacts = async (username: string) => {
  return instance.get(`/contacts/${username}`);
};

export const addContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/add", {
    sender,
    receiver,
  });
};

export const getPendingContact = async (username: string) => {
  return instance.get(`/contact/pending/${username}`);
};

export const acceptContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/accept", {
    sender,
    receiver,
  });
};

export const rejectContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/reject", {
    sender,
    receiver,
  });
};

export const sentContacts = async (username: string) => {
  return instance.get(`/contact/sent/${username}`);
};

export const cancelContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/cancel", {
    sender,
    receiver,
  });
};

export const blockContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/block", {
    sender,
    receiver,
  });
};

export const unblockContact = async (sender: string, receiver: string) => {
  return instance.post("/contact/unblock", {
    sender,
    receiver,
  });
};
