package com.samsung.galaxy_babies.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Baby implements Serializable {

    public enum Gender{
        NONE,
        MEN,
        WOMEN
    }

    private String id;
    private String firstName;
    private String lastName;
    private Calendar birthday;
    private Gender gender;


    private ArrayList<Measure> heights;
    private ArrayList<Measure> weights;

    private String profileImg;


    public Baby() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baby baby = (Baby) o;
        return id.equals(baby.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName(){
        return String.format("%s%s", this.lastName, this.firstName);
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public int getMonthDiff(){
        Calendar current = Calendar.getInstance();
        int month1 = this.birthday.get(Calendar.YEAR) * 12 + this.birthday.get(Calendar.MONTH);
        int month2 = current.get(Calendar.YEAR) * 12 + current.get(Calendar.MONTH);

        return month2 - month1;
    }

    public String getDescription(){
        return String.format("%d년 %d월 %d일 (%s개월)", this.birthday.get(Calendar.YEAR), this.birthday.get(Calendar.MONTH)+1, this.birthday.get(Calendar.DAY_OF_MONTH), BabyData.getAge(this.birthday, Calendar.getInstance()));
    }


    public ArrayList<Measure> getHeights() {
        return heights;
    }

    public void setHeights(ArrayList<Measure> heights) {
        this.heights = heights;
    }

    public ArrayList<Measure> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Measure> weights) {
        this.weights = weights;
    }

    public String getHeightString(){
        return String.format("%.1f", this.getHeights().get(this.getHeights().size()-1).value);
    }

    public String getWeightString(){
        return String.format("%.1f", this.getWeights().get(this.getWeights().size()-1).value);
    }

    public double getLastWeight(){
        return this.getWeights().get(this.getWeights().size()-1).value;
    }

    public double getLastHeight(){
        return this.getHeights().get(this.getHeights().size()-1).value;
    }



    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
