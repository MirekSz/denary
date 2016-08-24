package pl.altkom.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.altkom.lib.BaseEntity;

@Entity
public class Teacher extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_teacher")
	@SequenceGenerator(allocationSize = 100, name = "s_teacher", sequenceName = "s_teacher")
	private Long id;
	// @Column(table = "PERSONAL_DATA")
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private TeacheTitle title;
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	@Column(nullable = false, unique = true)
	private String email;
	private String phone;
	private SalaryInfo SalaryInfo;

	@OneToMany(mappedBy = "studentSet")
	private Set<Classes> classesSet = new HashSet<Classes>();

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

	public TeacheTitle getTitle() {
		return title;
	}

	public void setTitle(TeacheTitle title) {
		this.title = title;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Set<Classes> getClassesSet() {
		return classesSet;
	}

	public void setClassesSet(Set<Classes> classesSet) {
		this.classesSet = classesSet;
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

	public SalaryInfo getSalaryInfo() {
		return SalaryInfo;
	}

	public void setSalaryInfo(SalaryInfo salaryInfo) {
		SalaryInfo = salaryInfo;
	}
}
