import React, { useState } from "react";
import "./style.css";
import {
  Avatar,
  List,
  ListItem,
  ListItemAvatar,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import ConfirmationDialog from "../ConfirmationDialog";
import { useUser } from "../../context/UserContext";
import PhotoIcon from "@mui/icons-material/Photo";
import MeetingRoomIcon from "@mui/icons-material/MeetingRoom";
import DeleteIcon from "@mui/icons-material/Delete";
import PersonIcon from "@mui/icons-material/Person";
import GroupIcon from "@mui/icons-material/Group";
import { RoomPopulated, User } from "../../types";
import { AxiosResponse } from "axios";
import Swal from "sweetalert2";
import { deleteRoom, leaveRoom } from "../../services/Group";

export interface RoomDetailsProps {
  roomDetails: RoomPopulated;
}

function RoomDetails({ roomDetails }: RoomDetailsProps) {
  const { code, description, users } = roomDetails;
  const [isOpen, setIsOpen] = useState(false);
  const [content, setContent] = useState("");
  const [type, setType] = useState("Leave");
  const { userDetails } = useUser();

  const openDialog = (type: string) => {
    setIsOpen(true);
    setType(type);

    if (type === "Leave") {
      setContent(
        "You will not be able to receive messeges sent in this room anymore. Other users in the room will also be notified when you leave."
      );
    } else {
      setContent("You will not be able to revert this deletion.");
    }
  }
  

  const handleModalClose = async (willProceed: boolean) => {
    let response: AxiosResponse;

    try {
      setIsOpen(false);
      if (willProceed) {
        if (type === "Leave") {
          response = await leaveRoom(userDetails.username, code);
        } else if (type === "Delete") {
          response = await deleteRoom(userDetails.username, code);
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
    const ROOM_OPTIONS = [
      {
        label: "Leave Group",
        icon: <MeetingRoomIcon />,
        adminOnly: false,
        action: () => openDialog("Leave"),
      },
      {
        label: "Delete Group",
        icon: <DeleteIcon />,
        adminOnly: true,
        action: () => openDialog("Delete"),
      },
    ];
    return ROOM_OPTIONS.map(({ label, icon, adminOnly, action }, i) => {
      return (
        (!adminOnly ||
          (adminOnly && roomDetails.owner === userDetails.username)) && (
          <ListItem key={i} button onClick={action}>
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={label} />
          </ListItem>
        )
      );
    });
  };

  const generateUserList = () => {
    console.log("users", users);
    return users.map((username: any) => {
      return (
        <ListItem key={username}>
          <ListItemAvatar>
            <Avatar>{username.charAt(0)}</Avatar>
          </ListItemAvatar>
          <ListItemText primary={username} />
        </ListItem>
      );
    });
  };

  return (
    <div className="room__details">
      <Avatar className="avatar--large">
        <GroupIcon />
      </Avatar>
      <h1>{code}</h1>
      <p>{description}</p>
      <List>{generateOptions()}</List>
      <List>{generateUserList()}</List>
      <ConfirmationDialog
        open={isOpen}
        onClose={handleModalClose}
        content={content}
      />
    </div>
  );
}

export default RoomDetails;
