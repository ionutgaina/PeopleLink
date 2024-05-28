import React from 'react';
import { parseISO } from 'date-fns';
import { MessagePopulated } from '../../types';
import './style.css';

export interface ChatHeaderProps {
	roomCode: string;
	messages: MessagePopulated[];
	formatDate: (date: Date) => string;
}

function ChatHeader({ roomCode, messages, formatDate }: ChatHeaderProps) {
	return (
		<div className="chat__header">
			<div className="chat__headerInfo">
				<p>
					{messages.length > 0 ? (
						'Last activity ' + formatDate(parseISO(messages[messages.length - 1].createdAt))
					) : (
						'No recent activities...'
					)}
				</p>
			</div>
		</div>
	);
}

export default ChatHeader;
