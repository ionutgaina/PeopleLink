import { Client, StompConfig } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export function mySocket(username: string): Client {
  const stompConfig: StompConfig = {
    
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    reconnectDelay: 5000,
    onConnect: (frame) => {
      console.log("Connected: " + frame);
      stompClient.subscribe("/user/public", function (message) {
        console.log("Received public message: ", JSON.parse(message.body));
      });

      stompClient.subscribe(`/user/${username}/queue/contacts`, function (message) {
        console.log("Received private message: ", JSON.parse(message.body));
      });

      stompClient.subscribe(`/user/${username}/queue/rooms`, function (message) {
        console.log("Received room message: ", JSON.parse(message.body));
      });

      // receive my contacts and rooms
      // and subscribe to their queues
    },
    onDisconnect: (frame) => {
      console.log("Disconnected: " + frame);
    },
    onStompError: (frame) => {
      console.error("Broker reported error: " + frame.headers['message']);
      console.error("Additional details: " + frame.body);
    },
    onWebSocketError: (event) => {
      console.error("WebSocket Error: ", event);
    },
    onWebSocketClose: (event) => {
      console.log("WebSocket Closed: ", event);
    }
  };

  const stompClient = new Client(stompConfig);
  stompClient.activate();

  return stompClient;
}
