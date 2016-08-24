package pl.altkom.lib;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity {
	@Version
	private Date lastModified;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) obj;
			if (entity.getId() == this.getId()) {
				return true;
			}
			if (this.getId() == null && entity.getId() == null) {
				return true;
			}
			if (this.getId() == null || entity.getId() == null) {
				return false;
			}
			return this.getId().equals(entity.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.getId() == null) {
			return super.hashCode();
		}
		return this.getId().hashCode();
	}

	public abstract Long getId();

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
