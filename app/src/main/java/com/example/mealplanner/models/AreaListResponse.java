package com.example.mealplanner.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaListResponse {
    @SerializedName("meals")
    private List<AreaName> areaNames;

    public List<AreaName> getAreaNames() { return areaNames; }
    public void setAreaNames(List<AreaName> value) { this.areaNames = value; }
}

