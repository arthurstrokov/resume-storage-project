package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import com.gmail.arthurstrokov.resume.exceptions.ResourceAlreadyExistsException;
import com.gmail.arthurstrokov.resume.exceptions.ResourceNotFoundException;
import com.gmail.arthurstrokov.resume.mapper.EmployeeMapper;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    SpecificationService specificationService;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeMapper employeeMapper;
    private List<Employee> employeeList;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @Test
    void context() {
        assertNotNull(employeeRepository);
        assertNotNull(employeeService);
    }

    @BeforeEach
    void setUp() {
        employeeList = new ArrayList<>();
        employee = Employee.builder()
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375-291555376")
                .birthDate(new Date())
                .gender(Gender.MALE)
                .email("arthurstrokov@gmail.com")
                .build();
        employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375-291555376")
                .birthDate(new Date())
                .gender(Gender.MALE)
                .email("arthurstrokov@gmail.com")
                .build();
        employeeList.add(employee);
    }

    @AfterEach
    void tearDown() {
        employeeList = null;
        employee = null;
    }

    @Test
    @DisplayName("test if employee with it's email already exists")
    void testIfExists() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        assertTrue(employeeService.ifExists("arthurstrokov@gmail.com"));
    }

    @Test
    @DisplayName("test save if employee already exists")
    void testSaveIfEmployeeDoesntExistsYet() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toEntity(employeeDTO)).thenReturn(employee);

        employeeService.save(employeeDTO);
        verify(employeeRepository, times(1)).save(employee);
        assertDoesNotThrow(() -> employeeService.save(employeeDTO));
    }

    @Test
    @DisplayName("test save if employee doesn't exists")
    void testSaveIfEmployeeAlreadyExists() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);
        when(employeeMapper.toEntity(employeeDTO)).thenReturn(employee);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.save(employeeDTO));
        verify(employeeRepository, times(0)).save(employee);
    }

    @Test
    @DisplayName("test find employee by id")
    void testFindById() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(employee));
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO employeeDTOFromDatabase = employeeService.findById(1L);
        assertEquals(employeeDTOFromDatabase, employeeDTO);
        assertThat(employeeService.findById(employeeDTO.getId())).isEqualTo(employeeDTO);
        assertDoesNotThrow(() -> employeeService.findById(4L));
    }

    @Test
    @DisplayName("test get all employees")
    void testGetAll() {
        when(employeeRepository.findAll()).thenReturn(employeeList);

        employeeRepository.save(employee);
        List<EmployeeDTO> employeeDTOFromDatabase = employeeService.getAll();
        assertNotNull(employeeDTOFromDatabase);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test get all pageable employees")
    void testGetPageable() {
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Pageable pageable = PageRequest.of(0, 5, Sort.by("email"));
        Page<EmployeeDTO> employeesPageable = employeeService.getAllPageable(pageable);
        assertNotNull(employeesPageable);
        verify(employeeRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("test get all filtered employees")
    void testGetFiltered() {
        Specification<Employee> spec = specificationService.employeeRequestToSpecification("email:arthurstrokov@gmail.com");
        when(employeeRepository.findAll(spec)).thenReturn(employeeList);

        employeeRepository.findAll(spec);
        List<EmployeeDTO> employeesFiltered = employeeService.getAllByFilter("email:arthurstrokov@gmail.com");
        assertNotNull(employeesFiltered);
        verify(employeeRepository, times(2)).findAll(spec);
    }

    @Test
    @DisplayName("test get all filtered and pageable employees")
    void testGetAllFilteredAndPageable() {
        Specification<Employee> spec = specificationService.employeeRequestToSpecification("email:arthurstrokov@gmail.com");
        Pageable pageable = PageRequest.of(0, 5, Sort.by("email"));
        when(employeeRepository.findAll(spec, pageable)).thenReturn(Page.empty());

        Page<EmployeeDTO> employeesFilteredAndPageable = employeeService.getAllFilteredAndPageable("email:arthurstrokov@gmail.com", pageable);
        assertNotNull(employeesFilteredAndPageable);
        verify(employeeRepository, times(1)).findAll(spec, pageable);
    }

    @Test
    @DisplayName("test update employee")
    void testUpdate() {
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee saved = employeeService.update(employeeDTO, 1L);
        assertEquals(saved, employee);
    }

    @Test
    @DisplayName("test delete employee by id")
    void testDeleteById() {
        employeeService.deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> employeeService.findById(1L));
        verify(employeeRepository, times(1)).deleteById(1L);
        assertDoesNotThrow(() -> employeeService.deleteById(anyLong()));
    }
}
