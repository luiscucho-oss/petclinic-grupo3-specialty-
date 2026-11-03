package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Grupo 3
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

	private static final int MIN_HOUR = 0;
	private static final int MAX_HOUR = 23;

	private final SpecialtyRepository specialtyRepository;
	private final SpecialtyMapper specialtyMapper;

	/**
	 *
	 * @param specialtyDTO
	 * @return
	 * @throws InvalidScheduleException
	 */
	@Override
	public SpecialtyDTO create(SpecialtyDTO specialtyDTO) throws InvalidScheduleException {

		validateSchedule(specialtyDTO);

		Specialty newSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

		return specialtyMapper.mapToDto(newSpecialty);
	}

	/**
	 *
	 * @param specialtyDTO
	 * @return
	 * @throws InvalidScheduleException
	 */
	@Override
	public SpecialtyDTO update(SpecialtyDTO specialtyDTO) throws InvalidScheduleException {

		validateSchedule(specialtyDTO);

		Specialty updatedSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

		return specialtyMapper.mapToDto(updatedSpecialty);
	}

	/**
	 *
	 * @param id
	 * @throws SpecialtyNotFoundException
	 */
	@Override
	public void delete(Integer id) throws SpecialtyNotFoundException {

		SpecialtyDTO specialty = findById(id);

		specialtyRepository.delete(this.specialtyMapper.mapToEntity(specialty));
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws SpecialtyNotFoundException
	 */
	@Override
	public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {

		Specialty specialty = specialtyRepository.findById(id)
				.orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with id: " + id));

		return this.specialtyMapper.mapToDto(specialty);
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	@Override
	public List<SpecialtyDTO> findByName(String name) {

		List<Specialty> specialties = specialtyRepository.findByName(name);

		specialties.forEach(specialty -> log.info("{}", specialty));

		return specialties
				.stream()
				.map(this.specialtyMapper::mapToDto)
				.toList();
	}

	/**
	 *
	 * @param office
	 * @return
	 */
	@Override
	public List<SpecialtyDTO> findByOffice(String office) {

		List<Specialty> specialties = specialtyRepository.findByOffice(office);

		specialties.forEach(specialty -> log.info("{}", specialty));

		return specialties
				.stream()
				.map(this.specialtyMapper::mapToDto)
				.toList();
	}

	/**
	 * Hours must be in 0-23 and the opening hour must be before the closing hour.
	 */
	private void validateSchedule(SpecialtyDTO specialtyDTO) throws InvalidScheduleException {

		int openHour = specialtyDTO.getOpenHour();
		int closeHour = specialtyDTO.getCloseHour();

		if (openHour < MIN_HOUR || openHour > MAX_HOUR || closeHour < MIN_HOUR || closeHour > MAX_HOUR)
			throw new InvalidScheduleException("Hours must be between " + MIN_HOUR + " and " + MAX_HOUR);

		if (openHour >= closeHour)
			throw new InvalidScheduleException("Opening hour must be before closing hour");
	}
}
