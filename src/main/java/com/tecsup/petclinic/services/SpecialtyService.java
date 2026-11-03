package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

/**
 *
 * @author Grupo 3
 *
 */
public interface SpecialtyService {

	/**
	 *
	 * @param specialtyDTO
	 * @return
	 * @throws InvalidScheduleException
	 */
	SpecialtyDTO create(SpecialtyDTO specialtyDTO) throws InvalidScheduleException;

	/**
	 *
	 * @param specialtyDTO
	 * @return
	 * @throws InvalidScheduleException
	 */
	SpecialtyDTO update(SpecialtyDTO specialtyDTO) throws InvalidScheduleException;

	/**
	 *
	 * @param id
	 * @throws SpecialtyNotFoundException
	 */
	void delete(Integer id) throws SpecialtyNotFoundException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws SpecialtyNotFoundException
	 */
	SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

	/**
	 *
	 * @param name
	 * @return
	 */
	List<SpecialtyDTO> findByName(String name);

	/**
	 *
	 * @param office
	 * @return
	 */
	List<SpecialtyDTO> findByOffice(String office);
}
