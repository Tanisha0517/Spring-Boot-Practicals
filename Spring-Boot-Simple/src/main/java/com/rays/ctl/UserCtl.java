package com.rays.ctl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.Response;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.UserService;

@RestController
@RequestMapping(value = "User")
public class UserCtl extends BaseCtl {

	@Autowired
	private UserService userService;

	@PostMapping("save")
	public Response save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		Response res = new Response();

		res = validate(bindingResult);
		if (res.isSuccess() == false) {
			return res;
		}

		UserDTO dto = (UserDTO) form.getDto();

		try {
			if (dto.getId() != null && dto.getId() > 0) {
				userService.update(dto);
				res.addMessage("user updated successfully");
				res.addData(dto);
			} else {
				userService.add(dto);
				res.addMessage("user added successfully");
				res.addData(dto);
			}
		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
		}

		return res;

	}

	@GetMapping("delete/{ids}")
	public Response save(@PathVariable long[] ids) {

		Response res = new Response();

		for (long id : ids) {
			userService.delete(id);
			res.setSuccess(true);
			res.addMessage("record deleted successfully");
		}

		return res;

	}

	@GetMapping("get/{id}")
	public Response save(@PathVariable long id) {

		Response res = new Response();

		UserDTO dto = userService.findByPk(id);

		if (dto != null) {
			res.addData(dto);
			res.setSuccess(true);
		} else {
			res.addMessage("record not found");
		}

		return res;

	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/search/{pageNo}")
	public Response search(@RequestBody UserForm form, @PathVariable int pageNo) {

		Response res = new Response();

		int pageSize = 5;

		UserDTO dto = (UserDTO) form.getDto();

		List<UserDTO> list = userService.search(dto, pageNo, pageSize);

		if (list != null) {
			res.addData(list);
			res.setSuccess(true);
		} else {
			res.addMessage("record not found");
		}

		return res;

	}
	
	//y bhi kr skte h

//	@GetMapping("findByUniqueKey/{login}")
//	public Response findByUniqueKey(@PathVariable String login) {
//
//		Response res = new Response();
//
//		UserDTO dto = userService.findByUniqueKey("login", login);
//
//		if (dto != null) {
//			res.setSuccess(true);
//			res.addData(dto);
//			res.addMessage("User found successfully");
//		} else {
//			res.setSuccess(false);
//			res.addMessage("User not found");
//		}
//
//		return res;
//	}

	@PostMapping("findByLogin")
	public Response findByLogin(@RequestBody UserForm form) {

		Response res = new Response();

		UserDTO dto = (UserDTO) form.getDto();

		UserDTO user = userService.findByLogin(dto.getLogin());

		if (user != null) {
			res.setSuccess(true);
			res.addData(user);
			res.addMessage("User found successfully");
		} else {
			res.setSuccess(false);
			res.addMessage("User not found");
		}

		return res;
	}

}