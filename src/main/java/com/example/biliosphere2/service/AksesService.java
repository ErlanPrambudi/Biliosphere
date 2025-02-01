package com.example.biliosphere2.service;

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespAksesDTO;
import com.example.biliosphere2.dto.validasi.ValAksesDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Akses;
import com.example.biliosphere2.repository.AksesRepo;
import com.example.biliosphere2.util.*;
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
public class AksesService implements IService<Akses> {

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformPagination transformPagination;

    private StringBuilder sbuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request) {
        Map<String,Object> token = GlobalFunction.extractToken(request);
        try{
            if(akses == null){
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }
            akses.setCreatedBy(token.get("nm").toString());
            akses.setCreatedDate(new Date());
            akses.setMenuList(akses.getMenuList());

            aksesRepo.save(akses);
        }catch (Exception e){
            LoggingFile.logException("AksesService","save --> Line 42", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {
        Map<String,Object> token = GlobalFunction.extractToken(request);
        try{
            if(akses == null){
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }
            Optional<Akses> aksesOptional = aksesRepo.findById(id);
            if(!aksesOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Akses aksesDB = aksesOptional.get();
            aksesDB.setUpdatedBy(token.get("userId").toString());
            aksesDB.setUpdatedDate(new Date());
            aksesDB.setNamaAkses(akses.getNamaAkses());
            aksesDB.setMenuList(akses.getMenuList());

        }catch (Exception e){
            LoggingFile.logException("AksesService","update --> Line 75", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> token = GlobalFunction.extractToken(request);
        try{
            Optional<Akses> aksesOptional = aksesRepo.findById(id);
            if(!aksesOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            aksesRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException("AksesService","delete --> Line 111", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Akses> page = aksesRepo.findAll(pageable);
        List<Akses> list = page.getContent();
        List<RespAksesDTO> listDTO = convertToListRespAksesDTO(list);

        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }

        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespAksesDTO respAksesDTO;
        try{
            Optional<Akses> aksesOptional = aksesRepo.findById(id);
            if(!aksesOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Akses aksesDB = aksesOptional.get();
            respAksesDTO = modelMapper.map(aksesDB, RespAksesDTO.class);
        }catch (Exception e){
            LoggingFile.logException("AksesService","findById --> Line 162", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT03041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respAksesDTO, null, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Akses> page;
        List<Akses> list;
        switch(columnName){
            case "nama": page = aksesRepo.findByNamaContainsIgnoreCase(pageable, value); break;
            default: page = aksesRepo.findAll(pageable); break;
        }
        list = page.getContent();
        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespAksesDTO> listDTO = convertToListRespAksesDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }

    public List<RespAksesDTO> convertToListRespAksesDTO(List<Akses> aksesList){
        return modelMapper.map(aksesList, new TypeToken<List<RespAksesDTO>>(){}.getType());
    }

    public Akses convertToAkses(ValAksesDTO aksesDTO){
        return modelMapper.map(aksesDTO, Akses.class);
    }
}
