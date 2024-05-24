import { Avatar, IconButton, Menu, MenuItem } from "@mui/material";
import React from "react";
import ChatIcon from "@mui/icons-material/Chat";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import PersonIcon from "@mui/icons-material/Person";
import { USER_INITIAL_VALUE } from "../../constants";
import { useUser } from "../../context/UserContext";
import "./style.css";
import { useNavigate } from "react-router-dom";

export interface SidebarHeaderProps {
  onNewRoom: () => void;
}

function SidebarHeader({ onNewRoom }: SidebarHeaderProps) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const { userDetails, setUserDetails } = useUser();

  const navigate = useNavigate();

  const onLogout = () => {
    setAnchorEl(null);
    localStorage.clear();
    setUserDetails(USER_INITIAL_VALUE);
    navigate("/login");
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
