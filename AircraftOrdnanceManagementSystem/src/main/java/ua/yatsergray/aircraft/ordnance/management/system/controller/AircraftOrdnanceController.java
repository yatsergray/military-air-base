package ua.yatsergray.aircraft.ordnance.management.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.aircraft.ordnance.management.system.domain.dto.AircraftOrdnanceDto;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceHoming;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnancePurpose;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceType;
import ua.yatsergray.aircraft.ordnance.management.system.exception.NoSuchAircraftOrdnanceException;
import ua.yatsergray.aircraft.ordnance.management.system.service.impl.AircraftOrdnanceServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/aircraft-ordnance")
public class AircraftOrdnanceController {
    private final AircraftOrdnanceServiceImpl aircraftOrdnanceService;

    @Autowired
    public AircraftOrdnanceController(AircraftOrdnanceServiceImpl aircraftOrdnanceService) {
        this.aircraftOrdnanceService = aircraftOrdnanceService;
    }

    @GetMapping("/create")
    public String createAircraftOrdnance(Model model) {
        model.addAttribute("types", AircraftOrdnanceType.values());
        model.addAttribute("purposes", AircraftOrdnancePurpose.values());
        model.addAttribute("allHoming", AircraftOrdnanceHoming.values());
        model.addAttribute("aircraftOrdnance", new AircraftOrdnanceDto());

        return "create-aircraft-ordnance";
    }

    @PostMapping("/create")
    public String createAircraftOrdnance(@ModelAttribute AircraftOrdnanceDto aircraftOrdnanceDto) {
        aircraftOrdnanceService.addAircraftOrdnance(aircraftOrdnanceDto);

        return "redirect:/aircraft-ordnance";
    }

    @GetMapping("/{id}")
    public String readAircraftOrdnanceById(@PathVariable("id") String id, Model model) {
        Optional<AircraftOrdnanceDto> aircraftOrdnanceDtoOptional;

        try {
            aircraftOrdnanceDtoOptional = aircraftOrdnanceService.getAircraftOrdnanceById(id);
        } catch (NoSuchAircraftOrdnanceException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        aircraftOrdnanceDtoOptional.ifPresent(aircraftOrdnanceDto -> model.addAttribute("aircraftOrdnance", aircraftOrdnanceDto));

        return "read-aircraft-ordnance";
    }

    @GetMapping
    public String readAllAircraftOrdnance(Model model) {
        model.addAttribute("allAircraftOrdnance", aircraftOrdnanceService.getAllAircraftOrdnance());

        return "read-all-aircraft-ordnance";
    }

    @GetMapping("/update/{id}")
    public String updateAircraftOrdnanceById(@PathVariable("id") String id, Model model) {
        Optional<AircraftOrdnanceDto> aircraftOrdnanceDtoOptional;

        try {
            aircraftOrdnanceDtoOptional = aircraftOrdnanceService.getAircraftOrdnanceById(id);
        } catch (NoSuchAircraftOrdnanceException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        if (aircraftOrdnanceDtoOptional.isPresent()) {
            model.addAttribute("types", AircraftOrdnanceType.values());
            model.addAttribute("purposes", AircraftOrdnancePurpose.values());
            model.addAttribute("allHoming", AircraftOrdnanceHoming.values());
            model.addAttribute("aircraftOrdnance", aircraftOrdnanceDtoOptional.get());
        }

        return "update-aircraft-ordnance";
    }

    @PostMapping("/update/{id}")
    public String updateAircraftOrdnanceById(@PathVariable("id") String id, @ModelAttribute AircraftOrdnanceDto aircraftOrdnanceDto, Model model) {
        try {
            aircraftOrdnanceService.modifyAircraftOrdnanceById(id, aircraftOrdnanceDto);
        } catch (NoSuchAircraftOrdnanceException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/aircraft-ordnance";
    }

    @GetMapping("/delete/{id}")
    public String deleteAircraftOrdnanceById(@PathVariable("id") String id, Model model) {
        try {
            aircraftOrdnanceService.removeAircraftOrdnanceById(id);
        } catch (NoSuchAircraftOrdnanceException e) {
            model.addAttribute("message", e.getMessage());

            return "not-found";
        }

        return "redirect:/aircraft-ordnance";
    }
}
