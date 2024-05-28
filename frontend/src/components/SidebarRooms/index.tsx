import { Avatar } from "@mui/material";
import React from "react";
import GroupIcon from "@mui/icons-material/Group";
import { RoomPopulated, User } from "../../types";
import "./style.css";

export interface SidebarRoomProps {
  room: RoomPopulated;
  userDetails: User;
  onRoomClick: (code: string) => void;
}

const SidebarRoom = ({ room, userDetails, onRoomClick }: SidebarRoomProps) => {
  console.log("userDetails", userDetails);
  console.log("room", room);
  return (
    <div className="sidebarRoom" onClick={() => onRoomClick(room.code)}>
      <Avatar>
        <GroupIcon />
      </Avatar>
        <React.Fragment>
        <div className={`sidebarRoom__details`}>
          <h2>{room.code}</h2>
          <p>{room.description}</p>
        </div>
        </React.Fragment>
    </div>
  );
};

export default SidebarRoom;
