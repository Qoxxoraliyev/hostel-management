package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.repository.HostelExpensesRepository;
import com.hostel.hostel.management.repository.HostelRepository;
import com.hostel.hostel.management.service.dto.HostelCreateDTO;
import com.hostel.hostel.management.service.impl.HostelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HostelServiceTests {

    @Mock
    private HostelRepository hostelRepository;

    @Mock
    private HostelExpensesRepository hostelExpensesRepository;

    @InjectMocks
    private HostelServiceImpl hostelService;

    private Hostel hostel;
    private HostelCreateDTO hostelCreateDTO;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        hostelCreateDTO=new HostelCreateDTO(true,"Fergana Hostel","Fergana",120);

        hostel=new Hostel();
        hostel.setName("Fergana Hostel");
    }
}
