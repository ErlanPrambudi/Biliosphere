package com.example.biliosphere2.service;

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespGroupMenuDTO;
import com.example.biliosphere2.dto.response.RespMenuDTO;
import com.example.biliosphere2.dto.response.TableMenuDTO;
import com.example.biliosphere2.dto.validasi.ValMenuDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.GroupMenu;
import com.example.biliosphere2.model.Menu;
import com.example.biliosphere2.repository.MenuRepo;
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
 * Modul Code : 02
 * FV = Failed Validation
 * FE = Failed Error
 */
@Service
public class MenuService implements IService<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private StringBuilder sbuild = new StringBuilder();


    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        try{
            if(menu==null){
                return GlobalResponse.dataTidakValid("FVAUT02001",request);
            }
            menu.setCreatedBy("erlan");
            menu.setCreatedDate(new Date());
            menuRepo.save(menu);
        }catch (Exception e){
            LoggingFile.logException("MenuService","save --> Line 42",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT02001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        try{
            if(menu==null){
                return GlobalResponse.dataTidakValid("FVAUT02011",request);
            }
            Optional<Menu> menuOptional = menuRepo.findById(id);
            if(!menuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Menu menuDB = menuOptional.get();
            menuDB.setUpdatedBy("Erlan");
            menuDB.setUpdatedDate(new Date());
            menuDB.setNama(menu.getNama());
            menuDB.setPath(menu.getPath());
            menuDB.setGroupMenu(menu.getGroupMenu());//ini relasi nya

        }catch (Exception e){
            LoggingFile.logException("MenuService","update --> Line 75",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try{
            Optional<Menu> menuOptional = menuRepo.findById(id);
            if(!menuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            menuRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException("MenuService","delete --> Line 111",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        page = menuRepo.findAll(pageable);
        list = page.getContent();
        //List<RespMenuDTO> listDTO = convertToListRespMenuDTO(list);
        List<TableMenuDTO> listDTO = convertToTableMenuDTO(list);

        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }

        Map<String, Object> mapList = transformPagination.transformPagination(listDTO,page,"id","");
        return GlobalResponse.dataResponseList(mapList,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespMenuDTO respMenuDTO;
        try{
            Optional<Menu> menuOptional = menuRepo.findById(id);
            if(!menuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Menu menuDB = menuOptional.get();
            respMenuDTO = modelMapper.map(menuDB, RespMenuDTO.class);
        }catch (Exception e){
            LoggingFile.logException("MenuService","findById --> Line 162",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT02041",request);
        }
        return new ResponseHandler().handleResponse("OK",
                HttpStatus.OK,
                respMenuDTO,null,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        switch(columnName){

            case "nama": page = menuRepo.findByNamaContainsIgnoreCase(pageable,value);break;
            case "group": page = menuRepo.cariGroupMenu(pageable,value);break;
            default : page = menuRepo.findAll(pageable);break;
        }
        list = page.getContent();
        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespMenuDTO> listDTO = convertToListRespMenuDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO,page,columnName,value);
        return GlobalResponse.dataResponseList(mapList,request);
    }

    public List<RespMenuDTO> convertToListRespMenuDTO(List<Menu> menuList){
        return modelMapper.map(menuList,new TypeToken<List<RespMenuDTO>>(){}.getType());
    }

    public Menu convertToMenu(ValMenuDTO menuDTO){
        return modelMapper.map(menuDTO,Menu.class);
    }
    public List<TableMenuDTO> convertToTableMenuDTO(List<Menu> menuList){
        List<TableMenuDTO> list = new ArrayList<>();
        TableMenuDTO tableMenuDTO ;
        for(Menu menu : menuList){
            tableMenuDTO = new TableMenuDTO();
            tableMenuDTO.setId(menu.getId());
            tableMenuDTO.setNama(menu.getNama());
            tableMenuDTO.setPath(menu.getPath());
            tableMenuDTO.setNamaGroupMenu(menu.getGroupMenu()==null?"":menu.getGroupMenu().getNamaGroupMenu());
            list.add(tableMenuDTO);
        }
        return list;
    }
}
