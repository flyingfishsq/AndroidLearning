package com.bignerdranch.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Boolean mSolved = false;

    //默认构造方法为每个实例化对象设置个随机UUID作为ID
    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public Boolean getSolved() {
        return mSolved;
    }

    public void setSolved(Boolean mSolved) {
        this.mSolved = mSolved;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());//是一个long型的数据
        return json;
    }

    public Crime(JSONObject json)throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
    }

}
