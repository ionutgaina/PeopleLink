import React, { useEffect, useState, useRef } from "react";
import "./style.css";
import Chat from "../Chat";
import Sidebar from "../Sidebar";
import NewRoom from "../NewRoom";
import RoomDetails from "../RoomDetails";
import GeneralSnackbar from "../GeneralSnackbar";
import { ContactPopulated, RoomPopulated } from "../../types";
import { roomData, usersData } from "../../constants";
import ContactDetails from "../ContactDetails";
import { useUser } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";


const Room = () => {
  const [openModal, setOpenModal] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const snackbarMsg = useRef("");
  const [rooms, setRooms] = useState([] as RoomPopulated[]);
  const [users, setUsers] = useState([] as ContactPopulated[]);
  const [roomCode, setRoomCode] = useState('');
  const currentUser = useUser();

  const navigate = useNavigate();


  useEffect(() => {
    if (currentUser.userDetails.username === "") {
      navigate("/login");
    }
  }
  , [currentUser]);

  useEffect(() => {
	setRooms(roomData);
  setUsers(usersData);
  }, []);

  const getCurrentRoom = () => {
    return rooms.find((room: RoomPopulated) => room.code === roomCode);
  };

  const getCurrentContact = () => {
    return users.find((user) => user.username === roomCode);
  }



  const handleRoomClick = (code: string) => {
    setRoomCode(code);
    setRooms((prevRooms: RoomPopulated[]) => {
      const newRooms = [...prevRooms];
      return newRooms;
    });
    setUsers((prevUsers: ContactPopulated[]) => {
      const newUsers = [...prevUsers];
      return newUsers;
    }
    );
  };

  const handleRoomLeave = (code: string) => {
    setRoomCode("");
    setRooms(rooms.filter((room: RoomPopulated) => room.code !== code));
  };

  const handleModalClose = (room: RoomPopulated | null) => {
    if (room) {
      setRooms([...rooms, room]);
      setRoomCode(room.code);
    }
    setOpenModal(false);
  };

  return (
    <div className="room">
      <Sidebar
        onNewRoom={() => setOpenModal(true)}
        rooms={rooms}
        users={users.filter((user) => user.username !== currentUser.userDetails?.username)}
        onRoomClick={handleRoomClick}
      />
      {roomCode ? (
        <React.Fragment>
          <Chat roomCode={roomCode} />
          {
            users.find((user) => user.username === roomCode) ? (
              <ContactDetails
                contactDetails={getCurrentContact()!}
                onUnfriend={() => {console.log('unfriend');}}
                onBlock={() => {console.log('block');}}
              />
            ) : (
              <RoomDetails
                roomDetails={getCurrentRoom()!}
                onRoomLeave={handleRoomLeave}
              />
            )
          }
        </React.Fragment>
      ) : (
        <div className="chat chat--no-room">
          <div className="chat__header" />
          <div className="chat__body">
            <p className="header__text">
              {rooms.length > 0
                ? "Click a room to start chatting!"
                : "Create or Join a room to start a conversation!"}
            </p>
          </div>
        </div>
      )}

      <NewRoom open={openModal} onClose={handleModalClose} />
      <GeneralSnackbar
        message={snackbarMsg.current}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />
    </div>
  );
};

export default Room;
