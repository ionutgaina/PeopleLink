import { Avatar, IconButton, Menu, MenuItem } from "@material-ui/core";
import React from "react";
import ChatIcon from "@material-ui/icons/Chat";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import PersonIcon from "@material-ui/icons/Person";
import { USER_INITIAL_VALUE } from "../../constants";
import { useUser } from "../../context/UserContext";
import "./style.css";
import { useHistory } from "react-router-dom";

export interface SidebarHeaderProps {
  history: ReturnType<typeof useHistory>;
  onNewRoom: () => void;
}

function SidebarHeader({ history, onNewRoom }: SidebarHeaderProps) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const { userDetails, setUserDetails } = useUser();

  const onLogout = () => {
    setAnchorEl(null);
    localStorage.clear();
    setUserDetails(USER_INITIAL_VALUE);
    history.push("/login");
  };
  return (
    <div className="sidebar__header">
      <div className="sidebar__headerAvatar">
        <Avatar>{userDetails.username.charAt(0)}</Avatar>
        <p className="header__text">{userDetails.username}</p>
      </div>
      <div className="sidebar__headerIcons">
        <IconButton onClick={onNewRoom}>
          <ChatIcon />
        </IconButton>
        <IconButton onClick={(e) => setAnchorEl(e.currentTarget)}>
          <MoreVertIcon />
        </IconButton>
        <Menu
          id="sidebar-menu"
          anchorEl={anchorEl}
          keepMounted
          open={Boolean(anchorEl)}
          onClose={() => setAnchorEl(null)}
        >
          <MenuItem onClick={onLogout}>Logout</MenuItem>
        </Menu>
      </div>
    </div>
  );
}

export default SidebarHeader;
