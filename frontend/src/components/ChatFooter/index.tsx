import { IconButton } from "@mui/material";
import React, { useState } from "react";
import SendIcon from "@mui/icons-material/Send";
import { User } from "../../types";
import "./style.css";
import { sendMessage } from "../../services/Messages";
import Swal from "sweetalert2";

export interface ChatFooterProps {
  roomCode: string;
  loggedInUser: User;
}
function ChatFooter({ roomCode, loggedInUser }: ChatFooterProps) {
  const [input, setInput] = useState("");
  const sendMessageChat = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    if (input) {
      try {
        await sendMessage(roomCode, loggedInUser.username, input);
      } catch (error: any) {
        console.error("Error sending message: ", error);
        if (error.response) {
          Swal.fire({
            title: error.response.data,
            icon: "error",
            timer: 2500,
            showConfirmButton: false,
          });
        }
      } finally {
        setInput("");
      }
    }
  };
  return (
    <div className="chat__footer">
      <form>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          type="text"
          placeholder="Start typing.."
        />
        <button onClick={sendMessageChat} type="submit">
          Send
        </button>
      </form>
      <IconButton onClick={sendMessageChat}>
        <SendIcon />
      </IconButton>
    </div>
  );
}

export default ChatFooter;
