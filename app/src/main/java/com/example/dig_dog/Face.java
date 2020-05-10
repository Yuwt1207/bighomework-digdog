package com.example.dig_dog;

public class Face {
    private String faceName;
    private String faceURI;

    public  Face(String faceName,String faceURI)
    {
        this.faceName=faceName;
        this.faceURI=faceURI;
    }

    public  String getName()
    {
        return faceName;
    }

    public  String getURI()
    {
        return faceURI;
    }
}
