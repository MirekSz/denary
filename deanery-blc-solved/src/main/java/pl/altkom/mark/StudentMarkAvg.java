package pl.altkom.mark;

import pl.altkom.model.Student;

public class StudentMarkAvg implements Comparable<StudentMarkAvg> {
	private Student student;
	private Double avg;

	public StudentMarkAvg(Student student, Double avg) {
		this.student = student;
		this.avg = avg;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	@Override
	public int compareTo(StudentMarkAvg arg0) {
		return this.avg.compareTo(arg0.avg);
	}
}
