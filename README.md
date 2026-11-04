# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

---

## Grupo 3 — Pruebas para SpecialtyService

### Integrantes y pruebas

| Integrante | Rama | Pruebas |
|------------|------|---------|
| luiscucho-oss (Integrante 1) | `feature/specialty-crud-validations` | `testCreateSpecialty`, `testUpdateSpecialty`, `testFindSpecialtyById`, `testCreateSpecialty_InvalidSchedule`, `testCreateSpecialty_InvalidHourRange` |
| Jesus529 (Integrante 2) | `feature/specialty-search-errors` | `testFindByOffice`, `testFindByName`, `testDeleteSpecialty`, `testFindSpecialtyById_NotFound` |

El código base del servicio (entidad, DTO, mapper, repositorio, excepciones y `SpecialtyService`) se integró antes en la rama `feature/specialty-service` (PR #1), revisada y aprobada por Jesus529.

### Conflictos resueltos

**Archivo:** `src/test/java/com/tecsup/petclinic/services/SpecialtyServiceTest.java`

**Cuándo:** después de fusionar el PR #2 (`feature/specialty-search-errors`), al traer `main` a la rama `feature/specialty-crud-validations` con `git merge origin/main`.

**Causa:** las dos ramas agregaron sus pruebas en el mismo lugar, al final de la clase `SpecialtyServiceTest`. Además, la rama de Jesus529 reformateó el archivo (tabulaciones a espacios) y quitó el import de `InvalidScheduleException`, que las pruebas de validación necesitan.

**Resolución (luiscucho-oss):**
- Se conservaron las 9 pruebas: las 4 de Jesus529 y las 5 de luiscucho-oss.
- Se unificó la indentación con espacios, como quedó en `main`.
- Se restauró el import de `InvalidScheduleException`.
- Se verificó con `./mvnw test`: 24 pruebas, 0 fallos.
