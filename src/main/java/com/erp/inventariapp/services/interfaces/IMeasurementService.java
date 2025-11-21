package com.erp.inventariapp.services.interfaces;

import java.util.List;

import com.erp.inventariapp.dto.MeasurementDTO;

public interface IMeasurementService {
    public List<MeasurementDTO> findAll();
    public MeasurementDTO findById(Long idmeasurement);
    public List<MeasurementDTO> findByName(String name);
    public MeasurementDTO create(MeasurementDTO measurement);
    public MeasurementDTO update(Long idmeasurement, MeasurementDTO measurement);
    public void delete(Long idmeasurement);
}
