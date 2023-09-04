package com.learningSpring.sudoko.repository;

import org.springframework.data.repository.CrudRepository;

import com.learningSpring.sudoko.entity.Sudoku;

public interface SudokuRepository extends CrudRepository<Sudoku, Integer> {

}
