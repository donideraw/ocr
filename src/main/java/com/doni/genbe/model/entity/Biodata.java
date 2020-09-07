package com.doni.genbe.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_biodata")
public class Biodata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_bio", unique = true)
	private Integer kodeBiodata;
	
	@Column(name = "nohp", length = 16)
	private String nomorHandphone;
	
	@Column(name = "tanggal_lahir", nullable = false)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date tanggalLahir;
	
	@Column(name = "tempat_lahir", length = 50)
	private String tempatLahir;
	
	@OneToOne
	@JoinColumn(name = "idperson", unique = true, nullable = false)
	private Person person;

	public Integer getKodeBiodata() {
		return kodeBiodata;
	}

	public void setKodeBiodata(Integer kodeBiodata) {
		this.kodeBiodata = kodeBiodata;
	}

	public String getNomorHandphone() {
		return nomorHandphone;
	}

	public void setNomorHandphone(String nomorHandphone) {
		this.nomorHandphone = nomorHandphone;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
