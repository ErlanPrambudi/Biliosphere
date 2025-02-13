package com.example.biliosphere2.service;

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespUserDTO;
import com.example.biliosphere2.dto.response.TableUserDTO;
import com.example.biliosphere2.dto.validasi.ValUserDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.User;
import com.example.biliosphere2.model.Akses;
import com.example.biliosphere2.security.BcryptImpl;
import java.time.format.DateTimeFormatter;
import com.example.biliosphere2.repository.UserRepo;
import com.example.biliosphere2.util.GlobalResponse;
import com.example.biliosphere2.util.LoggingFile;
import com.example.biliosphere2.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements IService<User> {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;


    @Override
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
        try {
            if (user == null) {
                return GlobalResponse.dataTidakValid("FVAUT04001", request);
            }
            if(user.getAkses()==null){
                Akses akses = new Akses();
                akses.setId(2L);
                user.setAkses(akses);
            }
            user.setRegistered(true);
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            user.setCreatedBy("erlan");
            user.setCreatedDate(new Date());
            System.out.println("DEBUG - Data yang akan disimpan:");
            System.out.println("Nama: " + user.getNama());
            System.out.println("Tanggal Lahir: " + user.getTanggalLahir()); // 🔍 Cek apakah null
            System.out.println("Akses ID: " + (user.getAkses() != null ? user.getAkses().getId() : "NULL"));



            userRepo.save(user);

        } catch (Exception e) {
            LoggingFile.logException("UserService", "save --> Line 42", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT04001", request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
        try {
            if (user == null) {
                return GlobalResponse.dataTidakValid("FVAUT04011", request);
            }
            Optional<User> userOptional = userRepo.findById(id);
            if (!userOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            User userDB = userOptional.get();
            userDB.setUpdatedBy("erlan");
            userDB.setUpdatedDate(new Date());
            userDB.setNama(user.getNama());
            userDB.setAlamat(user.getAlamat());
            userDB.setTanggalLahir(user.getTanggalLahir());
            userDB.setNoHp(user.getNoHp());
            userDB.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));;
            userDB.setAkses(user.getAkses()); // ini relasi nya
        } catch (Exception e) {
            LoggingFile.logException("UserService", "update --> Line 75", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT04011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<User> userOptional = userRepo.findById(id);
            if (!userOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            userRepo.deleteById(id);
        } catch (Exception e) {
            LoggingFile.logException("UserService", "delete --> Line 111", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT04021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        page = userRepo.findAll(pageable);
        list = page.getContent();
        //List<RespUserDTO> listDTO = convertToListRespUserDTO(list);
        List<TableUserDTO> listDTO = convertToTableUserDTO(list);

        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }

        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespUserDTO respUserDTO;
        try {
            Optional<User> userOptional = userRepo.findById(id);
            if (!userOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            User userDB = userOptional.get();
            respUserDTO = modelMapper.map(userDB, RespUserDTO.class);
        } catch (Exception e) {
            LoggingFile.logException("UserService", "findById --> Line 162", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT04041", request);
        }
        return new ResponseHandler().handleResponse("OK",
                HttpStatus.OK,
                respUserDTO, null, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        switch (columnName) {
            case "nama":
                page = userRepo.findByNamaContainsIgnoreCase(pageable, value);
                break;
            case "nohp":
                page = userRepo.findByNoHpContainsIgnoreCase(pageable, value);
                break;
            case "alamat":
                page = userRepo.findByAlamatContainsIgnoreCase(pageable, value);
                break;
            case "email":
                page = userRepo.findByEmailContainsIgnoreCase(pageable, value);
                break;
            default:
                page = userRepo.findAll(pageable);
                break;
        }
        list = page.getContent();
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        //List<RespUserDTO> listDTO = convertToListRespUserDTO(list);
        List<TableUserDTO> listDTO = convertToTableUserDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }

    public List<RespUserDTO> convertToListRespUserDTO(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<RespUserDTO>>() {
        }.getType());
    }

    public User convertToUser(ValUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public List<TableUserDTO> convertToTableUserDTO(List<User> userList){
        List<TableUserDTO> tableUserDTOList = new ArrayList<>();
        TableUserDTO tableUserDTO = null;
        for(User user : userList){
            tableUserDTO = new TableUserDTO();
            tableUserDTO.setId(user.getId());
            tableUserDTO.setNamaAkses(user.getAkses()==null?"":user.getAkses().getNamaAkses());
            tableUserDTO.setNoHp(user.getNoHp());
            tableUserDTO.setAlamat(user.getAlamat());
            tableUserDTO.setEmail(user.getEmail());
            tableUserDTO.setTanggalLahir(user.getTanggalLahir());
            tableUserDTO.setPassword(user.getPassword());
            tableUserDTO.setUsername(user.getUsername());
            tableUserDTO.setNama(user.getNama());
            tableUserDTOList.add(tableUserDTO);
        }
        return tableUserDTOList;
    }
}
