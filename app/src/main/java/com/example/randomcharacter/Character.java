package com.example.randomcharacter;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class Character
{
    public String name;
    public String species;
    public String gender;
    public String origin;
    public String location;
    public String status;
    public String image;

    public Character(String name, String species, String gender, String origin, String location, String status, String image)
    {
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.status = status;
        this.image = image;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return String.format("Name: %s\nSpecies: %s\nGender: %s\nOrigin: %s\nLocation: %s\nStatus: %s",
                name, species, gender, origin, location, status);
    }
}
