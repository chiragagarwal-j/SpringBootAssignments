package com.learningSpring.sudoko.model;

import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {

	public static boolean isValidSudoku(String gameBoard) {
		if (gameBoard == null || gameBoard.length() != 81) {
			return false;
		}

		char[][] board = new char[9][9];

		int index = 0;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				char cell = gameBoard.charAt(index++);
				if ((cell < '1' || cell > '9') && cell != '.') {
					return false;
				}
				board[row][col] = cell;
			}
		}

		for (int i = 0; i < 9; i++) {
			if (!isValidRow(board, i) || !isValidColumn(board, i) || !isValidSubgrid(board, i)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValidRow(char[][] board, int row) {
		Set<Character> seen = new HashSet<>();
		for (int col = 0; col < 9; col++) {
			char cell = board[row][col];
			if (cell != '.' && !seen.add(cell)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidColumn(char[][] board, int col) {
		Set<Character> seen = new HashSet<>();
		for (int row = 0; row < 9; row++) {
			char cell = board[row][col];
			if (cell != '.' && !seen.add(cell)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidSubgrid(char[][] board, int subgrid) {
		Set<Character> seen = new HashSet<>();
		int rowOffset = (subgrid / 3) * 3;
		int colOffset = (subgrid % 3) * 3;
		for (int row = rowOffset; row < rowOffset + 3; row++) {
			for (int col = colOffset; col < colOffset + 3; col++) {
				char cell = board[row][col];
				if (cell != '.' && !seen.add(cell)) {
					return false;
				}
			}
		}
		return true;
	}
}
