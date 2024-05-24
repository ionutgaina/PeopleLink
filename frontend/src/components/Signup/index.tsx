import React, { useState, useRef } from "react";
import { DebounceInput } from "react-debounce-input";
import { Button } from "@material-ui/core";
import "./style.css";
import { useHistory } from "react-router-dom";
import { register } from "../../services/Auth";
import Swal from "sweetalert2";

export interface SignUpProps {
  history: ReturnType<typeof useHistory>;
}

function SignUp({ history }: SignUpProps) {
  const [username, setUsername] = useState("");
  const passwordRef = useRef<HTMLInputElement>(null);
  const [errorMsg, setErrorMsg] = useState("");

  const proceed = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();

    if (canProceed() && passwordRef.current) {
      setErrorMsg("");

      try {
        await register(username, passwordRef.current.value);

        // success message
        const response = "Successfully registered!";

        Swal.fire({
          title: response,
          icon: "success",
          showConfirmButton: true,
        }).then(() => history.push("/login"));
      } catch (e: any) {
        // Error message
        Swal.fire({
          title: e.response.data,
          icon: "error",
          timer: 2500,
          showConfirmButton: false,
        });

        throw e;
      }
    } else {
      setErrorMsg("Fill-in all required fields");
    }
  };

  const canProceed = () => {
    return username && passwordRef.current && passwordRef.current.value;
  };

  return (
    <div className="login auth__wrapper">
      <div className="login__area area__wrapper">
        <h1>PLink Register</h1>
        <form>
          <DebounceInput
            debounceTimeout={300}
            onChange={(e) => {
              setUsername(e.target.value);
            }}
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
          <p>
            Already have an account?
            <b
              className="signup__link header__text"
              onClick={() => {
                history.push("/login");
              }}
            >
              Go to Login
            </b>
          </p>
        </form>
        {errorMsg && <strong className="error__msg">{errorMsg}</strong>}
      </div>
    </div>
  );
}

export default SignUp;
