import { Avatar } from "@material-ui/core";
import { useState, useEffect, useCallback } from "react";
import Scrollbar from "react-scrollbars-custom";
import "./style.css";
import { MessagePopulated } from "../../types";
import {
  parseISO,
  differenceInCalendarDays,
  format,
  formatDistanceToNow,
} from "date-fns";
import PersonIcon from "@material-ui/icons/Person";
import { useUser } from "../../context/UserContext";
import ChatHeader from "../ChatHeader";
import ChatFooter from "../ChatFooter";
import { messageData } from "../../constants";

export interface ChatProps {
  roomCode: string;
}

const Chat = ({ roomCode }: ChatProps) => {
  const [messages, setMessages] = useState([] as MessagePopulated[]);
  // const chatSocket = useChat();
  const { userDetails } = useUser();
  const setRef = useCallback((node) => {
    if (node) {
      node.scrollIntoView({ smooth: true });
    }
  }, []);

  useEffect(() => {
    setMessages(messageData);
  }, [roomCode]);

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
        <Scrollbar className="chat__scrollbar">
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
                    <Avatar>
                      {user.firstName && user.lastName ? (
                        user.firstName.charAt(0) + user.lastName.charAt(0)
                      ) : (
                        <PersonIcon />
                      )}
                    </Avatar>
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
        </Scrollbar>
      </div>
      <ChatFooter roomCode={roomCode} loggedInUser={userDetails} />
    </div>
  );
};

export default Chat;
