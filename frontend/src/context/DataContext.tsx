import { createContext, useContext } from "react";
import { ContactPopulated, RoomPopulated } from "../types";

export type DataType = {
  users: ContactPopulated[];
  rooms: RoomPopulated[];
  setUsers: (users: ContactPopulated[]) => void;
  setRooms: (rooms: RoomPopulated[]) => void;
};

export const DataContext = createContext<DataType>({
  users: [],
  rooms: [],
  setUsers: () => {},
  setRooms: () => {},
});

export const useData = () => useContext(DataContext);
