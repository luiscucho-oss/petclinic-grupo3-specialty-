package com.tecsup.petclinic.exceptions;

/**
 * Thrown when a specialty schedule is not valid:
 * hours outside 0-23 or opening hour not before closing hour.
 *
 * @author Grupo 3
 *
 */
public class InvalidScheduleException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidScheduleException(String message) {
		super(message);
	}

}
