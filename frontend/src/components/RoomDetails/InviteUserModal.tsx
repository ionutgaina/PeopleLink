import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from "@mui/material";

interface InviteUserModalProps {
  open: boolean;
  onClose: (username?: string) => void;
}

const InviteUserModal: React.FC<InviteUserModalProps> = ({ open, onClose }) => {
  const [username, setUsername] = useState("");

  const handleInvite = () => {
    onClose(username);
  };

  const handleCancel = () => {
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleCancel}>
      <DialogTitle>Invite User</DialogTitle>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          label="Username"
          type="text"
          fullWidth
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel} color="primary">
          Cancel
        </Button>
        <Button onClick={handleInvite} color="primary">
          Invite
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default InviteUserModal;
