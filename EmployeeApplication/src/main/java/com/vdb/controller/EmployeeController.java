package com.vdb.controller;

import com.vdb.exception.RecordNotFoundException;
import com.vdb.model.Employee;
import com.vdb.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@Valid @RequestBody Employee employee) {
        log.info("Trying to signup employee: {}", employee.getEmpName());
        return ResponseEntity.ok(employeeService.signUp(employee));
    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Employee>> saveAll(@RequestBody List<Employee> employeeList) {
        return ResponseEntity.ok(employeeService.saveAll(employeeList));
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId,
                                          @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeService.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyname")
    public ResponseEntity<Employee> findByName(
            @RequestParam(defaultValue = "ram") String empName) {
        return ResponseEntity.ok(employeeService.findByName(empName));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .sorted(Comparator.comparing(Employee::getEmpName))
                        .toList()
        );
    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .sorted(Comparator.comparing(Employee::getEmpSalary))
                        .toList()
        );
    }

    @GetMapping("/findbyemail/{empEmailId}")
    public ResponseEntity<List<Employee>> findByEmail(@PathVariable String empEmailId) {
        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .filter(emp -> emp.getEmpEmailId().equalsIgnoreCase(empEmailId))
                        .toList()
        );
    }

    @GetMapping("/findbyaddress/{empAddress}")
    public ResponseEntity<List<Employee>> findByAddress(@PathVariable String empAddress) {
        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .filter(emp -> emp.getEmpAddress().equalsIgnoreCase(empAddress))
                        .toList()
        );
    }

    @GetMapping("/findbydob/{empDOB}")
    public ResponseEntity<List<Employee>> findByDOB(@PathVariable String empDOB) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .filter(emp -> emp.getEmpDOB() != null &&
                                sdf.format(emp.getEmpDOB()).equals(empDOB))
                        .toList()
        );
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(
                employeeService.findAll().stream()
                        .filter(emp ->
                                emp.getEmpName().equalsIgnoreCase(input)
                                        || String.valueOf(emp.getEmpId()).equals(input)
                                        || emp.getEmpAddress().equalsIgnoreCase(input)
                                        || (emp.getEmpDOB() != null &&
                                        sdf.format(emp.getEmpDOB()).equals(input))
                                        || emp.getEmpEmailId().equalsIgnoreCase(input)
                        ).toList()
        );
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId,
                                           @Valid @RequestBody Employee employee) {

        Employee employee1 = employeeService.findById(empId)
                .orElseThrow(() -> new RecordNotFoundException("Employee Not Found"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpPassword(employee.getEmpPassword());

        return ResponseEntity.ok(employeeService.update(employee1));
    }

    @PatchMapping("/changeemailid/{empId}/{empEmailId}")
    public ResponseEntity<Employee> changeEmailId(@PathVariable int empId,
                                                  @PathVariable String empEmailId) {

        Employee employee = employeeService.findById(empId)
                .orElseThrow(() -> new RecordNotFoundException("Employee ID Not Found"));

        employee.setEmpEmailId(empEmailId);

        return ResponseEntity.ok(employeeService.update(employee));
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {

        employeeService.deleteById(empId);

        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {

        employeeService.deleteAll();

        return ResponseEntity.ok("All Employees Deleted Successfully");
    }
}
