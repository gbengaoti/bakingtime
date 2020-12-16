package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    @SerializedName("steps")
    private List<Step> bakingSteps;
    @SerializedName("servings")
    private Integer noOfServings;
    @SerializedName("image")
    private String imageUrl;

    protected Recipe(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        bakingSteps = in.createTypedArrayList(Step.CREATOR);
        if (in.readByte() == 0) {
            noOfServings = null;
        } else {
            noOfServings = in.readInt();
        }
        imageUrl = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getBakingSteps() {
        return bakingSteps;
    }

    public void setBakingSteps(List<Step> bakingSteps) {
        this.bakingSteps = bakingSteps;
    }

    public Integer getNoOfServings() {
        return noOfServings;
    }

    public void setNoOfServings(int noOfServings) {
        this.noOfServings = noOfServings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeTypedList(ingredientList);
        parcel.writeTypedList(bakingSteps);
        if (noOfServings == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(noOfServings);
        }
        parcel.writeString(imageUrl);
    }
}
