package hello.controller;


import hello.repository.AccessDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    AccessDetailsRepository employeeRoleRepository;

    @GetMapping("/postEmployee")
    public void saveEmployee() {
        System.out.println(employeeRoleRepository.findByRoleId(1));
    }
}
