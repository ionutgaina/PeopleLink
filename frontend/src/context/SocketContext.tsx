import { createContext, useContext, Context } from "react";
import { Client } from "@stomp/stompjs";

export const SocketContext: Context<Client> = createContext({} as Client);

export let useSocket = () => useContext(SocketContext);
