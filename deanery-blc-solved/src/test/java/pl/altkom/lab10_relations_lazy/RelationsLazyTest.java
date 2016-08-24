package pl.altkom.lab10_relations_lazy;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Random;
import java.util.Set;

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

public class RelationsLazyTest extends BaseTest {
	private ClassesDao classesDao;
	private StudentDao studentDao;
	private SubjectDao subjectDao;
	private TeacherDao teacherDao;
	private MarkDao markDao;
	private Long STUDENT_ID;

	@Override
	protected void beforeTest(Session session) {
		teacherDao = new TeacherDao(session);
		subjectDao = new SubjectDao(session);
		classesDao = new ClassesDao(session);
		studentDao = new StudentDao(session);
		markDao = new MarkDao(session);

		Student student = (Student) session.createCriteria(Student.class)
				.add(eq("index", "Index 00")).uniqueResult();
		this.STUDENT_ID = student.getId();
	}

	@Test
	public void should_read_student_and_sum_all_marks() {
		// when
		Student load = studentDao.load(STUDENT_ID);

		// then
		int sum = 0;
		Set<Mark> set = load.getMarkSet();
		for (Mark mark : set) {
			sum += mark.getValue();
		}
		assertThat(sum).isGreaterThan(0);

	}

	@Test
	public void should_read_student_and_subject_of_mark() {
		// when
		Student load = studentDao.load(STUDENT_ID);

		// then
		Set<Mark> markSet = load.getMarkSet();
		for (Mark m : markSet) {
			System.out.println(m.getSubject().getTitle());
		}
	}

	@Test
	public void should_read_student_marks_classes() {
		// given
		Student load = studentDao.load(STUDENT_ID);

		// then
		Set<Mark> markSet = load.getMarkSet();
		for (Mark m : markSet) {
			Subject subject3 = m.getSubject();
			for (Classes c : subject3.getClassesSet()) {
				System.out.println(c.getRoom());
			}
		}

	}

	@Test
	public void should_read_classes() {
		classesDao.get(1L);
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

	private int addRandomMarks(Subject subject, Student student) {
		int sum = 0;
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			int mark = random.nextInt(5) + 1;
			sum += mark;
			markDao.addMark(subject, student, mark);
		}
		return sum;
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
		teacherDao.save(teacher);
		return teacher;
	}

}
