package com.iqeramobile.development.sakaiclient20.models.sakai.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse implements Serializable {

    @SerializedName("userId")
    @Expose
    public String userId;

}
