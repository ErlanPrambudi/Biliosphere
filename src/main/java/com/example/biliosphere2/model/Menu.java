package com.example.biliosphere2.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstMenu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMenu")
    private Long id;

    @Column(name = "NamaMenu",length = 40,nullable = false,unique = true)
    private String nama;

    @Column(name = "Path",length = 40,nullable = false,unique = true)
    private String path;

    @ManyToMany(mappedBy = "menuList")
    @JsonBackReference
    private List<Akses> aksesList;

    @ManyToOne
    @JoinColumn(name = "IDGroupMenu",foreignKey = @ForeignKey(name = "fk-to-group-menu"))
    private GroupMenu groupMenu;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private String createdBy;

    @Column(name = "CreatedDate",updatable = false,nullable = false)
    private Date createdDate = new Date();

    @Column(name = "UpdatedBy",insertable = false)
    private String updatedBy;

    @Column(name = "UpdatedDate",insertable = false)
    private Date updatedDate;

    // Update getter dan setter untuk aksesList
    public List<Akses> getAksesList() {
        return aksesList;
    }

    public void setAksesList(List<Akses> aksesList) {
        this.aksesList = aksesList;
    }

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

    public GroupMenu getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(GroupMenu groupMenu) {
        this.groupMenu = groupMenu;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
