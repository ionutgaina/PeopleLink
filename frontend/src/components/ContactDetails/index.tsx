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
import {
  acceptContact,
  cancelContact,
  rejectContact,
} from "../../services/Contact";
import Swal from "sweetalert2";
import { AxiosResponse } from "axios";
import { th } from "date-fns/locale";

export interface ContactDetailsProps {
  contactDetails: ContactPopulated;
}

function ContactDetails({ contactDetails }: ContactDetailsProps) {
  const { username, status, sender } = contactDetails;
  const [isOpen, setIsOpen] = useState(false);
  const [content, setContent] = useState("");
  const [type, setType] = useState("Unfriend");
  const { userDetails } = useUser();

  const openDialog = (type: string) => {
    setIsOpen(true);
    setType(type);

    if (type === "Block") {
      setContent(`Are you sure you want to block ${username}?`);
    } else if (type === "Accept") {
      setContent(`Accept friend request from ${username}?`);
    } else if (type === "Decline") {
      setContent(`Decline friend request from ${username}?`);
    } else if (type === "Cancel") {
      setContent(`Cancel friend request to ${username}?`);
    }
  };

  const handleModalClose = async (willProceed: boolean) => {
    let response: AxiosResponse;

    
    try {
      setIsOpen(false);

      if (willProceed) {
        if (type === "Accept") {
          response = await acceptContact(sender, userDetails.username);
        } else if (type === "Decline") {
          response = await rejectContact(sender, userDetails.username);
        } else if (type === "Cancel") {
          response = await cancelContact(sender, username);
        } else {
          throw new Error("Invalid action");
        }
        Swal.fire({
          title: response.data,
          icon: "success",
          showConfirmButton: true,
        });
      }
    } catch (e: any) {
      if (e.response) {
        Swal.fire({
          title: e.response.data,
          icon: "error",
          timer: 2500,
          showConfirmButton: false,
        });
      }
    }
  };

  const generateOptions = () => {
    const ROOM_OPTIONS_PENDING = [
      {
        label: "Accept",
        icon: <MeetingRoomIcon />,
        action: () => openDialog("Accept"),
      },
      {
        label: "Decline",
        icon: <DeleteIcon />,
        action: () => openDialog("Decline"),
      },
    ];

    const ROOM_OPTIONS_SENT = [
      {
        label: "Cancel",
        icon: <DeleteIcon />,
        action: () => openDialog("Cancel"),
      },
    ];

    if (status === "PENDING" && sender === userDetails.username) {
      return ROOM_OPTIONS_SENT.map(({ label, icon, action }, i) => {
        return (
          <ListItem key={i} button onClick={action}>
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={label} />
          </ListItem>
        );
      });
    }

    if (status === "PENDING" && sender !== userDetails.username) {
      return ROOM_OPTIONS_PENDING.map(({ label, icon, action }, i) => {
        return (
          <ListItem key={i} button onClick={action}>
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={label} />
          </ListItem>
        );
      });
    }

    return null;
  };

  return (
    <div className="room__details">
      <Avatar className="avatar--large">
        <GroupIcon />
      </Avatar>
      <h1>{username}</h1>
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
