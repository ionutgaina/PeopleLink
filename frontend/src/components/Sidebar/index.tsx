import React from "react";
import "./style.css";
import SidebarRoom from "../SidebarRooms";
import { useHistory } from "react-router-dom";
import Scrollbar from "react-scrollbars-custom";
import { ContactPopulated, RoomPopulated } from "../../types";
import SidebarHeader from "../SidebarHeader";
import { useUser } from "../../context/UserContext";
import SidebarContact from "../SidebarContact";

export interface SidebarProps {
  history: ReturnType<typeof useHistory>;
  rooms: RoomPopulated[];
  users: ContactPopulated[];
  onNewRoom: () => void;
  onRoomClick: (code: string) => void;
}

const Sidebar = ({
  onNewRoom,
  rooms,
  users,
  history,
  onRoomClick,
}: SidebarProps) => {
  const { userDetails } = useUser();

  console.log("rooms", rooms);
  return (
    <div className="sidebar">
      <SidebarHeader onNewRoom={onNewRoom} history={history} />

      <div className="sidebar__rooms">
        <Scrollbar className="sidebar__scrollbar">
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
        </Scrollbar>
      </div>
    </div>
  );
};

export default Sidebar;
