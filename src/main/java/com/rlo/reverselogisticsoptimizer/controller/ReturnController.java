package com.rlo.reverselogisticsoptimizer.controller;

import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.enums.ReturnStatus;
import com.rlo.reverselogisticsoptimizer.service.ReturnService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PostMapping
    public Return createReturn(
            @Valid @RequestBody Return returnRequest
    ) {
        return returnService.createReturn(returnRequest);
    }

    @GetMapping
    public List<Return> getAllReturns() {
        return returnService.getAllReturns();
    }

    @PutMapping("/{id}/status")
    public Return updateStatus(
            @PathVariable String id,
            @RequestParam ReturnStatus status
    ) {
        return returnService.updateStatus(id, status);
    }
}