import { createContext, useContext } from "react";
import { ContactPopulated, RoomPopulated } from "../types";

export type DataType = {
  users: ContactPopulated[];
  rooms: RoomPopulated[];
  setUsers: (users: ContactPopulated[]) => void;
  setRooms: (rooms: RoomPopulated[]) => void;
  roomCode: string;
  setRoomCode: (code: string) => void;
};

export const DataContext = createContext<DataType>({
  users: [],
  rooms: [],
  setUsers: () => {},
  setRooms: () => {},
  roomCode: "",
  setRoomCode: () => {},
});

export const useData = () => useContext(DataContext);
