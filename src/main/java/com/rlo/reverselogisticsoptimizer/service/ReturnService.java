package com.rlo.reverselogisticsoptimizer.service;

import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.enums.ReturnStatus;
import com.rlo.reverselogisticsoptimizer.repository.ReturnRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReturnService {

    private final ReturnRepository returnRepository;

    public ReturnService(ReturnRepository returnRepository) {
        this.returnRepository = returnRepository;
    }

    public Return createReturn(Return returnRequest) {

        returnRequest.setStatus(ReturnStatus.CREATED);
        returnRequest.setCreatedAt(LocalDateTime.now());

        return returnRepository.save(returnRequest);
    }

    public List<Return> getAllReturns() {
        return returnRepository.findAll();
    }

    public Return updateStatus(
            String id,
            ReturnStatus status
    ) {

        Return returnRequest =
                returnRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Return not found")
                        );

        returnRequest.setStatus(status);

        return returnRepository.save(returnRequest);
    }
}