export interface MessagePopulated {
	content: string;
	user: User;
	createdAt: string;
}
export interface User {
	username: string;
}

export interface RoomPopulated {
	code: string;
	description: string;
	users: User[];
	owner: string;
}

export interface ContactPopulated {
	username: string;
	sender: string;
	status: string;
	roomCode: string;
}

export interface UserRoom {
	name: string;
	room: string;
}

export interface ChatMessage {
	userRoom: UserRoom;
	content: string;
	status?: string;
	isSystem?: boolean;
}

export interface CredAvailabilityData {
	value: string;
	type: string;
}
export interface LoginData {
	username: string;
	password: string;
}

export interface NewRoomData {
	description: string;
}
export interface RoomData {
	roomCode: string;
}