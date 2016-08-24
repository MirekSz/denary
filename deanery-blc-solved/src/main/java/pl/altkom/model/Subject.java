package pl.altkom.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import pl.altkom.lib.BaseEntity;

@Entity
// @BatchSize(size = 5)
public class Subject extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_subject")
	@SequenceGenerator(allocationSize = 100, name = "s_subject", sequenceName = "s_subject")
	private Long id;
	private String title;
	@Enumerated
	private SubjectType kind;

	@OneToMany(mappedBy = "subject")
	private Set<Classes> classesSet = new HashSet<Classes>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SubjectType getKind() {
		return kind;
	}

	public void setKind(SubjectType kind) {
		this.kind = kind;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Classes> getClassesSet() {
		return classesSet;
	}

	public void setClassesSet(Set<Classes> classesSet) {
		this.classesSet = classesSet;
	}
}
