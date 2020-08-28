package com.doni.genbe.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_pendidikan")
public class Pendidikan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pendidikan", unique = true)
	private Integer kodePendidikan;
	
	@ManyToOne
	@JoinColumn (name = "idperson", nullable = false)
	private Person person;
	
	@Column(name = "jenjang", nullable = false, length = 10)
	private String jenjang;
	
	@Column(name = "institusi", nullable = false, length = 50)
	private String institusi;
	
	@Column(name = "tahunmasuk", nullable = false, length = 10)
	private String tahunMasuk;
	
	@Column(name = "tahunlulus", nullable = false, length = 10)
	private String tahunLulus;

	public Integer getKodePendidikan() {
		return kodePendidikan;
	}

	public void setKodePendidikan(Integer kodePendidikan) {
		this.kodePendidikan = kodePendidikan;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getJenjang() {
		return jenjang;
	}

	public void setJenjang(String jenjang) {
		this.jenjang = jenjang;
	}

	public String getInstitusi() {
		return institusi;
	}

	public void setInstitusi(String institusi) {
		this.institusi = institusi;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public String getTahunLulus() {
		return tahunLulus;
	}

	public void setTahunLulus(String tahunLulus) {
		this.tahunLulus = tahunLulus;
	}
}
