package com.example.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.service.JoinService;

import lombok.RequiredArgsConstructor;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;

	@PostMapping("/join")
	public String joinProcess(JoinDTO joinDTO) {

		joinService.joinProcess(joinDTO);

		return "ok";
	}
}
