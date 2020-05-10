package com.example.networking.model;

import java.util.List;

public class PlayerDatas {
    /*{
    "status": "true",
    "message": "Data fetched successfully!",
    "data": [ {
            "id": "1",
            "name": "Roger Federer",
            "country": "Switzerland",
            "city": "Basel",
            "imgURL": "https://demonuts.com/Demonuts/SampleImages/roger.jpg"
        },  */


   private String  status;
   private String  message;
   private List<Player> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Player> getData() {
        return data;
    }

    public void setData(List<Player> data) {
        this.data = data;
    }
}
