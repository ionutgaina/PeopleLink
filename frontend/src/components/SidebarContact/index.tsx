import { Avatar } from "@material-ui/core";
import React from "react";
import GroupIcon from "@material-ui/icons/Group";
import { ContactPopulated, User } from "../../types";
import "./style.css";

export interface SidebarRoomProps {
  user: ContactPopulated;
  userDetails: User;
  onRoomClick: (code: string) => void;
}

const SidebarContact = ({
  user,
  userDetails,
  onRoomClick,
}: SidebarRoomProps) => {
  console.log("userDetails", userDetails);
  return (
    <div className="sidebarRoom" onClick={() => onRoomClick(user.username)}>
      <Avatar>
        <GroupIcon />
      </Avatar>
      <React.Fragment>
        <div className={`sidebarRoom__details`}>
          <h2>{user.username}</h2>
        </div>
      </React.Fragment>
    </div>
  );
};

export default SidebarContact;
