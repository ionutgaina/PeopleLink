import React from "react";
import "./style.css";
import SidebarRoom from "../SidebarRooms";
import { useNavigate } from "react-router-dom";
import { Scrollbars } from 'react-custom-scrollbars-2';
import { ContactPopulated, RoomPopulated } from "../../types";
import SidebarHeader from "../SidebarHeader";
import { useUser } from "../../context/UserContext";
import SidebarContact from "../SidebarContact";

export interface SidebarProps {
  rooms: RoomPopulated[];
  users: ContactPopulated[];
  onNewRoom: () => void;
  onRoomClick: (code: string) => void;
}

const Sidebar = ({ onNewRoom, rooms, users, onRoomClick }: SidebarProps) => {
  const { userDetails } = useUser();

  const navigate = useNavigate();
  console.log("rooms", rooms);
  return (
    <div className="sidebar">
      <SidebarHeader onNewRoom={onNewRoom} />

      <div className="sidebar__rooms">
        <Scrollbars className="sidebar__scrollbar">
            <p className="sidebar_text">Rooms</p>
            {rooms.map((room: RoomPopulated, i: number) => (
              <SidebarRoom
                key={i}
                room={room}
                userDetails={userDetails}
                onRoomClick={onRoomClick}
              />
            ))}
            <p className="sidebar_text">Contacts</p>
            {users.map((user: ContactPopulated, i: number) => (
              <SidebarContact
                key={i}
                user={user}
                userDetails={userDetails}
                onRoomClick={onRoomClick}
              />
            ))}
        </Scrollbars>
      </div>
    </div>
  );
};

export default Sidebar;
