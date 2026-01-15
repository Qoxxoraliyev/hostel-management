package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Student;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.StudentRepository;
import com.hostel.hostel.management.service.StudentService;
import com.hostel.hostel.management.service.dto.StudentCreateDTO;
import com.hostel.hostel.management.service.dto.StudentResponseDTO;
import com.hostel.hostel.management.service.mapper.StudentMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponseDTO create(StudentCreateDTO studentCreateDTO){
        Student student= StudentMapper.toEntity(studentCreateDTO);
        Student saved=studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }

    @Override
    public StudentResponseDTO update(Long studentId,StudentCreateDTO studentCreateDTO){
        Student student=studentRepository.findById(studentId).orElseThrow(()->new AppException(ErrorCode.STUDENT_NOT_FOUND,"Student not found with id: "+studentId));
        student.setDob(studentCreateDTO.dob());
        student.setPhone(studentCreateDTO.phone());
        student.setAge(studentCreateDTO.age());
        student.setFullName(studentCreateDTO.fullName());
        Student saved=studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }


    @Override
    public StudentResponseDTO getById(Long studentId){
        Student student=studentRepository.findById(studentId).orElseThrow(()->new AppException(ErrorCode.STUDENT_NOT_FOUND,"Student not found with id: "+studentId));
        return StudentMapper.toResponse(student);
    }


    @Override
    public void delete(Long studentId){
        Student student=studentRepository.findById(studentId).orElseThrow(()->new AppException(ErrorCode.STUDENT_NOT_FOUND,"Student not found with id: "+studentId));
        studentRepository.delete(student);
    }



    @Override
    public List<StudentResponseDTO> getAll(Pageable pageable){
        return studentRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }



}
