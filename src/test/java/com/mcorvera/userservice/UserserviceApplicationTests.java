package com.mcorvera.userservice;

import com.mcorvera.userservice.application.dtos.UserResponse;
import com.mcorvera.userservice.application.inputadapters.UserService;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.dtos.BaseResponse;
import com.mcorvera.userservice.infraestructure.inputadapters.http.UserController;
import com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions.ExceptionHandlerClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


@SpringBootTest
class UserserviceApplicationTests {
	@Autowired
	private UserController userController;
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(userController);
		Assertions.assertNotNull(userService);
	}

	@Test
	public void testRegisterUser_SuccessfulRegistration() {
		// Set a new User object with correct data
		User validUser = new User();
		validUser.setName("John Doe");
		validUser.setEmail("johndoe@example.com");
		validUser.setPassword("Password123!");

		//Mock the service UserService
		UserService userService = Mockito.mock(UserService.class);
		UserResponse expectedUserResponse = new UserResponse();
		expectedUserResponse.setId("123");
		expectedUserResponse.setUsername("johndoe");
		Mockito.when(userService.createUser(validUser)).thenReturn(expectedUserResponse);

		// Set up an instance of Controller
		UserController userController = new UserController(userService);

		ResponseEntity<BaseResponse> response = userController.registerUser(validUser);

		//Validate status http is OK
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		//Validate that the return data is right:
		BaseResponse baseResponse = response.getBody();
		assert baseResponse != null;
		UserResponse actualUserResponse = (UserResponse) baseResponse.getData();
		Assertions.assertEquals(expectedUserResponse.getId(), actualUserResponse.getId());
		Assertions.assertEquals(expectedUserResponse.getUsername(), actualUserResponse.getUsername());
	}

}
