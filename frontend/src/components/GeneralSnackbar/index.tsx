import { IconButton, Snackbar, SnackbarCloseReason  } from '@mui/material';
import { SyntheticEvent, MouseEvent } from 'react';
import CloseIcon from '@mui/icons-material/Close';
import React from 'react';
import './style.css';

export interface GeneralSnackbarProps {
	message: string;
	open: boolean;
	onClose: () => void;
}

function GeneralSnackbar({ message, open, onClose }: GeneralSnackbarProps) {
	const handleClose = (
		event: Event | SyntheticEvent<Element, Event> | MouseEvent<Element, MouseEvent>,
		reason?: SnackbarCloseReason
	  ) => {
		if (reason === 'clickaway') {
		  return;
		}
		onClose();
	  };

	return (
		<div className="general__snackbar">
			<Snackbar
				anchorOrigin={{
					vertical: 'bottom',
					horizontal: 'left'
				}}
				open={open}
				autoHideDuration={6000}
				onClose={handleClose}
				message={message}
				action={
					<IconButton size="small" aria-label="close" color="inherit" onClick={handleClose}>
						<CloseIcon fontSize="small" />
					</IconButton>
				}
			/>
		</div>
	);
}

export default GeneralSnackbar;
