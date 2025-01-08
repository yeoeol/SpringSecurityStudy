package com.example.testsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;

	@GetMapping("/join")
	public String joinP() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO joinDTO) {
		System.out.println(joinDTO.getUsername());
		joinService.joinProcess(joinDTO);
		return "redirect:/login";
	}
}
