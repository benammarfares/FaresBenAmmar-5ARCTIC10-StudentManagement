package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class StudentServiceTest {
    
    private static final Logger logger = LogManager.getLogger(StudentServiceTest.class);
    
    @Autowired
    private StudentService studentService;

    @Test
    void testSaveAndGetStudent() {
        logger.info("Starting testSaveAndGetStudent test");
        
        // Create a new student
        Student student = new Student();
        student.setFirstName("Fares");
        student.setLastName("Ben Ammar");
        
        // Save the student
        Student savedStudent = studentService.saveStudent(student);
        logger.info("Student saved with ID: " + savedStudent.getIdStudent());
        
        // Verify save operation
        assertNotNull(savedStudent.getIdStudent());
        assertEquals("Fares", savedStudent.getFirstName());
        assertEquals("Ben Ammar", savedStudent.getLastName());
        
        // Test getStudentById
        Student retrievedStudent = studentService.getStudentById(savedStudent.getIdStudent());
        assertNotNull(retrievedStudent);
        assertEquals(savedStudent.getIdStudent(), retrievedStudent.getIdStudent());
        assertEquals("Fares", retrievedStudent.getFirstName());
        
        logger.info("Completed testSaveAndGetStudent test successfully");
    }

    @Test
    void testGetAllStudents() {
        logger.info("Starting testGetAllStudents test");
        
        // Create and save first student
        Student student1 = new Student();
        student1.setFirstName("Test1");
        student1.setLastName("Student1");
        studentService.saveStudent(student1);
        
        // Create and save second student
        Student student2 = new Student();
        student2.setFirstName("Test2");
        student2.setLastName("Student2");
        studentService.saveStudent(student2);
        
        // Get all students
        List<Student> students = studentService.getAllStudents();
        
        // Verify the list is not empty
        assertNotNull(students);
        assertTrue(students.size() >= 2);
        logger.info("Found " + students.size() + " students in database");
        
        logger.info("Completed testGetAllStudents test successfully");
    }

    @Test
    void testDeleteStudent() {
        logger.info("Starting testDeleteStudent test");
        
        // Create and save a student
        Student student = new Student();
        student.setFirstName("ToDelete");
        student.setLastName("Student");
        Student savedStudent = studentService.saveStudent(student);
        
        // Verify student was saved
        assertNotNull(savedStudent.getIdStudent());
        
        // Delete the student
        studentService.deleteStudent(savedStudent.getIdStudent());
        logger.info("Deleted student with ID: " + savedStudent.getIdStudent());
        
        // Verify student was deleted
        Student deletedStudent = studentService.getStudentById(savedStudent.getIdStudent());
        assertNull(deletedStudent);
        
        logger.info("Completed testDeleteStudent test successfully");
    }
}