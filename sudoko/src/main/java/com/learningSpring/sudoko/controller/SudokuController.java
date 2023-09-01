package com.learningSpring.sudoko.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.learningSpring.sudoko.entity.Sudoku;
import com.learningSpring.sudoko.model.SudokuValidator;
import com.learningSpring.sudoko.repository.SudokuRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;

@Controller
public class SudokuController {

	@Autowired
	private SudokuRepository sudokoRepo;

	private SudokuValidator validator;
	Sudoku s;
	String gameBoard;
//	private List<Sudoku> sudokoList;

	@PostConstruct
	public void init() {
		gameBoard = "0";
		gameBoard = gameBoard.repeat(81);
	}

	@GetMapping("/createSudoko")
	public String rent(Model model) {
		char[][] gameBoardArray = new char[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				gameBoardArray[i][j] = gameBoard.charAt(i * 9 + j);
			}
		}
		model.addAttribute("gameBoard", gameBoardArray);
		return "createSudoko";
	}

	@PostMapping("/add")
	public String processSudokuForm(@ModelAttribute("gameBoard") GameBoard gameBoard) {
		StringBuilder gameBoardString = new StringBuilder();
		for (char[] row : gameBoard) {
			for (char cell : row) {
				gameBoardString.append(cell);
			}
		}
		if (validator.isValidSudoku(gameBoardString.toString())) {
//			sudokoRepo.save(s);
			System.out.println("Valid Sudoku Board");
		} else {
			System.out.println("Invalid Sudoku Board");
		}
		return "redirect:/createSudoko";
	}

}