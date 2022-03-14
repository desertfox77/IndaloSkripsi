package com.fathanleo.skripsi.utilities;

public class MyModel {
    String title, harga;
    int image;

    public MyModel(String title, String harga, int image) {
        this.title = title;
        this.harga = harga;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
