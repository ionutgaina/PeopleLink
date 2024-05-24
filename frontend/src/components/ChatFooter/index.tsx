import { IconButton } from '@mui/material';
import React, { useState } from 'react';
import SendIcon from '@mui/icons-material/Send';
import { User } from '../../types';
import './style.css';
import { messageData } from '../../constants';

export interface ChatFooterProps {
	roomCode: string;
	loggedInUser: User;
}
function ChatFooter({ roomCode, loggedInUser }: ChatFooterProps) {
	const [ input, setInput ] = useState('');
	const sendMessage = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
		e.preventDefault();
		if (input) {
			const messageDetails = {
				content: input,
				user: {
					username: loggedInUser.username
				},
				createdAt: new Date().toISOString(),
				roomCode
			};
			setInput('');

			console.log('sending message: ' + JSON.stringify(messageDetails));
			messageData.push(messageDetails);
		}
	};
	return (
		<div className="chat__footer">
			<form>
				<input value={input} onChange={(e) => setInput(e.target.value)} type="text" placeholder="Start typing.." />
				<button onClick={sendMessage} type="submit">
					Send
				</button>
			</form>
			<IconButton onClick={sendMessage}>
				<SendIcon />
			</IconButton>
		</div>
	);
}

export default ChatFooter;
