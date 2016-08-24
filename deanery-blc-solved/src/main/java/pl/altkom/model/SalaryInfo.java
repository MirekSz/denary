package pl.altkom.model;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class SalaryInfo {
	private Double salary;
	private Date lastSalaryRise;

	private Double credit;

	public SalaryInfo() {
	}

	public SalaryInfo(Date lastSalaryRise, Double salary, Double credit) {
		this.lastSalaryRise = lastSalaryRise;
		this.salary = salary;
		this.credit = credit;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getLastSalaryRise() {
		return lastSalaryRise;
	}

	public void setLastSalaryRise(Date lastSalaryRise) {
		this.lastSalaryRise = lastSalaryRise;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

}
