package com.example.gongguhaejo;

import java.io.Serializable;

public class MatchList implements Serializable {
    private String masterId; // 사용자 아이디
    private String rest_name; // 식당 이름
    private String mfood_name; // 고른 음식 메뉴
    private int food_price; // 메뉴 가격
    private int recru_person; // 모집 인원
    private int recru_time; // 모집 시간
    private int food_deliveryprice; // 배달비
    private String location; // 사용자 위치
    private String receive; // 수령 장소
    private String key;

    public MatchList() {}

}