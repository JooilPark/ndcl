package com.park.factory.ndlc.db.entitys;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class courses {
	SimpleDateFormat toSqlDate = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat toSqlTime = new SimpleDateFormat("HH:mm");

	public int getIdcourses() {
		return idcourses;
	}

	public void setIdcourses(int idcourses) {
		this.idcourses = idcourses;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCourseteachername() {
		return courseteachername;
	}

	public void setCourseteachername(String courseteachername) {
		this.courseteachername = courseteachername;
	}

	public Date getTrainingstartdate() {
		return trainingstartdate;
	}

	public void setTrainingstartdate(Date trainingstartdate) {
		this.trainingstartdate = trainingstartdate;
	}

	public void setTrainingstartdate(String trainingstartdate) throws ParseException {
		this.trainingstartdate = toSqlDate.parse(trainingstartdate);
	}

	public Date getTrainingEnddate() {
		return trainingEnddate;
	}

	public void setTrainingEnddate(Date trainingEnddate) {
		this.trainingEnddate = trainingEnddate;
	}

	public void setTrainingEnddate(String trainingEnddate) throws ParseException {
		this.trainingEnddate = toSqlDate.parse(trainingEnddate);
	}

	public Time getTrainingstarttime() {
		return trainingstarttime;
	}

	public void setTrainingstarttime(Time trainingstarttime) {
		this.trainingstarttime = trainingstarttime;
	}

	public void setTrainingstarttime(String trainingstarttime) throws ParseException {
		
		if(trainingstarttime.isEmpty()){
			this.trainingstarttime =  new Time(toSqlTime.parse("00:00").getTime());
		}else {
			this.trainingstarttime = new Time(toSqlTime.parse(trainingstarttime.trim()).getTime());
		}
		
		
	}

	public Time getTrainingendtime() {
		return trainingendtime;
	}

	public void setTrainingendtime(Time trainingendtime) {
		this.trainingendtime = trainingendtime;
	}

	public void setTrainingendtime(String trainingendtime) throws ParseException {
		
		
		if(trainingendtime.isEmpty()){
			this.trainingendtime =  new Time(toSqlTime.parse("00:00").getTime());
		}else {
			this.trainingendtime = new Time(toSqlTime.parse(trainingendtime.trim()).getTime());
		}

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEdutarget() {
		return edutarget;
	}

	public void setEdutarget(String edutarget) {
		this.edutarget = edutarget;
	}

	public String getEdumthod() {
		return edumthod;
	}

	public void setEdumthod(String edumthod) {
		this.edumthod = edumthod;
	}

	public String getOperatingday() {
		return operatingday;
	}

	public void setOperatingday(String operatingday) {
		this.operatingday = operatingday;
	}

	public String getTrainingplace() {
		return trainingplace;
	}

	public void setTrainingplace(String trainingplace) {
		this.trainingplace = trainingplace;
	}

	public int getMaxstudents() {
		return maxstudents;
	}

	public void setMaxstudents(int maxstudents) {
		this.maxstudents = maxstudents;
	}

	public void setMaxstudents(String maxstudents) {
		this.maxstudents = Integer.parseInt(maxstudents.trim());
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public void setFee(String fee) {
		this.fee = Integer.parseInt(fee.trim());
		;
	}

	public String getRoaaddress() {
		return roaaddress;
	}

	public void setRoaaddress(String roaaddress) {
		this.roaaddress = roaaddress;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getOpernumber() {
		return opernumber;
	}

	public void setOpernumber(String opernumber) {
		this.opernumber = opernumber;
	}

	public Date getReceptionstart() {
		return receptionstart;
	}

	public void setReceptionstart(Date receptionstart) {
		this.receptionstart = receptionstart;
	}

	public void setReceptionstart(String receptionstart) throws ParseException {
		if(receptionstart.isEmpty()){
			this.receptionstart = toSqlDate.parse("2000-01-01");
		}else {
			this.receptionstart = toSqlDate.parse(receptionstart);	
		}
		
	}

	public Date getReceptionend() {
		return receptionend;
	}

	public void setReceptionend(Date receptionend) {
		this.receptionend = receptionend;
	}

	public void setReceptionend(String receptionend) throws ParseException {
		if(receptionend.isEmpty()){
			this.receptionend = toSqlDate.parse("2000-01-01");
		}else {
			this.receptionend = toSqlDate.parse(receptionend);	
		}
		
		
	}

	public String getReceptionmethod() {
		return receptionmethod;
	}

	public void setReceptionmethod(String receptionmethod) {
		this.receptionmethod = receptionmethod;
	}

	public String getReceptionselection() {
		return receptionselection;
	}

	public void setReceptionselection(String receptionselection) {
		this.receptionselection = receptionselection;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public boolean isDevelopmenttraining() {
		return developmenttraining;
	}

	public void setDevelopmenttraining(boolean developmenttraining) {
		this.developmenttraining = developmenttraining;
	}
	public void setDevelopmenttraining(String developmenttraining) {
		this.developmenttraining = (developmenttraining.trim().toLowerCase().equals("Y"));
	}
	public boolean isBankingevaluation() {
		return bankingevaluation;
	}

	public void setBankingevaluation(boolean bankingevaluation) {
		this.bankingevaluation = bankingevaluation;
	}
	public void setBankingevaluation(String bankingevaluation) {
		this.bankingevaluation = (bankingevaluation.trim().toLowerCase().equals("Y"));;
	}

	public boolean isAccountevaluation() {
		return accountevaluation;
	}

	public void setAccountevaluation(boolean accountevaluation) {
		this.accountevaluation = accountevaluation;
	}
	public void setAccountevaluation(String accountevaluation) {
		this.accountevaluation =  (accountevaluation.trim().toLowerCase().equals("Y"));;;
	}

	public Date getDatabasedate() {
		return databasedate;
	}

	public void setDatabasedate(Date databasedate) {
		this.databasedate = databasedate;
	}

	public void setDatabasedate(String databasedate) throws ParseException {
		this.databasedate = toSqlDate.parse(databasedate);
	}

	public String getProviderpode() {
		return providerpode;
	}

	public void setProviderpode(String providerpode) {
		this.providerpode = providerpode;
	}

	public String getProvidername() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername;
	}

	private int idcourses;
	private String coursename;// 강좌명
	private String courseteachername;// 강사명
	private Date trainingstartdate;// 교육시작일자
	private Date trainingEnddate;// 교육종료일자
	private Time trainingstarttime;// 교육시작시각
	private Time trainingendtime;// 교육종료시각
	private String content;// 강좌내용
	private String edutarget;// 교육대상구분
	private String edumthod;// 교육방법구분
	private String operatingday;// 운영요일
	private String trainingplace;// 교육장소
	private int maxstudents;// 강좌정원수
	private int fee;// 수강료
	private String roaaddress;// 교육장도로명주소
	private String opername;// 운영기관명
	private String opernumber;// 운영기관전화번호
	private Date receptionstart;// 접수시작일자
	private Date receptionend;// 접수종료일자
	private String receptionmethod;// 접수방법구분
	private String receptionselection;// 선정방법구분
	private String homepage = "없음";// 홈페이지주소
	private boolean developmenttraining = false;// 직업능력개발훈련비지원강좌여부
	private boolean bankingevaluation = false;// 학점은행제평가(학점)인정여부
	private boolean accountevaluation = false;// 평생학습계좌제평가인정여부
	private Date databasedate;// 데이터기준일자
	private String providerpode;// 제공기관코드
	private String providername;// 제공기관명

}
