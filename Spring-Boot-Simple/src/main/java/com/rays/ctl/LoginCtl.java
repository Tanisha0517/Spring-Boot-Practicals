package com.rays.ctl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.Response;
import com.rays.dto.UserDTO;
import com.rays.form.LoginForm;
import com.rays.form.UserRegistrationForm;
import com.rays.service.UserService;

@RestController
@RequestMapping(value = "Auth")
public class LoginCtl extends BaseCtl {

 	@Autowired
	private UserService userService;

	@PostMapping("login")
	public Response login(@RequestBody @Valid LoginForm form, BindingResult bindingResult, HttpSession session) {

		Response res = new Response();

		res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = (UserDTO) form.getDto();

		dto = userService.authenticate(dto.getLogin(), dto.getPassword());

		if (dto != null) {
			res.addData(dto);
			session.setAttribute("user", dto);
			res.addMessage("user login successfully");
		} else {
			res.setSuccess(false);
			res.addMessage("invalid login or password");
		}

		return res;

	}

	@PostMapping("signUp")
	public Response signUp(@RequestBody @Valid UserRegistrationForm form, BindingResult bindingResult) {

		Response res = new Response();

		res = validate(bindingResult);
		if (res.isSuccess() == false) {
			return res;
		}

		UserDTO dto = (UserDTO) form.getDto();

		try {

			userService.add(dto);
			res.addMessage("user registered successfully");
			res.addData(dto);

		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.addMessage(e.getMessage());
		}

		return res;

	}

	@GetMapping("logout")
	public Response logout(HttpSession session) {

		Response res = new Response();

		session.invalidate();
		res.setSuccess(true);
		res.addMessage("user logout successfully");

		return res;

	}

}