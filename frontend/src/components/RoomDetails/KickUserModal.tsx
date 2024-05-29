import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from "@mui/material";

interface KickUserModalProps {
  open: boolean;
  onClose: (username?: string) => void;
}

const KickUserModal: React.FC<KickUserModalProps> = ({ open, onClose }) => {
  const [username, setUsername] = useState("");

  const handleKick = () => {
    onClose(username);
  };

  const handleCancel = () => {
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleCancel}>
      <DialogTitle>Kick User</DialogTitle>
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
        <Button onClick={handleKick} color="primary">
          Kick
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default KickUserModal;
