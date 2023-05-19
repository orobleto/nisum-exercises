package com.nisum.exercises.users.entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phones")
@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR(2)")
	private String countryCode;
	@Column(nullable = false, columnDefinition = "VARCHAR(1)")
	private String cityCode;
	@Column(nullable = false, columnDefinition = "VARCHAR(10)")
	private String number;

}
