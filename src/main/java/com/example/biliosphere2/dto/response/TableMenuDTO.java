package com.example.biliosphere2.dto.response;


public class TableMenuDTO {
    private Long id;
    private String nama;
    private String path;
    private String namaGroupMenu;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getNamaGroupMenu() {
        return namaGroupMenu;
    }
    public void setNamaGroupMenu(String namaGroupMenu) {
        this.namaGroupMenu = namaGroupMenu;
    }
}
