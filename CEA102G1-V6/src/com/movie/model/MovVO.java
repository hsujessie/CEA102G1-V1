package com.movie.model;

import java.sql.Date;

public class MovVO implements java.io.Serializable{
    private Integer movno;
    private String movname;
    private String movver;
    private String movtype;
    private String movlan;
    private Date movondate;
    private Date movoffdate;
    private Integer movdurat;
    private String movrating;
    private String movditor;
    private String movcast;
    private String movdes;
    private byte[] movpos;
    private byte[] movtra;
    private Integer movsatitotal;
    private Integer movsatipers;
    private Integer movexpetotal;
    private Integer movexpepers;
	
	public MovVO() {}

	public Integer getMovno() {
		return movno;
	}
	public void setMovno(Integer movno) {
		this.movno = movno;
	}
	public String getMovname() {
		return movname;
	}
	public void setMovname(String movname) {
		this.movname = movname;
	}
	public String getMovver() {
		return movver;
	}
	public void setMovver(String movver) {
		this.movver = movver;
	}
	public String getMovtype() {
		return movtype;
	}
	public void setMovtype(String movtype) {
		this.movtype = movtype;
	}
	public String getMovlan() {
		return movlan;
	}
	public void setMovlan(String movlan) {
		this.movlan = movlan;
	}
	public Date getMovondate() {
		return movondate;
	}
	public void setMovondate(Date movondate) {
		this.movondate = movondate;
	}
	public Date getMovoffdate() {
		return movoffdate;
	}
	public void setMovoffdate(Date movoffdate) {
		this.movoffdate = movoffdate;
	}
	public Integer getMovdurat() {
		return movdurat;
	}
	public void setMovdurat(Integer movdurat) {
		this.movdurat = movdurat;
	}
	public String getMovrating() {
		return movrating;
	}
	public void setMovrating(String movrating) {
		this.movrating = movrating;
	}
	public String getMovditor() {
		return movditor;
	}
	public void setMovditor(String movditor) {
		this.movditor = movditor;
	}
	public String getMovcast() {
		return movcast;
	}
	public void setMovcast(String movcast) {
		this.movcast = movcast;
	}
	public String getMovdes() {
		return movdes;
	}
	public void setMovdes(String movdes) {
		this.movdes = movdes;
	}
	public byte[] getMovpos() {
		return movpos;
	}
	public void setMovpos(byte[] movpos) {
		this.movpos = movpos;
	}
	public byte[] getMovtra() {
		return movtra;
	}
	public void setMovtra(byte[] movtra) {
		this.movtra = movtra;
	}
	public Integer getMovsatitotal() {
		return movsatitotal;
	}
	public void setMovsatitotal(Integer movsatitotal) {
		this.movsatitotal = movsatitotal;
	}
	public Integer getMovsatipers() {
		return movsatipers;
	}
	public void setMovsatipers(Integer movsatipers) {
		this.movsatipers = movsatipers;
	}
	public Integer getMovexpetotal() {
		return movexpetotal;
	}
	public void setMovexpetotal(Integer movexpetotal) {
		this.movexpetotal = movexpetotal;
	}
	public Integer getMovexpepers() {
		return movexpepers;
	}
	public void setMovexpepers(Integer movexpepers) {
		this.movexpepers = movexpepers;
	}
}
