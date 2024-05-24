import React, { useState } from "react";
import "./style.css";
import {
  Avatar,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import ConfirmationDialog from "../ConfirmationDialog";
import { useUser } from "../../context/UserContext";
import MeetingRoomIcon from "@mui/icons-material/MeetingRoom";
import DeleteIcon from "@mui/icons-material/Delete";
import GroupIcon from "@mui/icons-material/Group";
import { ContactPopulated } from "../../types";

export interface ContactDetailsProps {
  contactDetails: ContactPopulated;
  onUnfriend: (username: string) => void;
  onBlock: (username: string) => void;
}

function ContactDetails({
  contactDetails,
  onUnfriend,
  onBlock,
}: ContactDetailsProps) {
  const { username, firstName, lastName, email } = contactDetails;
  const [isOpen, setIsOpen] = useState(false);
  const [content, setContent] = useState("");
  const [type, setType] = useState("Leave");
  const { userDetails } = useUser();

  const openDialog = (type: string) => {
    setIsOpen(true);
    setType(type);
    if (type === "Unfriend") {
      setContent(
        "Are you sure you want to unfriend this user? You will not be able to revert this action."
      );
    } else {
      setContent("Are you sure you want to block this user?");
    }
  };

  const handleModalClose = async (willProceed: boolean) => {
    try {
      setIsOpen(false);
      if (willProceed) {
        if (type === "Unfriend") {
          onUnfriend(username);
        } else {
          onBlock(username);
        }
      }
    } catch (e) {
      console.error(e);
    }
  };

  const generateOptions = () => {
    const ROOM_OPTIONS = [
      {
        label: "Unfriend",
        icon: <MeetingRoomIcon />,
        action: () => openDialog("Leave"),
      },
      {
        label: "Block",
        icon: <DeleteIcon />,
        action: () => openDialog("Delete"),
      },
    ];
    return ROOM_OPTIONS.map(({ label, icon, action }, i) => {
      return (
        <ListItem key={i} button onClick={action}>
          <ListItemIcon>{icon}</ListItemIcon>
          <ListItemText primary={label} />
        </ListItem>
      );
    });
  };

  return (
    <div className="room__details">
      <Avatar className="avatar--large">
        <GroupIcon />
      </Avatar>
      <h1>{username}</h1>
      <p>{firstName + " " + lastName}</p>
      <List>{generateOptions()}</List>
      <ConfirmationDialog
        open={isOpen}
        onClose={handleModalClose}
        content={content}
      />
    </div>
  );
}

export default ContactDetails;
