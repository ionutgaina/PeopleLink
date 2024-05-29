import React, { useState } from "react";
import { IconButton } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import AttachFileIcon from "@mui/icons-material/AttachFile";
import CloseIcon from "@mui/icons-material/Close";
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
  const [file, setFile] = useState<File | null>(null);
  const [filePreview, setFilePreview] = useState<string | null>(null);

  const sendMessageChat = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    if (input || file) {
      try {
        await sendMessage(roomCode, loggedInUser.username, input, file);
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
      }
      setInput("");
      removeFile();
    }
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const selectedFile = e.target.files[0];
      if (selectedFile.type.startsWith("image/")) {
        setFile(selectedFile);
        const reader = new FileReader();
        reader.onload = () => {
          setFilePreview(reader.result as string);
        };
        reader.readAsDataURL(selectedFile);
      } else {
        Swal.fire({
          title: "Only image files are allowed",
          icon: "warning",
          timer: 2500,
          showConfirmButton: false,
        });
      }
    }
  };

  const removeFile = () => {
    setFile(null);
    setFilePreview(null);
    const fileInput = document.getElementById("file-input") as HTMLInputElement;
    if (fileInput) {
      fileInput.value = "";
    }
  };

  return (
    <div className="chat__footer">
      {filePreview && (
        <div className="file-preview">
          <img
            src={filePreview}
            alt="Selected file"
            className="preview-image"
          />
          <IconButton className="close-button" onClick={removeFile}>
            <CloseIcon />
          </IconButton>
        </div>
      )}
      <form>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          type="text"
          placeholder="Start typing..."
        />
        <input
          type="file"
          accept="image/*"
          onChange={handleFileChange}
          style={{ display: "none" }}
          id="file-input"
        />
        <label htmlFor="file-input">
          <IconButton component="span">
            <AttachFileIcon />
          </IconButton>
        </label>
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
