package com.learning.learningSpring.model.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Classroom {
	private List<Student> students;
	private static int idCounter = 1;
	StudentDB db;

	public Classroom() {
		students = new ArrayList<>();
		db = new StudentDB();
//		loadDB();
	}

	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}

	private void rank() {
		Collections.sort(students, (s1, s2) -> -Integer.compare(s1.getScore(), s2.getScore()));
		for (int i = 0; i < students.size(); i++)
			students.get(i).setRank(i + 1);
		for (int i = 1; i < students.size(); i++) {
			if (students.get(i).getScore() == students.get(i - 1).getScore())
				students.get(i).setRank(students.get(i - 1).getRank());
		}
	}

	public void add(Student student) {
		student.setId(idCounter++);
		students.add(student);
		rank();
		try {
			db.saveData(student.getId(), student.getRank(), student.getName(), student.getScore());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateTable();
	}

	public void remove(int id) {
		Optional<Student> studentToRemove = getById(id);
		students.remove(studentToRemove.get());
		rank();
		try {
			db.removeData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateTable();
	}

	public void replace(int id, Student current) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getId() == id) {
				students.get(i).setName(current.getName());
				students.get(i).setScore(current.getScore());
			}
		}
		rank();
		updateTable();
	}

	public Optional<Student> getById(int id) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getId() == id)
				return Optional.of(students.get(i));
		}
		return Optional.empty();
	}

	public void updateTable() {
		for (Student s : students) {
			try {
				db.updateData(Integer.valueOf(s.getId()), Integer.valueOf(s.getRank()), String.valueOf(s.getName()),
						Integer.valueOf(s.getScore()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void clear() {
		db.clearDB();
		
	}
	
//	public void loadDB() {
//		ResultSet rs = db.getData();
//			try {
//				while (rs.next()) {
//					students.add(new Student(rs.getInt("id"),rs.getInt("sRank"),rs.getString("name"),rs.getInt("score")));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//	}

}