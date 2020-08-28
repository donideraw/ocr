package com.doni.genbe.model.dto;

import java.util.Date;


public class GetDto {
	
	private String nik;
	private String name;
	private String address;
	private String hp;
	private Date tgl;
	private String tempatLahir;
	private String umur;
	private String pendidikan_terakhir;
	
	public String getNik() {
		return nik;
	}
	public void setNik(String nik) {
		this.nik = nik;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public Date getTgl() {
		return tgl;
	}
	public void setTgl(Date tgl) {
		this.tgl = tgl;
	}
	public String getTempatLahir() {
		return tempatLahir;
	}
	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}
	public String getUmur() {
		return umur;
	}
	public void setUmur(String umur) {
		this.umur = umur;
	}
	public String getPendidikan_terakhir() {
		return pendidikan_terakhir;
	}
	public void setPendidikan_terakhir(String pendidikan_terakhir) {
		this.pendidikan_terakhir = pendidikan_terakhir;
	}
}
