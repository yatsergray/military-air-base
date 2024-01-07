package com.example.military.air.base.management.system.controller;

import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.MilitaryAirBaseDto;
import com.example.military.air.base.management.system.model.dto.editable.MilitaryAirBaseEditableDto;
import com.example.military.air.base.management.system.model.type.MilitaryAirBaseType;
import com.example.military.air.base.management.system.model.type.MilitaryAirBaseUse;
import com.example.military.air.base.management.system.service.impl.MilitaryAirBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("/military-air-bases")
public class MilitaryAirBaseController {
    private final MilitaryAirBaseServiceImpl militaryAirBaseService;

    @Autowired
    public MilitaryAirBaseController(MilitaryAirBaseServiceImpl militaryAirBaseService) {
        this.militaryAirBaseService = militaryAirBaseService;
    }

    @GetMapping("/create")
    public String createMilitaryAirBase(Model model) {
        model.addAttribute("uses", MilitaryAirBaseUse.values());
        model.addAttribute("types", MilitaryAirBaseType.values());
        model.addAttribute("militaryAirBase", new MilitaryAirBaseEditableDto());

        return "military_air_base/create-military-air-base";
    }

    @PostMapping("/create")
    public String createMilitaryAirBase(@ModelAttribute MilitaryAirBaseEditableDto militaryAirBaseEditableDto, @RequestParam("image") MultipartFile image, Model model) {
        try {
            militaryAirBaseService.addMilitaryAirBase(militaryAirBaseEditableDto, image);
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/military-air-bases";
    }

    @GetMapping("/{id}")
    public String readMilitaryAirBaseById(@PathVariable("id") Long id, Model model) {
        Optional<MilitaryAirBaseDto> militaryAirBaseDtoOptional;

        try {
            militaryAirBaseDtoOptional = militaryAirBaseService.getMilitaryAirBaseById(id);
        } catch (NoSuchMilitaryAirBaseException | DataFormatException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        militaryAirBaseDtoOptional.ifPresent(militaryAirBaseDto -> model.addAttribute("militaryAirBase", militaryAirBaseDto));

        return "military_air_base/read-military-air-base";
    }

    @GetMapping
    public String readAllMilitaryAirBases(Model model) {
        model.addAttribute("allMilitaryAirBases", militaryAirBaseService.getAllMilitaryAirBases());

        return "military_air_base/read-all-military-air-bases";
    }

    @GetMapping("/update/{id}")
    public String updateMilitaryAirBaseById(@PathVariable("id") Long id, Model model) {
        Optional<MilitaryAirBaseEditableDto> militaryAirBaseEditableDtoOptional;

        try {
            militaryAirBaseEditableDtoOptional = militaryAirBaseService.getMilitaryAirBaseEditableById(id);
        } catch (NoSuchMilitaryAirBaseException | DataFormatException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        if (militaryAirBaseEditableDtoOptional.isPresent()) {
            model.addAttribute("uses", MilitaryAirBaseUse.values());
            model.addAttribute("types", MilitaryAirBaseType.values());
            model.addAttribute("militaryAirBase", militaryAirBaseEditableDtoOptional.get());
        }

        return "military_air_base/update-military-air-base";
    }

    @PostMapping("/update/{id}")
    public String updateMilitaryAirBaseById(@PathVariable("id") Long id, @ModelAttribute MilitaryAirBaseEditableDto militaryAirBaseEditableDto, @RequestParam("image") MultipartFile image, Model model) {
        try {
            militaryAirBaseService.modifyMilitaryAirBaseById(id, militaryAirBaseEditableDto, image);
        } catch (NoSuchMilitaryAirBaseException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/military-air-bases";
    }

    @GetMapping("/delete/{id}")
    public String deleteMilitaryAirBaseById(@PathVariable("id") Long id, Model model) {
        try {
            militaryAirBaseService.removeMilitaryAirBaseById(id);
        } catch (NoSuchMilitaryAirBaseException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/military-air-bases";
    }

    @RequestMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Optional<MilitaryAirBaseDto> militaryAirBaseDtoOptional;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        try {
            militaryAirBaseDtoOptional = militaryAirBaseService.getMilitaryAirBaseById(id);
        } catch (NoSuchMilitaryAirBaseException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException | DataFormatException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return militaryAirBaseDtoOptional
                .map(militaryAirBaseDto -> new ResponseEntity<>(militaryAirBaseDto.getImageData(), headers, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
