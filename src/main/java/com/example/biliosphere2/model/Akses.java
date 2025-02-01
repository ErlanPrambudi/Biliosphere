package com.example.biliosphere2.model;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstAkses")
public class Akses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namaAkses", nullable = false, unique = true, length = 50)
    private String nama;

    @OneToMany(mappedBy = "akses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name = "MstAksesMenu",
            joinColumns = @JoinColumn(name = "AksesID"),
            inverseJoinColumns = @JoinColumn(name = "MenuID")
    )
    private List<Menu> menuList;

    @Column(name = "createdBy", updatable = false, nullable = false)
    private String createdBy;

    @Column(name = "createdDate", updatable = false, nullable = false)
    private Date createdDate = new Date();

    @Column(name = "updatedBy", insertable = false)
    private String updatedBy;

    @Column(name = "updatedDate", insertable = false)
    private Date updatedDate;

    // Tambahkan getter dan setter untuk menuList
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaAkses() {
        return nama;
    }

    public void setNamaAkses(String namaAkses) {
        this.nama = namaAkses;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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