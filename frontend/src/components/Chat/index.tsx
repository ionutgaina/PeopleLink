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
import PersonIcon from "@mui/icons-material/Person";
import { useUser } from "../../context/UserContext";
import ChatHeader from "../ChatHeader";
import ChatFooter from "../ChatFooter";
import { useData } from "../../context/DataContext";
import { useStompClient } from "react-stomp-hooks";
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
      socket.subscribe(`/messages/${roomCode}`, (message) => {
        const messages = JSON.parse(message.body);
        setMessages(messages);
      });
    }

    fetchData();

    return () => {
      if (socket) {
        socket.unsubscribe(`/messages/${roomCode}`);
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
      <div className="chat__body">
        <Scrollbars className="chat__scrollbar">
          <div className="chat__main">
            {messages.map(({ content, user, createdAt }, i) => {
              const lastMessage = messages.length - 1 === i;
              return (
                <div
                  className={`chat__block ${
                    userDetails.username === user.username &&
                    "chat__block--sender"
                  } ${user.username === "Chatbot" && "chat__block--bot"}`}
                  key={i}
                >
                  <div className="message__block">
                    <Avatar>{user.username.charAt(0)}</Avatar>
                    <p
                      ref={lastMessage ? setRef : null}
                      className="chat__message"
                    >
                      <span className="header__text chat__person">
                        {userDetails.username === user.username
                          ? "You"
                          : user.username}
                      </span>
                      {content}
                    </p>
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
