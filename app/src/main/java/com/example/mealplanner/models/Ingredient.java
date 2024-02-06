package com.example.mealplanner.models;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;

    public String getIDIngredient() { return idIngredient; }
    public void setIDIngredient(String value) { this.idIngredient = value; }

    public String getStrIngredient() { return strIngredient; }
    public void setStrIngredient(String value) { this.strIngredient = value; }

    public String getStrDescription() { return strDescription; }
    public void setStrDescription(String value) { this.strDescription = value; }

    public String getStrType() { return strType; }
    public void setStrType(String value) { this.strType = value; }
}

