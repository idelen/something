package com.jackpot.something.config.auditing;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false, nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime createdDate;

	@LastModifiedDate
	@Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime modifiedDate;

	@CreatedBy
	@Column(updatable = false, nullable = false)
	private String createdBy;

	@LastModifiedBy
	@Column(nullable = false)
	private String modifiedBy;
}
