package com.learningSpring.sudoko.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.learningSpring.sudoko.entity.Sudoku;
import com.learningSpring.sudoko.model.SudokuValidator;
import com.learningSpring.sudoko.repository.SudokuRepository;

import jakarta.annotation.PostConstruct;

@Controller
public class SudokuController {

	@Autowired
	private SudokuRepository sudokuRepo;

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
		GameBoard gameBoard = new GameBoard();
		gameBoard.setCells(gameBoardArray);
		model.addAttribute("gameBoard", gameBoard);
		return "createSudoko";
	}

	@PostMapping("/add")
	public String processSudokuForm(@ModelAttribute("gameBoard") GameBoard gameBoard) {
		StringBuilder gameBoardString = new StringBuilder();
		for (char[] row : gameBoard.getCells()) {
			for (char cell : row) {
				gameBoardString.append(cell);
			}
		}
		System.out.println(gameBoardString.toString());

		if (SudokuValidator.isValidSudoku(gameBoardString.toString())) {
			Sudoku sudoku = new Sudoku();
			sudoku.setGameBoard(gameBoardString.toString());
			sudokuRepo.save(sudoku);
			System.out.println("Valid Sudoku Board");
		} else {
			System.out.println("Invalid Sudoku Board");
		}
		return "redirect:/createSudoko";
	}

	@GetMapping("/createSudoko/{id}")
	public String playSudokuForm(@PathVariable int id, Model model) throws ResourceNotFoundException {
		Optional<Sudoku> sudoku = Optional.ofNullable(sudokuRepo.findById(id));
		if (sudoku.isEmpty()) {
			throw new ResourceNotFoundException("No game with the requested ID");
		} else {
			String gameBoard = sudoku.getGameBoard();
			char[][] gameBoardArray = new char[9][9];
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					gameBoardArray[i][j] = gameBoard.charAt(i * 9 + j);
				}
			}
			GameBoard gameBoard = new GameBoard();
			gameBoard.setCells(gameBoardArray);
			model.addAttribute("gameBoard", gameBoard);
		}
		return "createSudoku";
	}

}