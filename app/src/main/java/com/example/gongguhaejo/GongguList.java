package com.example.gongguhaejo;

import java.time.LocalDateTime;
import java.io.Serializable;

public class GongguList implements Serializable {
    private String userId; // 사용자 아이디
    private String rest_name; // 식당 이름
    private String food_cate; // 음식 카테고리
    private String food_cate_image;// 음식 카테고리 이미지
    private String food_name; // 고른 음식 메뉴
    private int food_price; // 메뉴 가격
    private int recru_person; // 모집 인원
    private int recru_time; // 모집 시간
    private int food_deliveryprice; // 배달비
    private String location; // 사용자 위치
    private String receive; // 수령 장소

    public GongguList() {}

    public String getId() {
        // ID를 생성하여 반환하는 로직 작성
        return rest_name.replace(" ", "_").toLowerCase();
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getFood_cate() {
        return food_cate;
    }

    public void setFood_cate(String food_cate) {
        this.food_cate = food_cate;
    }

    public String getFood_cate_image() {
        return food_cate_image;
    }

    public void setFood_cate_image(String food_cate_image) {
        this.food_cate_image = food_cate_image;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setRecru_person(int recru_person) {
        this.recru_person = recru_person;
    }

    public int getRecru_person() {
        return recru_person;
    }
    public void setRecru_time(int recru_time) {
        this.recru_time = recru_time;
    }

    public int getRecru_time() {
        return recru_time;
    }
    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public int getFood_deliveryprice() {
        return food_deliveryprice;
    }

    public void setFood_deliveryprice(int food_deliveryprice) {
        this.food_deliveryprice = food_deliveryprice;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}