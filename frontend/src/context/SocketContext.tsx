import { createContext, useContext, Context } from "react";
import { mySocket } from "../services/Socket";

export const SocketContext: Context<mySocket> = createContext(new mySocket());

export const useSocket = () => useContext(SocketContext);
