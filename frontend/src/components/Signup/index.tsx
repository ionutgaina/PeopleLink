import React, { useState, useRef } from "react";
import { DebounceInput } from "react-debounce-input";
import { Button } from "@material-ui/core";
import "./style.css";
import { useHistory } from "react-router-dom";

export interface SignUpProps {
  history: ReturnType<typeof useHistory>;
}

function SignUp({ history }: SignUpProps) {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const firstNameRef = useRef<HTMLInputElement>(null);
  const lastNameRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const [errorMsg, setErrorMsg] = useState("");

	const proceed = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
		e.preventDefault();

		if (canProceed() && firstNameRef.current && passwordRef.current) {
			setErrorMsg('');
			history.push('/login');
		} else {
			setErrorMsg('Fill-in all required fields');
		}
	};

  const canProceed = () => {
    return (
      username &&
      email &&
      firstNameRef.current &&
      passwordRef.current &&
      passwordRef.current.value &&
      firstNameRef.current.value
    );
  };

  return (
    <div className="signup auth__wrapper">
      <div className="signup__area area__wrapper">
        <form>
          <DebounceInput
            debounceTimeout={300}
            onChange={(e) => {setEmail(e.target.value);}}
            value={email}
            type="text"
            placeholder="Email"
            required
          />
          <DebounceInput
            debounceTimeout={300}
            onChange={(e) => {setUsername(e.target.value);}}
            value={username}
            type="text"
            placeholder="Username"
            required
          />
          <input
            ref={passwordRef}
            type="password"
            placeholder="Password"
            required
          />
          <div className="signup__name">
            <input
              ref={firstNameRef}
              type="text"
              placeholder="First Name"
              required
            />
            <input ref={lastNameRef} type="text" placeholder="Last Name" />
          </div>

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
        {errorMsg && <strong className="error__msg">{errorMsg}</strong>}
      </div>
    </div>
  );
}

export default SignUp;
