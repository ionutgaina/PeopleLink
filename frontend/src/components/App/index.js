import React, { useState } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import './style.css';
import Room from '../Room';
import SignUp from '../Signup';
import Login from '../Login';
import NotFound from '../NotFound';
import { USER_INITIAL_VALUE } from '../../constants';
import { UserContext } from '../../context/UserContext';
import { StylesProvider } from '@material-ui/core/styles';

const routes = [
	{ path: '/signup', component: SignUp },
	{ path: '/login', component: Login },
	{ path: '/room', component: Room },
	{ path: '/', component: Login }
];


function App() {
	const [ userDetails, setUserDetails ] = useState(USER_INITIAL_VALUE);

	return (
		<StylesProvider injectFirst>
			<UserContext.Provider value={{ userDetails, setUserDetails }}>
					<div className="app">
						<Router>
							<Switch>
								{routes.map(({ path, component }) => <Route key={path} path={path} component={component} exact />)}
								<Route component={NotFound} />
							</Switch>
						</Router>
					</div>
			</UserContext.Provider>
		</StylesProvider>
	);
}

export default App;
