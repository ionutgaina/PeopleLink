import React, { useEffect, useState, useRef } from "react";
import "./style.css";
import Chat from "../Chat";
import Sidebar from "../Sidebar";
import NewRoom from "../NewRoom";
import RoomDetails from "../RoomDetails";
import GeneralSnackbar from "../GeneralSnackbar";
import ContactDetails from "../ContactDetails";
import { ContactPopulated, RoomPopulated } from "../../types";
import { roomData, usersData } from "../../constants";
import { useUser } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";
import { SocketContext } from "../../context/SocketContext";
import { Client } from "@stomp/stompjs";
import { mySocket } from "../../services/Socket";

const Room = () => {
  const [openModal, setOpenModal] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const snackbarMsg = useRef("");
  const [rooms, setRooms] = useState([] as RoomPopulated[]);
  const [users, setUsers] = useState([] as ContactPopulated[]);
  const [roomCode, setRoomCode] = useState("");
  const currentUser = useUser();
  const clientStomp = useRef<Client>();

  const navigate = useNavigate();

  useEffect(() => {
    if (currentUser.userDetails.username === "") {
      navigate("/login");
    } else {
      clientStomp.current = mySocket({
        username: currentUser.userDetails.username,
        setRooms,
        setUsers,
      });
    }

    return () => {
      if (clientStomp.current) {
        clientStomp.current.deactivate();
      }
    };
  }, [currentUser, navigate]);

  useEffect(() => {
    setRooms(roomData);
    // setUsers(usersData);
  }, []);

  const getCurrentRoom = () => rooms.find((room) => room.code === roomCode);

  const getCurrentContact = () =>
    users.find((user) => user.username === roomCode);

  const handleRoomClick = (code: string) => {
    setRoomCode(code);
  };

  const handleRoomLeave = (code: string) => {
    setRoomCode("");
    setRooms((prevRooms) => prevRooms.filter((room) => room.code !== code));
  };

  const handleModalClose = (room: RoomPopulated | null) => {
    if (room) {
      setRooms((prevRooms) => [...prevRooms, room]);
      setRoomCode(room.code);
    }
    setOpenModal(false);
  };

  return clientStomp.current ? (
    <SocketContext.Provider value={clientStomp.current}>
      <div className="room">
        <Sidebar
          onNewRoom={() => setOpenModal(true)}
          rooms={rooms}
          users={users.filter(
            (user) => user.username !== currentUser.userDetails?.username
          )}
          onRoomClick={handleRoomClick}
        />
        {roomCode ? (
          <>
            <Chat roomCode={roomCode} />
            {users.find((user) => user.username === roomCode) ? (
              <ContactDetails
                contactDetails={getCurrentContact()!}
                onUnfriend={() => {
                  console.log("unfriend");
                }}
                onBlock={() => {
                  console.log("block");
                }}
              />
            ) : (
              <RoomDetails
                roomDetails={getCurrentRoom()!}
                onRoomLeave={handleRoomLeave}
              />
            )}
          </>
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
    </SocketContext.Provider>
  ) : (
    <div>Loading...</div>
  );
};

export default Room;
