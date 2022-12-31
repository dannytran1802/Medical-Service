package com.example.restapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "message")
public class MessageManager extends BaseIdEntity {

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "status")
	private boolean status;

}
