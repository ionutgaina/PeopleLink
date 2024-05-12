import React, { useEffect, useState, useRef } from "react";
import "./style.css";
import Chat from "../Chat";
import Sidebar from "../Sidebar";
import NewRoom from "../NewRoom";
import RoomDetails from "../RoomDetails";
import GeneralSnackbar from "../GeneralSnackbar";
import { useHistory } from "react-router-dom";
import { RoomPopulated } from "../../types";
import { roomData } from "../../constants";

export interface RoomProps {
  history: ReturnType<typeof useHistory>;
}

const Room = ({ history }: RoomProps) => {
  const [openModal, setOpenModal] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const snackbarMsg = useRef("");
  const [rooms, setRooms] = useState([] as RoomPopulated[]);
  const [roomCode, setRoomCode] = useState('');

  useEffect(() => {
	setRooms(roomData);
  }, []);

  useEffect(() => {}, []);
  const getCurrentRoom = () => {
    return rooms.find((room: RoomPopulated) => room.code === roomCode);
  };

  const handleRoomClick = (code: string) => {
    setRoomCode(code);
    setRooms((prevRooms: RoomPopulated[]) => {
      const newRooms = [...prevRooms];
      return newRooms;
    });
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
        history={history}
        onRoomClick={handleRoomClick}
      />
      {roomCode ? (
        <React.Fragment>
          <Chat roomCode={roomCode} />
          <RoomDetails
            roomDetails={getCurrentRoom()!}
            onRoomLeave={handleRoomLeave}
          />
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
