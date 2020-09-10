package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredient> ingredientList = null;
    @SerializedName("steps")
    private ArrayList<Step> bakingSteps = null;
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
//        this.ingredientList = new ArrayList<>();
//        in.readList(this.ingredientList, Ingredient.class.getClassLoader());
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
//        this.bakingSteps = new ArrayList<>();
//        in.readList(this.bakingSteps, Step.class.getClassLoader());
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

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<Step> getBakingSteps() {
        return bakingSteps;
    }

    public void setBakingSteps(ArrayList<Step> bakingSteps) {
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
        parcel.writeList(ingredientList);
        parcel.writeList(bakingSteps);
        if (noOfServings == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(noOfServings);
        }
        parcel.writeString(imageUrl);
    }
}
