package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
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

	/**
	 *
	 */
	@Test
	public void testCreateSpecialty() throws InvalidScheduleException {

		Specialty newSpecialty = TObjectCreator.newSpecialty();
		Specialty newSpecialtyCreated = TObjectCreator.newSpecialtyCreated();

		SpecialtyDTO newSpecialtyDTO = this.specialtyMapper.mapToDto(newSpecialty);

		Mockito.when(this.repository.save(newSpecialty))
				.thenReturn(newSpecialtyCreated);

		SpecialtyDTO specialtyDTOCreated = this.specialtyService.create(newSpecialtyDTO);

		log.info("Specialty created : {}", specialtyDTOCreated);

		assertNotNull(specialtyDTOCreated.getId());
		assertEquals(newSpecialtyCreated.getId(), specialtyDTOCreated.getId());
		assertEquals(newSpecialty.getName(), specialtyDTOCreated.getName());
		assertEquals(newSpecialty.getOffice(), specialtyDTOCreated.getOffice());
		assertEquals(newSpecialty.getOpenHour(), specialtyDTOCreated.getOpenHour());
		assertEquals(newSpecialty.getCloseHour(), specialtyDTOCreated.getCloseHour());

		Mockito.verify(this.repository).save(newSpecialty);
	}

	/**
	 *
	 */
	@Test
	public void testUpdateSpecialty() throws InvalidScheduleException {

		String UP_NAME = "neurosurgery";
		String UP_OFFICE = "Trujillo";
		int UP_OPEN_HOUR = 10;
		int UP_CLOSE_HOUR = 20;

		Specialty newSpecialty = TObjectCreator.newSpecialtyForUpdate();
		Specialty newSpecialtyCreated = TObjectCreator.newSpecialtyCreatedForUpdate();

		// ------------ Create ---------------

		Mockito.when(this.repository.save(newSpecialty))
				.thenReturn(newSpecialtyCreated);

		SpecialtyDTO specialtyDTOCreated = this.specialtyService.create(this.specialtyMapper.mapToDto(newSpecialty));
		log.info("Specialty created : {}", specialtyDTOCreated);

		// ------------ Update ---------------

		// Prepare data for update
		specialtyDTOCreated.setName(UP_NAME);
		specialtyDTOCreated.setOffice(UP_OFFICE);
		specialtyDTOCreated.setOpenHour(UP_OPEN_HOUR);
		specialtyDTOCreated.setCloseHour(UP_CLOSE_HOUR);

		Specialty specialtyToUpdate = this.specialtyMapper.mapToEntity(specialtyDTOCreated);

		Mockito.when(this.repository.save(specialtyToUpdate))
				.thenReturn(specialtyToUpdate);

		// Execute update
		SpecialtyDTO specialtyDTOUpdated = this.specialtyService.update(specialtyDTOCreated);
		log.info("Specialty updated : {}", specialtyDTOUpdated);

		//            EXPECTED                     ACTUAL
		assertEquals(newSpecialtyCreated.getId(), specialtyDTOUpdated.getId());
		assertEquals(UP_NAME, specialtyDTOUpdated.getName());
		assertEquals(UP_OFFICE, specialtyDTOUpdated.getOffice());
		assertEquals(UP_OPEN_HOUR, specialtyDTOUpdated.getOpenHour());
		assertEquals(UP_CLOSE_HOUR, specialtyDTOUpdated.getCloseHour());

		Mockito.verify(this.repository).save(specialtyToUpdate);
	}

	/**
	 *
	 */
	@Test
	public void testFindSpecialtyById() throws SpecialtyNotFoundException {

		Specialty specialtyExpected = TObjectCreator.getSpecialty();

		Mockito.when(this.repository.findById(specialtyExpected.getId()))
				.thenReturn(Optional.of(specialtyExpected));

		SpecialtyDTO specialty = this.specialtyService.findById(specialtyExpected.getId());

		log.info("Specialty found : {}", specialty);

		assertEquals(specialtyExpected.getId(), specialty.getId());
		assertEquals(specialtyExpected.getName(), specialty.getName());
		assertEquals(specialtyExpected.getOffice(), specialty.getOffice());
		assertEquals(specialtyExpected.getOpenHour(), specialty.getOpenHour());
		assertEquals(specialtyExpected.getCloseHour(), specialty.getCloseHour());

		Mockito.verify(this.repository).findById(specialtyExpected.getId());
	}

	/**
	 * h_open >= h_close must be rejected and nothing must be saved.
	 */
	@Test
	public void testCreateSpecialty_InvalidSchedule() {

		SpecialtyDTO openAfterCloseDTO = TObjectCreator.newSpecialtyDTOInvalidSchedule();
		SpecialtyDTO sameHoursDTO = SpecialtyDTO.builder()
				.name("cardiology")
				.office("Lima")
				.openHour(9)
				.closeHour(9)
				.build();

		InvalidScheduleException exception = assertThrows(InvalidScheduleException.class,
				() -> this.specialtyService.create(openAfterCloseDTO));

		log.info("Expected error : {}", exception.getMessage());

		assertEquals("Opening hour must be before closing hour", exception.getMessage());

		// Boundary: same opening and closing hour is also invalid
		assertThrows(InvalidScheduleException.class,
				() -> this.specialtyService.create(sameHoursDTO));

		Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
	}

	/**
	 * Hours outside 0-23 must be rejected and nothing must be saved.
	 */
	@Test
	public void testCreateSpecialty_InvalidHourRange() {

		SpecialtyDTO closeHourTooHighDTO = TObjectCreator.newSpecialtyDTOInvalidHourRange();
		SpecialtyDTO negativeOpenHourDTO = SpecialtyDTO.builder()
				.name("cardiology")
				.office("Lima")
				.openHour(-1)
				.closeHour(10)
				.build();

		InvalidScheduleException exception = assertThrows(InvalidScheduleException.class,
				() -> this.specialtyService.create(closeHourTooHighDTO));

		log.info("Expected error : {}", exception.getMessage());

		assertEquals("Hours must be between 0 and 23", exception.getMessage());

		assertThrows(InvalidScheduleException.class,
				() -> this.specialtyService.create(negativeOpenHourDTO));

		Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
	}

}
