package TEST.backend.security.controller;

import TEST.backend.security.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;

public class LoginAPI {

		@PostMapping(value="/signup")
		public String signup(UserDTO userDTO) {

		}

}
