package pl.altkom.lib;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Optional;

public abstract class BaseDao<E> {
	private Class persistentClass;
	protected Session session;

	public BaseDao(Session session) {
		this.session = session;
		this.persistentClass = (Class) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(E entity) {
		session.persist(entity);
		session.flush();
	}

	public E load(Long id) {
		return (E) session.load(persistentClass, id);

	}

	public Optional<E> get(Long id) {
		Object object = session.get(persistentClass, id);
		return Optional.fromNullable((E) object);
	}

	public void delete(E entity) {
		session.delete(entity);

	}

	public void delete(Long id) {
		session.delete(session.load(persistentClass, id));

	}

	public List<E> findAll() {
		Criteria criteria = session.createCriteria(persistentClass);
		criteria.setMaxResults(100);
		return criteria.list();
	}
}
