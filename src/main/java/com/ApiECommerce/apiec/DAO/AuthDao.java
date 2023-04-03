package com.ApiECommerce.apiec.DAO;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ApiECommerce.apiec.Configuration.JwtService;
import com.ApiECommerce.apiec.Exception.RequestException;
import com.ApiECommerce.apiec.Exception.UserNotFoundException;
import com.ApiECommerce.apiec.Model.AuthenticationResponse;
import com.ApiECommerce.apiec.Model.LoginDTO;
import com.ApiECommerce.apiec.Model.Role;
import com.ApiECommerce.apiec.Model.User;
import com.ApiECommerce.apiec.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDao {

	@Autowired
	private IUserDao userServ;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final JwtService jwtService = new JwtService();

	public AuthenticationResponse authenticate(LoginDTO loginDTO) {
		authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		LocalDateTime time = LocalDateTime.now();
		var user = userServ.findByUsername(loginDTO.getUsername());

		if (user == null) {
			throw new UserNotFoundException("User not found", "404", HttpStatus.NOT_FOUND);

		} else if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
			throw new RequestException("Wrong password", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		}

		var jwtToken = jwtService.generateToken(user);
		user.setLast_connection(time);
		userServ.save(user);

		return new AuthenticationResponse(jwtToken);

	}

	public AuthenticationResponse register(User user) {

		User user1 = userServ.findByUsername(user.getUsername());
		int mailExists = userServ.findUserByMail(user.getMail());

		if (mailExists > 0) {
			throw new RequestException("Mail already registered", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		} else if (user1 != null) {
			throw new RequestException("Username already registered", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//user.setRole(Role.ROLE_USER);
		user.setLast_connection(LocalDateTime.now());
		userServ.save(user);

		var jwtToken = jwtService.generateToken(user);

		return new AuthenticationResponse(jwtToken);
	}
	
	public String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    UserDetails userDetails = null ;
	    if (principal instanceof UserDetails) {
	        userDetails = (UserDetails) principal;
	    }
	    
	    @SuppressWarnings("null")
	    String userName = userDetails.getUsername();
	    return userName;
	}
}
