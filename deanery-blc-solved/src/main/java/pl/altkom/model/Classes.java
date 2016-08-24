package pl.altkom.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pl.altkom.lib.BaseEntity;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "room", columnNames = {
		"room", "dateFrom", "dateTo" }))
public class Classes extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_classes")
	@SequenceGenerator(allocationSize = 100, name = "s_classes", sequenceName = "s_classes")
	private Long id;
	private String room;
	private Date dateFrom;
	private Date dateTo;

	@ManyToOne
	@JoinColumn(name = "id_teacher")
	private Teacher teacher;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_subject")
	private Subject subject;

	private Integer semester;

	@ManyToMany
	@JoinTable(name = "classes_2_student", joinColumns = @JoinColumn(name = "id_classes"), inverseJoinColumns = @JoinColumn(name = "id_student"))
	private Set<Student> studentSet = new HashSet<Student>();

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Set<Student> getStudentSet() {
		return studentSet;
	}

	public void add(Student student) {
		studentSet.add(student);
		student.getClassesSet().add(this);

	}

}
