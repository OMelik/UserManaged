package com.infonal.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infonal.dao.UserDAO;
import com.infonal.model.User;

@Controller
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getUserList(Model model) {
		logger.info("listing users");
		List<User> users = new ArrayList<User>();
		users = userDAO.getAllUsers();
		model.addAttribute("userList", users);
		return "users";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUser(@RequestParam("id") String id) {
		userDAO.deleteUserFindById(id);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertUser(@RequestParam("user") User user) {
		userDAO.insertUser(user);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editUser(@RequestParam("user") User user) {
		userDAO.editUser(user);
		return "redirect:/";
	}
	
}
