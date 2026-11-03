
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @MockitoBean
    private SpecialtyRepository repository;

    @Test
    public void testFindByOffice() {
        Mockito.when(repository.findByOffice("Farewell"))
                .thenReturn(TObjectCreator.getSpecialtiesForFindByOffice());

        List<SpecialtyDTO> specialties =
                specialtyService.findByOffice("Farewell");

        assertEquals(2, specialties.size());
        assertEquals("radiology", specialties.get(0).getName());
        assertEquals("dermatology", specialties.get(1).getName());
    }

    @Test
    public void testFindByName() {
        Mockito.when(repository.findByName("surgery"))
                .thenReturn(TObjectCreator.getSpecialtiesForFindByName());

        List<SpecialtyDTO> specialties =
                specialtyService.findByName("surgery");

        assertEquals(1, specialties.size());
        assertEquals("surgery", specialties.get(0).getName());
    }

    @Test
    public void testDeleteSpecialty() throws SpecialtyNotFoundException {
        Specialty specialty =
                TObjectCreator.newSpecialtyCreatedForDelete();

        Mockito.when(repository.findById(2000))
                .thenReturn(Optional.of(specialty));

        specialtyService.delete(2000);

        Mockito.verify(repository).delete(specialty);
    }

    @Test
    public void testFindSpecialtyById_NotFound() {
        Mockito.when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                SpecialtyNotFoundException.class,
                () -> specialtyService.findById(999)
        );
    }
}
