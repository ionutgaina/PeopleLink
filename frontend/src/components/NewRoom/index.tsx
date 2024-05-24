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

export interface NewRoomProps {
  open: boolean;
  onClose: (value: null | RoomPopulated) => void;
}

function NewRoom({ open, onClose }: NewRoomProps) {
  const [isNew, setisNew] = useState(1);

  const handleClose = (val: null | RoomPopulated) => {
    onClose(val);
  };

  const proceed = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    handleClose(null);
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
            <textarea rows={3} placeholder="Room Description" />
          ) : (
            <input type="text" placeholder="Name" />
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
