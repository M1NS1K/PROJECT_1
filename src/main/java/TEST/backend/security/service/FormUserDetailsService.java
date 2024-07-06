package TEST.backend.security.service;

import TEST.backend.security.dto.UserDTO;
import TEST.backend.security.entity.User;
import TEST.backend.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

		private final UserRepository userRepository;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = userRepository.findByUsername(username);

				if(user == null) {
						if(userRepository.countByUsername(username) == 0) {
								throw new UsernameNotFoundException("No user found with username: " + username);
						}
				}

				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
				ModelMapper mapper = new ModelMapper();
				UserDTO userDTO = mapper.map(user, UserDTO.class);


		}
}
