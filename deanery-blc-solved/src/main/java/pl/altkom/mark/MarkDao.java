package pl.altkom.mark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import pl.altkom.lib.BaseDao;
import pl.altkom.model.Mark;
import pl.altkom.model.Student;
import pl.altkom.model.Subject;

public class MarkDao extends BaseDao<Mark> {
	public MarkDao(Session session) {
		super(session);
	}

	public void addMark(Subject subject, Student student, Integer val) {
		Mark mark = new Mark();
		mark.setStudent(student);
		mark.setSubject(subject);
		mark.setValue(val);
		mark.setIsFinal(false);

		session.persist(mark);
		session.flush();
		session.clear();
		// student.getMarkSet().add(mark);
	}

	public List<Mark> getFinalMarks() {
		return session.createCriteria(Mark.class)
				.setFetchMode("student", FetchMode.JOIN)
				.add(Restrictions.eq("isFinal", true)).list();
	}

	public Collection<StudentMarkAvg> countStudentsAvg() {
		Set<StudentMarkAvg> result = new TreeSet<StudentMarkAvg>(
				Collections.reverseOrder());

		List<Student> list = getAllStudents();

		for (Student student : list) {
			Set<Mark> markSet = student.getMarkSet();
			checkIfSomethingChange(student, markSet);
			BigDecimal sum = sumAllMarks(markSet);

			Double avg = countAvg(sum, markSet.size());

			result.add(new StudentMarkAvg(student, avg));

		}
		return result;
	}

	private void checkIfSomethingChange(Student student, Set<Mark> markSet) {
		Query query = session
				.createQuery("select count(*) From Mark where student = :student");
		query.setParameter("student", student);
		Long uniqueResult = (Long) query.uniqueResult();
		if (uniqueResult > markSet.size()) {
			throw new IllegalStateException(
					"Something changed during counting avg. This create wrong results");
		}
	}

	private double countAvg(BigDecimal sum, int size) {
		return sum.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP)
				.doubleValue();
	}

	private BigDecimal sumAllMarks(Set<Mark> markSet) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Mark mark : markSet) {
			sum = sum.add(BigDecimal.valueOf(mark.getValue()));
		}
		return sum;
	}

	private List<Student> getAllStudents() {
		Criteria studentCriteria = session.createCriteria(Student.class);
		List<Student> list = studentCriteria.list();
		return list;
	}

	public void addFinalMarkFromSubjectAndCountPromotion() {
		removeCurrentPromotion();

		deleteCurrentFinalMarks();

		List<Student> list = getAllStudents();

		for (Student student : list) {
			Double summaryAvg = 0.0;
			List<Long> subjects = getSubjectsFromWhichStudentHasMarks(student);

			for (Long subject : subjects) {
				Double avgFromSubject = getAvgFromSubject(student, subject);

				summaryAvg += avgFromSubject;

				int intValue = avgFromSubject.intValue();
				addFinalMarkFromSubject(subject, student, intValue);
			}
			if (summaryAvg > 0) {
				student.setPromotion(true);
			}
		}
	}

	private void deleteCurrentFinalMarks() {
		session.createQuery("delete From Mark where isFinal = true")
				.executeUpdate();
	}

	private void removeCurrentPromotion() {
		session.createQuery("update Student set promotion = false")
				.executeUpdate();
	}

	private void addFinalMarkFromSubject(Long subject, Student student,
			int intValue) {
		Mark mark = new Mark();
		mark.setStudent(student);
		Subject load = (Subject) session.load(Subject.class, subject);
		mark.setSubject(load);
		mark.setValue(intValue);
		mark.setIsFinal(true);
		session.persist(mark);

	}

	private List<Long> getSubjectsFromWhichStudentHasMarks(Student student) {
		Query query = session
				.createQuery("select distinct m.subject.id From Student s join s.markSet m where s = :student group by m.subject.id");
		query.setParameter("student", student);
		query.setFlushMode(FlushMode.MANUAL);
		return query.list();
	}

	private Double getAvgFromSubject(Student student, Long subject) {
		Query query = session
				.createQuery("select avg(m.value) from Mark m where m.subject.id = :subject and m.student = :student");
		query.setParameter("student", student);
		query.setParameter("subject", subject);
		return (Double) query.uniqueResult();
	}

	public List<Mark> getMarks(String index) {
		List<Mark> result = new ArrayList<Mark>();
		List<Mark> marks = session.createCriteria(Mark.class).list();
		for (Mark mark : marks) {
			if (mark.getStudent().getIndex().toLowerCase()
					.contains(index.toLowerCase())) {
				result.add(mark);
			}
		}
		return result;
	}
}
