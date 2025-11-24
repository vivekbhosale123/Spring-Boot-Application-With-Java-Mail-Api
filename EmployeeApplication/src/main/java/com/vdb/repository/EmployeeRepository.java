package com.vdb.repository;

import com.vdb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByEmpEmailIdAndEmpPassword(String empEmailId, String empPassword);

    @Query("select e from Employee e where e.empName = :empName")
    Employee findByEmpName(@Param("empName") String empName);
}
