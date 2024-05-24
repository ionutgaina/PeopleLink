export enum ChatEvent {
	CONNECT = 'connection',
	DISCONNECT = 'disconnect',
	JOIN = 'join',
	MESSAGE = 'message',
	UNREAD = 'unread',
	LEAVE = 'leave',
	ROOM_DELETE = 'room delete'
}
export enum MessageStatus {
	SENT = 'sent',
	DELIVERED = 'delivered',
	SEEN = 'seen',
	UNSENT = 'unsent',
	ERROR = 'error'
}

export const USER_INITIAL_VALUE = {
	username: '',
};

export const usersData = [
	{
		username: 'test',
		firstName: 'firstname',
		lastName: 'lastname',
		email: 'test@gmail.com',
		_id: '1'
	},
	{
		username: 'Chatbot',
		firstName: 'firstname1',
		lastName: 'lastname1',
		email: 'chat@gmail.com',
		_id: '2'
	},
	{
		username: 'Chatbot1',
		firstName: 'firstname2',
		lastName: 'lastname2',
		email: 'chat2gmail.com',
		_id: '3'
	}
];

export const roomData = [
	{
		code: 'AjXcBW',
		description: 'Room 1',
		users: [
			{
				user: usersData[0],
				unread: 0
			}
		],
		createdAt: '2021-02-01T12:00:00.000Z'
	},
	{
		code: 'AjXcBW1',
		description: 'Room 2',
		users: [
			{
				user: usersData[0],
				unread: 0
			}
		],
		createdAt: '2021-02-01T12:00:00.000Z'
	}
];


export const messageData = [
	{
		content: 'Hello',
		user: {
			username: usersData[1].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'

	},

	{
		content: 'Hi',
		user: {
			username: usersData[2].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'
	},

	{
		content: 'How are you?',
		user: {
			username: usersData[1].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'
	},

	{
		content: 'I am fine, thank you',
		user: {
			username: usersData[2].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'
	},

	{
		content: 'Good to hear',
		user: {
			username: usersData[1].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'
	},

	{
		content: 'Goodbye',
		user: {
			username: usersData[2].username
		},
		createdAt: '2021-02-01T12:00:00.000Z',
		roomCode: 'AjXcBW'
	}
];