package pl.altkom.subject;

import org.hibernate.Session;

import pl.altkom.lib.BaseDao;
import pl.altkom.model.Subject;

public class SubjectDao extends BaseDao<Subject> {
	public SubjectDao(Session session) {
		super(session);
	}
}
