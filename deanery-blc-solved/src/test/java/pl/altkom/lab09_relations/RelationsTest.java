package pl.altkom.lab09_relations;

import static org.fest.assertions.Assertions.assertThat;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.classes.ClassesDao;
import pl.altkom.mark.MarkDao;
import pl.altkom.model.Classes;
import pl.altkom.model.Mark;
import pl.altkom.model.Student;
import pl.altkom.model.Subject;
import pl.altkom.model.Teacher;
import pl.altkom.student.StudentDao;
import pl.altkom.subject.SubjectDao;
import pl.altkom.teacher.TeacherDao;
import pl.altkom.util.BaseTest;

public class RelationsTest extends BaseTest {
	private ClassesDao classesDao;
	private StudentDao studentDao;
	private SubjectDao subjectDao;
	private TeacherDao teacherDao;
	private MarkDao markDao;

	@Test
	public void should_add_mark_to_student_from_subject() {
		// given
		Subject subject = addSubject();

		Student student = addStudent();

		long rowsInTable = countRowsInTable(Mark.class);

		// when
		markDao.addMark(subject, student, 5);

		// then
		assertThat(countRowsInTable(Mark.class)).isEqualTo(rowsInTable + 1);

	}

	@Test
	public void should_add_student_to_classes() {
		// given
		Teacher teacher = addTeacher();

		Subject subject = addSubject();

		Classes classes = addClasses(teacher, subject);

		Student student = addStudent();
		flushClear();
		classes = classesDao.load(classes.getId());
		student = studentDao.load(student.getId());
		// when
		classesDao.addStudentToClasses(classes, student);
		flushClear();
		// then
		Classes load = classesDao.load(classes.getId());
		assertThat(load.getStudentSet().size()).isEqualTo(1);
	}

	@Test
	public void should_create_classes_with_teacher_and_subject() {
		// given
		Teacher teacher = addTeacher();

		Subject subject = addSubject();

		Classes classes = addClasses(teacher, subject);

		// when
		classesDao.save(classes);
		flushClear();

		// then
		Classes load = classesDao.load(classes.getId());
		assertThat(load.toString()).isNotNull();
	}

	@Test
	public void should_find_all_marks_of_student() {
		// given
		Student student = addStudent();
		Subject programing = addSubject();
		Subject entertiment = addSubject();

		// when
		markDao.addMark(programing, student, 12);
		markDao.addMark(programing, student, 3);
		markDao.addMark(entertiment, student, 2);
		markDao.addMark(entertiment, student, 3);
		flushClear();

		// then
		student = studentDao.load(student.getId());
		assertThat(student.getMarkSet().size()).isEqualTo(4);
	}

	private Classes addClasses(Teacher teacher, Subject subject) {
		Classes classes = new Classes();
		classes.setRoom("204");
		classes.setTeacher(teacher);
		classes.setSubject(subject);
		classesDao.save(classes);
		return classes;
	}

	private Student addStudent() {
		Student student = new Student();
		student.setIndex("123");
		studentDao.save(student);
		return student;
	}

	private Subject addSubject() {
		Subject subject = new Subject();
		subject.setTitle("Programowanie");
		subjectDao.save(subject);
		return subject;
	}

	private Teacher addTeacher() {
		Teacher teacher = new Teacher();
		teacher.setFirstName("Alan");
		teacher.setEmail("alan@gmail.com");
		teacherDao.save(teacher);
		return teacher;
	}

	@Override
	protected void beforeTest(Session session) {
		teacherDao = new TeacherDao(session);
		subjectDao = new SubjectDao(session);
		classesDao = new ClassesDao(session);
		studentDao = new StudentDao(session);
		markDao = new MarkDao(session);
	}
}
