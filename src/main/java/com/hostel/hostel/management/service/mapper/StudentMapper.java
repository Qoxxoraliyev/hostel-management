package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Student;
import com.hostel.hostel.management.service.dto.StudentCreateDTO;
import com.hostel.hostel.management.service.dto.StudentResponseDTO;

public class StudentMapper {

    public static Student toEntity(StudentCreateDTO dto){
        Student s=new Student();
        s.setAge(dto.age());
        s.setDob(dto.dob());
        s.setPhone(dto.phone());
        s.setFullName(dto.fullName());
        return s;
    }

    public static StudentResponseDTO toResponse(Student s){
        return new StudentResponseDTO(
                s.getStudentId(),
                s.getFullName(),
                s.getAge(),
                s.getPhone(),
                s.getDob(),
                s.getFees().stream().map(FeeMapper::toResponse).toList(),
                s.getPayments().stream().map(PaymentMapper::toResponse).toList(),
                s.getVisitors().stream().map(VisitorMapper::toResponse).toList()
        );
    }


}
