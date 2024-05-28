import React, { useState } from "react";
import {
  Button,
  ButtonGroup,
  Dialog,
  DialogTitle,
  DialogContent,
} from "@mui/material";
import "./style.css";
import { RoomPopulated } from "../../types";
import { useData } from "../../context/DataContext";
import { addContact, getContacts } from "../../services/Contact";
import { useUser } from "../../context/UserContext";
import Swal from "sweetalert2";

export interface NewRoomProps {
  open: boolean;
  onClose: (value: null | RoomPopulated) => void;
}

function NewRoom({ open, onClose }: NewRoomProps) {
  const [isNew, setisNew] = useState(1);
  const [description, setDescription] = useState("");
  const [roomCode, setRoomCode] = useState("");
  const { users, setUsers, rooms, setRooms } = useData();
  const currentUser = useUser();

  const handleClose = (val: null | RoomPopulated) => {
    onClose(val);
  };

  const proceed = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();

    if (isNew === 1) {
      // create room
      // const res = await createRoom();
      // if (res) {
      //   handleClose(res);
      // }
    }

    if (isNew === 0) {
      // join room
      // const res = await joinRoom();
      // if (res) {
      //   handleClose(res);
      // }
    }

    if (isNew === -1) {
      // add contact
      handleClose(null);
      try {
        const response = await addContact(
          currentUser.userDetails.username,
          roomCode
        );
        // success message
        Swal.fire({
          title: response.data,
          icon: "success",
          showConfirmButton: true,
        }).then(async () => {
          setUsers(await getContacts(currentUser.userDetails.username));
        });
      } catch (e: any) {
        // Error message
        Swal.fire({
          title: e.response.data,
          icon: "error",
          showConfirmButton: true,
        });
      }
    }
  };

  return (
    <Dialog
      onClose={() => handleClose(null)}
      aria-labelledby="new-room-dialog"
      open={open}
      className="newRoom"
    >
      <DialogTitle id="new-room-dialog">New Room</DialogTitle>
      <DialogContent className="newRoom__content">
        <form>
          <ButtonGroup className="newRoom__type" color="primary">
            <Button
              onClick={() => setisNew(1)}
              className={`newRoom__button ${
                isNew === 1 && "newRoom__button--selected"
              }`}
            >
              Create Room
            </Button>
            <Button
              onClick={() => setisNew(0)}
              className={`newRoom__button ${
                isNew === 0 && "newRoom__button--selected"
              }`}
            >
              Join Room
            </Button>
            <Button
              onClick={() => setisNew(-1)}
              className={`newRoom__button ${
                isNew === -1 && "newRoom__button--selected"
              }`}
            >
              Add Contact
            </Button>
          </ButtonGroup>

          {isNew === 1 ? (
            <textarea
              rows={3}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Room Description"
            />
          ) : (
            <input
              value={roomCode}
              onChange={(e) => setRoomCode(e.target.value)}
              type="text"
              placeholder={isNew === 0 ? "Room Code" : "Contact Username"}
            />
          )}

          <Button
            onClick={proceed}
            type="submit"
            className="secondary"
            variant="contained"
            color="primary"
            size="large"
          >
            Proceed
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default NewRoom;
