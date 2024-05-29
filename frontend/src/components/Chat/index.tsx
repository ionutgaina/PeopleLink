import { Avatar } from "@mui/material";
import { useState, useEffect, useCallback } from "react";
import { Scrollbars } from "react-custom-scrollbars-2";
import "./style.css";
import { MessagePopulated } from "../../types";
import {
  parseISO,
  differenceInCalendarDays,
  format,
  formatDistanceToNow,
} from "date-fns";
import { useUser } from "../../context/UserContext";
import ChatHeader from "../ChatHeader";
import ChatFooter from "../ChatFooter";
import { getMessages } from "../../services/Messages";
import { useSocket } from "../../context/SocketContext";

export interface ChatProps {
  roomCode: string;
}

const Chat = ({ roomCode }: ChatProps) => {
  const [messages, setMessages] = useState([] as MessagePopulated[]);
  const { userDetails } = useUser();
  const socket = useSocket();
  const setRef = useCallback((node: HTMLElement | null) => {
    if (node) {
      node.scrollIntoView({ behavior: "smooth" });
    }
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      const messages = await getMessages(roomCode, userDetails.username);
      setMessages(messages);
    };

    if (socket) {
      socket.subscribe(
        `/rooms/${roomCode}`,
        async (message) => {
          console.log("Message received: ", message);
          try {
            const messages = await getMessages(roomCode, userDetails.username);
            console.log("Messages: ", messages);
            setMessages(messages);
          } catch (error) {
            // console.log("Error: ", error);
          }
        },
        { id: "chat" }
      );
    }

    fetchData();

    return () => {
      if (socket) {
        socket.unsubscribe("chat");
      }
    };
  }, [socket, roomCode, userDetails.username]);

  const formatDate = (date: Date) => {
    return differenceInCalendarDays(new Date(), date) > 2
      ? format(date, "EEE MMM d h:m b")
      : formatDistanceToNow(date, { addSuffix: true });
  };

  return (
    <div className="chat">
      <ChatHeader
        roomCode={roomCode}
        messages={messages}
        formatDate={formatDate}
      />
      <div
        className="chat__body"
        style={{
          paddingTop: "10px",
        }}
      >
        <Scrollbars className="chat__scrollbar">
          <div className="chat__main">
            {messages.map(({ content, user, createdAt, mediaUrl }: any, i) => {
              const lastMessage = messages.length - 1 === i;
              return (
                <div
                  className={`chat__block ${
                    userDetails.username === user && "chat__block--sender"
                  }`}
                  key={i}
                >
                  <div className="message__block">
                    <Avatar>{user.charAt(0)}</Avatar>
                    <div
                      ref={lastMessage ? setRef : null}
                      className="chat__message"
                    >
                      <span className="header__text chat__person">
                        {userDetails.username === user ? "You" : user}
                      </span>
                      {/* Conditionally render the image if mediaUrl is present */}
                      {mediaUrl && (
                        <a href={mediaUrl} target="_blank" rel="noopener noreferrer">
                          <img
                            src={mediaUrl}
                            alt="media"
                            className="chat__image chat__person"
                          />
                        </a>
                      )}
                      {content}
                    </div>
                  </div>
                  <span className="chat__timestamp">
                    {formatDate(parseISO(createdAt))}
                  </span>
                </div>
              );
            })}
          </div>
        </Scrollbars>
      </div>
      <ChatFooter roomCode={roomCode} loggedInUser={userDetails} />
    </div>
  );
};

export default Chat;
