package com.example.restapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "otp")
public class OTP extends BaseIdEntity {

	@Column(name = "username")
	private String username;

	@Column(name = "otp")
	private String otp;

}
