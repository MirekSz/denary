package pl.altkom.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import pl.altkom.lib.BaseEntity;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "Student.findWithAtLeastFiveMarks", query = "SELECT t FROM Classes t  WHERE t.teacher.firstName='a'"),
		@NamedQuery(name = "Student.countAvgForStudent", query = "SELECT sub.title, avg(ms.value) FROM Student s JOIN s.markSet ms JOIN ms.subject sub WHERE s.id =:id GROUP BY sub.title") })
public class Student extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_student")
	@SequenceGenerator(allocationSize = 100, name = "s_student", sequenceName = "s_student")
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	@Column(unique = true)
	private String index;
	private String phone;
	private Date birthDate;
	private Boolean promotion;

	@ManyToMany(mappedBy = "studentSet", cascade = CascadeType.ALL)
	private Set<Classes> classesSet = new HashSet<Classes>();

	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// @BatchSize(size = 10)
	private Set<Mark> markSet = new HashSet<Mark>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Set<Classes> getClassesSet() {
		return classesSet;
	}

	public void setClassesSet(Set<Classes> classesSet) {
		this.classesSet = classesSet;
	}

	public Set<Mark> getMarkSet() {
		return markSet;
	}

	public void setMarkSet(Set<Mark> markSet) {
		this.markSet = markSet;
	}
}
