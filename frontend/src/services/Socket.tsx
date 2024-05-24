import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export class mySocket {
  private socket = new SockJS(process.env.REACT_APP_WEBSOCKET_URL!);
  private stompClient = Stomp.over(this.socket);

  public connect(username: string) {
    this.stompClient.connect(
      {},
      () => {
        this.stompClient.subscribe(`/user/public`, (data) => {
          console.log("Received data", JSON.parse(data.body));
        });

        this.stompClient.subscribe(
          `/user/${username}/queue/contacts`,
          (data) => {
            console.log("Received data", JSON.parse(data.body));
          }
        );
      },
      (error: any) => {
        console.log("Error connecting to websocket", error);
      }
    );
  }

  public disconnect() {
    this.socket.close();
  }
}
