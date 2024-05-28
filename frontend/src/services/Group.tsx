import { instance } from "./utils";

export const getGroups = async (username: string) => {
  const response = await instance.get(`/groups/user/${username}`);

  let groups = response.data;

  let rooms = groups.map((group: any) => {
    return {
      code: group.groupName,
      description: group.description,
      users: group.members,
      owner: group.ownerName,
    };
  });

  return rooms;
};

export const createGroup = async (
  ownerName: string,
  groupName: string,
  description = ""
) => {
  return instance.post("/groups/create", {
    ownerName,
    groupName,
    description,
  });
};

export const joinRoom = async (userName: string, roomCode: string) => {
  return instance.post(`/groups/join`, {
    userName,
    groupName: roomCode,
  });
};

export const leaveRoom = async (userName: string, groupName: string) => {
  return instance.post(`/groups/leave`, {
    groupName,
    userName,
  });
};

export const deleteRoom = async (adminName: string, groupName: string) => {
  return instance.post(`/groups/remove`, {
    adminName,
    groupName,
  });
};

export const inviteUser = async (adminName: string, groupName: string, userName: string) => {
  return instance.post(`/groups/invite`, {
    adminName,
    groupName,
    userName,
  });
}