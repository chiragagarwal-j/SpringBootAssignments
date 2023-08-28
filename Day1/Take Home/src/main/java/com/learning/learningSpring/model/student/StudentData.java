package com.learning.learningSpring.model.student;

public class StudentData {
	private int rank;
	private String name;
	private int score;

	public StudentData(int rank,String name, int score) {
		this.rank=rank;
		this.name = name;
		this.score = score;
	}

	public int getrank() {
		return rank;
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}

}