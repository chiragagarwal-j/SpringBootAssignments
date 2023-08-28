package com.learning.learningSpring.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.learningSpring.model.student.StudentData;

import java.util.Comparator;
import java.util.TreeSet;

@Controller
public class StudentRecordController {

	TreeSet<StudentData> records = new TreeSet<>(Comparator.comparingInt(StudentData::getScore));

	@GetMapping("/studentrecord")
	public String listStudents(Model model) {
		model.addAttribute("record", records);
		return "studentrecord";
	}

	@PostMapping("/studentrecord")
	public String processStudentForm(@RequestParam(value = "name", required = false) Integer deleteId,
			@RequestParam("Sname") String name, @RequestParam("Sscore") int score, Model model) {

		if (deleteId != null) {
			System.out.println("Deleting student with rank: " + deleteId);
			// records.removeIf(student -> student.getName().equals(name));
		} else {
			StudentData newStudent = new StudentData(records.size() + 1, name, score);
			records.add(newStudent);
		}

		model.addAttribute("record", records);
		return "studentrecord";
	}
}