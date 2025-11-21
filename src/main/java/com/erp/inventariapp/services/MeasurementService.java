package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.MeasurementDTO;
import com.erp.inventariapp.entities.Measurement;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.MeasurementRepository;
import com.erp.inventariapp.services.interfaces.IMeasurementService;

@Service
public class MeasurementService implements IMeasurementService {
    @Autowired
    MeasurementRepository measurementrepository;
    
    @Override
    public List<MeasurementDTO> findAll() {
        List<Measurement> measurements = (List<Measurement>) measurementrepository.findAll();
        List<MeasurementDTO> measurementsDTO = new ArrayList<>();
        measurements.forEach(m -> measurementsDTO.add(this.convertToDTO(m)));
        return measurementsDTO;
    }

    @Override
    public MeasurementDTO findById(Long idMeasurement) {
        Measurement m = measurementrepository.findById(idMeasurement).get();
        return (this.convertToDTO(m)); 
    }

    @Override
    public List<MeasurementDTO> findByName(String name) {
        Optional<List<Measurement>> optional = measurementrepository.findByNameContainingIgnoreCase(name);
        if(optional.isPresent()){
            List<Measurement> measurements = optional.get();
            List<MeasurementDTO> measurementsDTO = new ArrayList<>();
            measurements.forEach(c -> measurementsDTO.add(this.convertToDTO(c)));
            return measurementsDTO;
        }else{
            throw new ResourceNotFoundException("nameMesur");
        }
    }

    @Override
    public MeasurementDTO create(MeasurementDTO dto) {
        Measurement m = new Measurement();
        return saveOrUpdate(m, dto);
    }

    @Override
    public MeasurementDTO update(Long id, MeasurementDTO dto) {
        Measurement m = measurementrepository.findById(id).get();
        return saveOrUpdate(m, dto);
    }

    @Override
    public void delete(Long idMeasurement) {
        //if (!measurementrepository.existsById(idMeasurement))
        //    throw ResourceNotFoundException("Unidad de medida no encontrada");
        measurementrepository.deleteById(idMeasurement);
    }

    private MeasurementDTO saveOrUpdate(Measurement m, MeasurementDTO dto) {
        m.setName(dto.getName());
        m.setState(dto.getState());
        return convertToDTO(measurementrepository.save(m));
    }

    private MeasurementDTO convertToDTO(Measurement m) {
        MeasurementDTO dto = new MeasurementDTO();
        dto.setIdmeasurement(m.getIdmeasurement());
        dto.setName(m.getName());
        dto.setState(m.getState());
        return dto;
    }    

}
