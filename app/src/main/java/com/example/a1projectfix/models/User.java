package com.example.a1projectfix.models;

public class User {
    private String nama;
    private String tempat;
    private String tanggal;
    private String tanggalBergabung;
    private String alamat;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(String tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    private String foto1;
    private String foto2;
    private String kelas;
    private String key;
    private int biaya;
    private boolean register;

    public User(String nama, String tempat, String tanggal, String tanggalBergabung, String alamat, String foto1, String foto2, String kelas, String key, int biaya, boolean register) {
        this.nama = nama;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.tanggalBergabung = tanggalBergabung;
        this.alamat = alamat;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.kelas = kelas;
        this.key = key;
        this.biaya = biaya;
        this.register = register;
    }

    public User(){
        
    }
}
