
package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Specialty;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

    // ------------ Pet ---------------

    public static Pet getPet() {
        return new Pet(1, "Leo", 1, 1, null);
    }

    public static Pet newPet() {
        Pet newEntity = new Pet();
        newEntity.setName("Punky");
        newEntity.setTypeId(1);
        newEntity.setOwnerId(1);
        return newEntity;
    }

    public static Pet newPetCreated() {
        Pet newPet = newPet();
        newPet.setId(1000);
        return newPet;
    }

    public static PetDTO newPetDTO() {
        PetDTO newDTO = new PetDTO();
        newDTO.setName("Punky");
        newDTO.setTypeId(1);
        newDTO.setOwnerId(1);
        return newDTO;
    }

    public static PetDTO newPetDTOCreated() {
        PetDTO petDTO = newPetDTO();
        petDTO.setId(1000);
        return petDTO;
    }

    public static Pet newPetForUpdate() {
        return new Pet(0, "Bear", 1, 1, null);
    }

    public static Pet newPetCreatedForUpdate() {
        Pet pet = newPetForUpdate();
        pet.setId(4000);
        return pet;
    }

    public static Pet newPetForDelete() {
        return new Pet(0, "Bird", 1, 1, null);
    }

    public static Pet newPetCreatedForDelete() {
        Pet pet = newPetForDelete();
        pet.setId(2000);
        return pet;
    }

    public static List<Pet> getPetsForFindByName() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(1, "Leo", 1, 1, null));
        return pets;
    }

    public static List<Pet> getPetsForFindByTypeId() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(9, "Lucky", 5, 7, null));
        pets.add(new Pet(11, "Freddy", 5, 9, null));
        return pets;
    }

    public static List<Pet> getPetsForFindByOwnerId() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(12, "Lucky", 2, 10, null));
        pets.add(new Pet(13, "Sly", 1, 10, null));
        return pets;
    }

    // ------------ Specialty ---------------

    public static Specialty getSpecialty() {
        return new Specialty(1, "radiology", "Farewell", 8, 18);
    }

    public static Specialty newSpecialty() {
        return new Specialty(null, "cardiology", "Lima", 9, 17);
    }

    public static Specialty newSpecialtyCreated() {
        Specialty specialty = newSpecialty();
        specialty.setId(1000);
        return specialty;
    }

    public static Specialty newSpecialtyForUpdate() {
        return new Specialty(null, "neurology", "Arequipa", 8, 14);
    }

    public static Specialty newSpecialtyCreatedForUpdate() {
        Specialty specialty = newSpecialtyForUpdate();
        specialty.setId(4000);
        return specialty;
    }

    public static Specialty newSpecialtyForDelete() {
        return new Specialty(null, "oncology", "Cusco", 10, 16);
    }

    public static Specialty newSpecialtyCreatedForDelete() {
        Specialty specialty = newSpecialtyForDelete();
        specialty.setId(2000);
        return specialty;
    }

    // Horario inválido: hora de apertura mayor que hora de cierre
    public static SpecialtyDTO newSpecialtyDTOInvalidSchedule() {
        return new SpecialtyDTO(null, "cardiology", "Lima", 18, 8);
    }

    // Horario inválido: hora de cierre fuera del rango 0-23
    public static SpecialtyDTO newSpecialtyDTOInvalidHourRange() {
        return new SpecialtyDTO(null, "cardiology", "Lima", 8, 25);
    }

    public static List<Specialty> getSpecialtiesForFindByName() {
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(new Specialty(2, "surgery", "Maryland", 8, 12));
        return specialties;
    }

    public static List<Specialty> getSpecialtiesForFindByOffice() {
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(new Specialty(1, "radiology", "Farewell", 8, 18));
        specialties.add(new Specialty(4, "dermatology", "Farewell", 14, 20));
        return specialties;
    }
}
