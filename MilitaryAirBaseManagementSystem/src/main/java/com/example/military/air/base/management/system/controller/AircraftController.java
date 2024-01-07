package com.example.military.air.base.management.system.controller;

import com.example.military.air.base.management.system.exception.NoSuchAircraftException;
import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.AircraftDto;
import com.example.military.air.base.management.system.model.dto.MilitaryAirBaseDto;
import com.example.military.air.base.management.system.model.dto.editable.AircraftEditableDto;
import com.example.military.air.base.management.system.model.type.AircraftCombatTask;
import com.example.military.air.base.management.system.model.type.AircraftPurpose;
import com.example.military.air.base.management.system.model.type.AircraftType;
import com.example.military.air.base.management.system.service.impl.AircraftServiceImpl;
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
@RequestMapping("/aircraft")
public class AircraftController {
    private final AircraftServiceImpl aircraftService;
    private final MilitaryAirBaseServiceImpl militaryAirBaseService;

    @Autowired
    public AircraftController(AircraftServiceImpl aircraftService, MilitaryAirBaseServiceImpl militaryAirBaseService) {
        this.aircraftService = aircraftService;
        this.militaryAirBaseService = militaryAirBaseService;
    }

    @GetMapping("/create")
    public String createAircraft(Model model) {
        model.addAttribute("types", AircraftType.values());
        model.addAttribute("purposes", AircraftPurpose.values());
        model.addAttribute("combatTasks", AircraftCombatTask.values());
        model.addAttribute("allMilitaryAirBases", militaryAirBaseService.getAllMilitaryAirBases());
        model.addAttribute("aircraft", new AircraftEditableDto());

        return "aircraft/create-aircraft";
    }

    @PostMapping("/create")
    public String createAircraft(@ModelAttribute AircraftEditableDto aircraftEditableDto, @RequestParam("image") MultipartFile image, Model model) {
        try {
            aircraftService.addAircraft(aircraftEditableDto, image);
        } catch (NoSuchMilitaryAirBaseException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/aircraft";
    }

    @GetMapping("/{id}")
    public String readAircraftById(@PathVariable("id") Long id, Model model) {
        Optional<AircraftDto> aircraftDtoOptional;
        Optional<MilitaryAirBaseDto> militaryAirBaseDtoOptional;

        try {
            aircraftDtoOptional = aircraftService.getAircraftById(id);

            if (aircraftDtoOptional.isPresent()) {
                militaryAirBaseDtoOptional = militaryAirBaseService.getMilitaryAirBaseByAircraftId(aircraftDtoOptional.get().getId());

                model.addAttribute("aircraft", aircraftDtoOptional.get());
                militaryAirBaseDtoOptional.ifPresent(militaryAirBaseDto -> model.addAttribute("militaryAirBase", militaryAirBaseDto));
            }
        } catch (NoSuchAircraftException | NoSuchMilitaryAirBaseException | DataFormatException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "aircraft/read-aircraft";
    }

    @GetMapping
    public String readAllAircraft(Model model) {
        model.addAttribute("allAircraft", aircraftService.getAllAircraft());

        return "aircraft/read-all-aircraft";
    }

    @GetMapping("/update/{id}")
    public String updateAircraftById(@PathVariable("id") Long id, Model model) {
        Optional<AircraftEditableDto> aircraftEditableDtoOptional;

        try {
            aircraftEditableDtoOptional = aircraftService.getAircraftEditableById(id);
        } catch (NoSuchAircraftException | DataFormatException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        if (aircraftEditableDtoOptional.isPresent()) {
            model.addAttribute("types", AircraftType.values());
            model.addAttribute("purposes", AircraftPurpose.values());
            model.addAttribute("combatTasks", AircraftCombatTask.values());
            model.addAttribute("allMilitaryAirBases", militaryAirBaseService.getAllMilitaryAirBases());
            model.addAttribute("aircraft", aircraftEditableDtoOptional.get());
        }

        return "aircraft/update-aircraft";
    }

    @PostMapping("/update/{id}")
    public String updateAircraftById(@PathVariable("id") Long id, @ModelAttribute AircraftEditableDto aircraftEditableDto, @RequestParam("image") MultipartFile image, Model model) {
        try {
            aircraftService.modifyAircraftById(id, aircraftEditableDto, image);
        } catch (NoSuchAircraftException | NoSuchMilitaryAirBaseException | IOException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/aircraft";
    }

    @GetMapping("/delete/{id}")
    public String deleteAircraftById(@PathVariable("id") Long id, Model model) {
        try {
            aircraftService.removeAircraftById(id);
        } catch (NoSuchAircraftException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/aircraft";
    }

    @RequestMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> readImage(@PathVariable("id") Long id) {
        Optional<AircraftDto> aircraftDtoOptional;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        try {
            aircraftDtoOptional = aircraftService.getAircraftById(id);
        } catch (NoSuchAircraftException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException | DataFormatException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return aircraftDtoOptional
                .map(aircraftDto -> new ResponseEntity<>(aircraftDto.getImageData(), headers, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
