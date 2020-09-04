package com.sundaypark.factory.ndcl.retrofit.pojo

import java.util.*

/**
 *
 *
"coursename": "미술심리상담",// 강좌명
"courseteachername": "이재란",// 강사명
"edutarget": "지역주민",// 교육대상구분
"receptionstart": "2017-08-06", // 접수 시작
"receptionend": "2017-08-10",   // 접수종료
"trainingstartString": "2017-09-03",// 교육시작일자
"trainingstarttime": "10:00:00", // 교육시작일자
"trainingEndString": "2017-12-03",// 교육종료일자
"trainingendtime": "12:00:00",// 교육종료일자
"edumthod": "오프라인",// 교육방법구분
"bankingevaluation": 0, //학점은행제평가(학점)인정여부
"developmenttraining": 0,// 직업능력개발훈련비지원강좌여부
"accountevaluation": 0,// 평생학습계좌제평가인정여부
"receptionmethod": "인터넷·방문접수",// 접수방법구분
"receptionselection": "선착순",// 선정방법구분
"trainingplace": "레인보우영동도서관",// 교육장소
"opername": "영동군청 생활지원과",;// 운영기관명
"opernumber": "043-740-3772",// 운영기관전화번호
"providername": "충청북도 영동군",// 제공기관명
"roaaddress": "충청북도 영동군 영동읍 계산로 2길 25",// 교육장도로명주소
"idcourses": 493953,
"operatingday": "월요일",// 운영요일
"maxstudents": 20,// 강좌정원수
"fee": 0,// 수강료



"content": "미술로 알아보는 마음상태",// 강좌내용


"databaseString": "2018-02-22",// 데이터기준일자














"homepage": "https://www.rainbowlib.go.kr/"// 홈페이지주소
 */


data class NewCourses(
    val coursename: String?,
    val courseteachername: String?,
    val trainingstartString: String?,
    val trainingEndString: String?,
    val trainingstarttime: String?,
    val trainingendtime: String?,
    val content: String?,
    val edutarget: String?,
    val edumthod: String?,
    val operatingday: String?,
    val trainingplace: String?,
    val maxstudents: String?,
    val fee: Int?,
    val roaaddress: String?,
    val opernumber: String?,
    val receptionstart: String?,
    val receptionend: String?,
    val receptionmethod: String?,
    val receptionselection: String?,
    val homepage: String?,
    val developmenttraining: Int?,
    val bankingevaluation: Int?,
    val accountevaluation: Int?,
    val databaseString: String?,
    val providerpode: String?,
    val providername: String?,
val opername : String?,
    val idcourses:String?
)