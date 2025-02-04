package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:38 PM
@Last Modified 1/30/2025 6:38 PM
Version 1.0
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespGroupMenuDTO;
import com.example.biliosphere2.dto.validasi.ValGroupMenuDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.GroupMenu;
import com.example.biliosphere2.model.LogGroupMenu;
import com.example.biliosphere2.repository.GroupMenuRepo;
import com.example.biliosphere2.repository.LogGroupMenuRepo;
import com.example.biliosphere2.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Platform Code : AUT
 * Modul Code : 01
 * FV = Failed Validation
 * FE = Failed Error
 */
@Service
public class GroupMenuService implements IService<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private LogGroupMenuRepo logGroupMenuRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private StringBuilder sbuild = new StringBuilder();


    @Override
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {
        try{
            if(groupMenu==null){
                return GlobalResponse.dataTidakValid("FVAUT01001",request);
            }
            groupMenu.setCreatedBy("Paul");
            groupMenu.setCreatedDate(new Date());
            GroupMenu g = groupMenuRepo.save(groupMenu);

            LogGroupMenu logGroupMenu = new LogGroupMenu();
            logGroupMenu.setIdGroupMenu(g.getId());
            logGroupMenu.setNama(g.getNamaGroupMenu());
            logGroupMenu.setCreatedBy(g.getCreatedBy());
            logGroupMenu.setCreatedDate(new Date());
            logGroupMenuRepo.save(logGroupMenu);

        }catch (Exception e){
            LoggingFile.logException("GroupMenuService","save --> Line 42",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT01001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {
        try{
            if(groupMenu==null){
                return GlobalResponse.dataTidakValid("FVAUT01011",request);
            }
            Optional<GroupMenu> groupMenuOptional = groupMenuRepo.findById(id);
            if(!groupMenuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            GroupMenu groupMenuDB = groupMenuOptional.get();
            groupMenuDB.setUpdatedBy("erlan");
            groupMenuDB.setUpdatedDate(new Date());
            groupMenuDB.setNamaGroupMenu(groupMenu.getNamaGroupMenu());

        }catch (Exception e){
            LoggingFile.logException("GroupMenuService","update --> Line 75",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT01011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try{
            Optional<GroupMenu> groupMenuOptional = groupMenuRepo.findById(id);
            if(!groupMenuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            groupMenuRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException("GroupMenuService","delete --> Line 111",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT01021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        page = groupMenuRepo.findAll(pageable);
        list = page.getContent();
        List<RespGroupMenuDTO> listDTO = convertToListRespGroupMenuDTO(list);

        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }

        Map<String, Object> mapList = transformPagination.transformPagination(listDTO,page,"id","");
        return GlobalResponse.dataResponseList(mapList,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespGroupMenuDTO respGroupMenuDTO;
        try{
            Optional<GroupMenu> groupMenuOptional = groupMenuRepo.findById(id);
            if(!groupMenuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            GroupMenu groupMenuDB = groupMenuOptional.get();
            respGroupMenuDTO = modelMapper.map(groupMenuDB, RespGroupMenuDTO.class);
        }catch (Exception e){
            LoggingFile.logException("GroupMenuService","findById --> Line 162",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT01041",request);
        }
        return new ResponseHandler().handleResponse("OK",
                HttpStatus.OK,
                respGroupMenuDTO,null,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        switch(columnName){

            case "nama": page = groupMenuRepo.findByNamaGroupMenuContainsIgnoreCase(pageable,value);break;
            default : page = groupMenuRepo.findAll(pageable);break;
        }
        list = page.getContent();
        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespGroupMenuDTO> listDTO = convertToListRespGroupMenuDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO,page,columnName,value);
        return GlobalResponse.dataResponseList(mapList,request);
    }

    public List<RespGroupMenuDTO> convertToListRespGroupMenuDTO(List<GroupMenu> groupMenuList){
        return modelMapper.map(groupMenuList,new TypeToken<List<RespGroupMenuDTO>>(){}.getType());
    }

    public GroupMenu convertToListRespGroupMenuDTO(ValGroupMenuDTO groupMenuDTO){
        return modelMapper.map(groupMenuDTO,GroupMenu.class);
    }


}

