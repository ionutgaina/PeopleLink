import React from 'react';
import './style.css';
import SidebarRoom from '../SidebarRooms';
import { useHistory } from 'react-router-dom';
import Scrollbar from 'react-scrollbars-custom';
import { RoomPopulated } from '../../types';
import SidebarHeader from '../SidebarHeader';
import { useUser } from '../../context/UserContext';

export interface SidebarProps {
	history: ReturnType<typeof useHistory>;
	rooms: RoomPopulated[];
	onNewRoom: () => void;
	onRoomClick: (code: string) => void;
}

const Sidebar = ({ onNewRoom, rooms, history, onRoomClick }: SidebarProps) => {
	const { userDetails } = useUser();
	return (
		<div className="sidebar">
			<SidebarHeader onNewRoom={onNewRoom} history={history} />

			<div className="sidebar__rooms">
				<Scrollbar className="sidebar__scrollbar">
					{rooms.map((room: RoomPopulated, i: number) => (
						<SidebarRoom key={i} room={room} userDetails={userDetails} onRoomClick={onRoomClick} />
					))}
				</Scrollbar>
			</div>
		</div>
	);
};

export default Sidebar;
