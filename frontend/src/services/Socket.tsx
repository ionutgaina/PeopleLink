import { Client, StompConfig } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ContactPopulated, RoomPopulated } from "../types";
import { getContacts } from "./Contact";
import { getGroups } from "./Group";

interface mySocketProps {
  username: string;
  setRooms: (rooms: RoomPopulated[]) => void;
  setUsers: (users: ContactPopulated[]) => void;
}

export function mySocket({ username, setRooms, setUsers }: mySocketProps) {
  const stompConfig: StompConfig = {
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    reconnectDelay: 5000,
    onConnect: async (frame) => {
      console.log("Connected: " + frame);
      stompClient.subscribe("/user/public", function (message) {
        console.log("Received public message: ", JSON.parse(message.body));
      });

      stompClient.subscribe(
        `/user/${username}/queue/contacts`,
        async function (message) {
          console.log("Received private message: ", JSON.parse(message.body));
          let users: ContactPopulated[] = [];
          try {
            users = await getContacts(username);
            if (users) setUsers(users);
            else setUsers([]);
          } catch (e: any) {
            setUsers([]);
            users = [];
          }
        }
      );

      stompClient.subscribe(
        `/user/${username}/queue/rooms`,
        async function (message) {
          console.log("Received room message: ", JSON.parse(message.body));
          let rooms: RoomPopulated[] = [];
          try {
            rooms = await getGroups(username);
            if (rooms) setRooms(rooms);
            else setRooms([]);
          } catch (e: any) {
            setRooms([]);
            rooms = [];
          }
        }
      );

      let users: ContactPopulated[] = [];
      try {
        users = await getContacts(username);
        if (users) setUsers(users);
        else setUsers([]);
      } catch (e: any) {
        setUsers([]);
        users = [];
      }

      let rooms: RoomPopulated[] = [];
      try {
        rooms = await getGroups(username);
        if (rooms) setRooms(rooms);
        else setRooms([]);
      } catch (e: any) {
        setRooms([]);
        rooms = [];
      }
    },
    onDisconnect: (frame) => {
      console.log("Disconnected: " + frame);
    },
    onStompError: (frame) => {
      console.error("Broker reported error: " + frame.headers["message"]);
      console.error("Additional details: " + frame.body);
    },
    onWebSocketError: (event) => {
      console.error("WebSocket Error: ", event);
    },
    onWebSocketClose: (event) => {
      console.log("WebSocket Closed: ", event);
    },
  };

  const stompClient = new Client(stompConfig);
  stompClient.activate();

  return stompClient;
}