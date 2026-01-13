package com.hostel.hostel.management.service.impl;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.exceptions.HostelNotFoundException;
import com.hostel.hostel.management.repository.HostelRepository;
import com.hostel.hostel.management.service.HostelService;
import com.hostel.hostel.management.service.dto.HostelCreateDTO;
import com.hostel.hostel.management.service.dto.HostelDetailDTO;
import com.hostel.hostel.management.service.mapper.HostelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelServiceImpl implements HostelService {

    private HostelRepository hostelRepository;

    public HostelServiceImpl(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }


    @Override
    public HostelDetailDTO create(HostelCreateDTO dto){
        Hostel hostel=HostelMapper.toEntity(dto);
        Hostel savedHostel=hostelRepository.save(hostel);
        return HostelMapper.hostelDetailDTO(savedHostel);
    }



    @Override
    public List<HostelDetailDTO> getByName(String name){
        List<Hostel> hostels=hostelRepository.findByNameContainingIgnoreCase(name);

        if (hostels.isEmpty()){
            throw new HostelNotFoundException("No hostel found containing name: "+name);
        }

        return hostels.stream()
                .map(HostelMapper::hostelDetailDTO)
                .toList();
    }



    @Override
    public HostelDetailDTO update(Long hostelId,HostelCreateDTO hostelCreateDTO){
        Hostel hostel=hostelRepository.findById(hostelId).orElseThrow(()->new HostelNotFoundException(
                "Hostel not found with id: "+hostelId
        ));
        hostel.setLocation(hostelCreateDTO.location());
        hostel.setAnnualExpenses(hostelCreateDTO.annualExpenses());
        hostel.setName(hostelCreateDTO.name());
        hostel.setTotalRooms(hostel.getTotalRooms());
        Hostel updatedHostel=hostelRepository.save(hostel);
        return HostelMapper.hostelDetailDTO(updatedHostel);
    }



    @Override
    public HostelDetailDTO getById(Long hostelId){
        Hostel hostel=hostelRepository.findById(hostelId)
                .orElseThrow(()->new HostelNotFoundException(
                        "Hostel not found with id: "+hostelId
                ));
        return HostelMapper.hostelDetailDTO(hostel);
    }



    @Override
    public void delete(Long hostelId){
        Hostel hostel=hostelRepository.findById(hostelId)
                .orElseThrow(()->new HostelNotFoundException(
                        "Hostel not found with id: "+hostelId
                ));
        hostelRepository.delete(hostel);
    }


    @Override
    public List<HostelDetailDTO> getAll(Pageable pageable){
        return hostelRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(HostelMapper::hostelDetailDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<HostelDetailDTO> getActiveHostels(Pageable pageable){
        return hostelRepository.findByActiveTrue(pageable)
                .getContent()
                .stream()
                .map(HostelMapper::hostelDetailDTO)
                .collect(Collectors.toList());
    }


}
