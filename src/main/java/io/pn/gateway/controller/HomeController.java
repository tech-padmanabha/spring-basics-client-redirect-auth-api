package io.pn.gateway.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.pn.gateway.dto.Employee;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String displayHomePage(Model model,@AuthenticationPrincipal OAuth2User oauth) {
		
		List<Employee> allEmpList = List.of(
				new Employee(101, "Rohan", "rohan@gmail.com"),
				new Employee(102, "Sahil", "sahil@gmail.com"),
				new Employee(103, "Kumar", "kumar@gmail.com")
				);
		
		if(oauth != null) {
			String name = oauth.getAttribute("name");
			model.addAttribute("allemplist", allEmpList);
			model.addAttribute("username", name);
		}
		return "home";
	}
}
