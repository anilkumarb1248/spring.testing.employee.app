package com.app.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.entity.DepartmentEntity;
import com.app.exceptions.DepartmentNotFoundException;
import com.app.exceptions.DuplicateDepartmentException;
import com.app.model.DepartmentDto;
import com.app.repository.dept.DepartmentRepository;
import com.app.util.ResponseStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	DepartmentRepository departmentRepository;

//	@Autowired // @Autowired is not required as we are using single constructor
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public List<DepartmentDto> getDepartments() {
		List<DepartmentEntity> entityList = departmentRepository.findAll();
		List<DepartmentDto> dtoList = new ArrayList<>();
		entityList.stream().forEach(entity -> {
			dtoList.add(convertToDto(entity));
		});
		return dtoList;
	}

	@Override
	public DepartmentDto getDepartment(Long departmentId) {
		Optional<DepartmentEntity> optional = departmentRepository.findById(departmentId);
		if (!optional.isPresent()) {
			throw new DepartmentNotFoundException("Department not found with id: " + departmentId);
		}
		return convertToDto(optional.get());
	}

	@Override
	public DepartmentDto getDepartment(String departmentName) {
		Optional<DepartmentEntity> optional = departmentRepository.findByDepartmentName(departmentName);
		if (!optional.isPresent()) {
			throw new DepartmentNotFoundException("Department not found with name: " + departmentName);
		}
		return convertToDto(optional.get());
	}

	@Override
	public ResponseStatus addDepartment(DepartmentDto departmentDto) {
		if (!isDuplicateDepartment(true, departmentDto)) {
			DepartmentEntity entity = departmentRepository.save(convertToEntity(departmentDto));

			if (null != entity) {
				return createResponseStatus(HttpStatus.CREATED, "Department added successfully");
			} else {
				return createResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to add Department");
			}
		} else {
			throw new DuplicateDepartmentException(
					"Department already exist with name: " + departmentDto.getDepartmentName());
		}
	}

	@Override
	public ResponseStatus addDepartmentsList(List<DepartmentDto> departmentList) {
		List<DepartmentEntity> entityList = new ArrayList<>();
		departmentList.forEach(dto -> {
			if (!isDuplicateDepartment(true, dto)) {
				entityList.add(convertToEntity(dto));
			}
		});
		departmentRepository.saveAll(entityList);
		LOGGER.info("Inserted departments dummy data successfully");
		return createResponseStatus(HttpStatus.OK, "department list inserted successfully");
	}

	@Override
	public ResponseStatus updateDepartment(DepartmentDto departmentDto) {
		if (isDepartmentExist(departmentDto.getDepartmentId())) {
			if (isDuplicateDepartment(false, departmentDto)) {
				throw new DuplicateDepartmentException(
						"Department already exist with name: " + departmentDto.getDepartmentName());
			}

			departmentRepository.save(convertToEntity(departmentDto));

			return createResponseStatus(HttpStatus.OK, "Department updated successfully");

		} else {
			throw new DepartmentNotFoundException("No Department found with id: " + departmentDto.getDepartmentId());
		}
	}

	@Override
	public ResponseStatus deleteDepartment(Long departmentId) {
		if (isDepartmentExist(departmentId)) {
			departmentRepository.deleteById(departmentId);
			return createResponseStatus(HttpStatus.OK, "Department deleted successfully");
		} else {
			throw new DepartmentNotFoundException("No Department found with id: " + departmentId);
		}
	}

	@Override
	@Transactional
	public ResponseStatus deleteDepartment(String departmentName) {
		if (isDepartmentExistByName(departmentName)) {
			try {
				departmentRepository.deleteByDepartmentName(departmentName);
				return createResponseStatus(HttpStatus.OK, "Department deleted successfully");
			} catch (Exception ex) {
				LOGGER.info("Failed to insert deparments dummy data");
				LOGGER.error(ex.getMessage());
				return createResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR,
						"Internal server error occured while delete department by name: " + departmentName);
			}
		} else {
			throw new DepartmentNotFoundException("No Department found with name: " + departmentName);
		}
	}

	@Override
	public ResponseStatus deleteAll() {
		departmentRepository.deleteAll();
		return createResponseStatus(HttpStatus.OK, "Departments deleted successfully");
	}

	@Override
	public ResponseStatus addDummyData() {
		LOGGER.info("Dummy Departments insertion started");
		ResponseStatus status = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = new ClassPathResource("departments-data.json");
			InputStream inputStream = resource.getInputStream();
			TypeReference<List<DepartmentDto>> typeReference = new TypeReference<List<DepartmentDto>>() {
			};

			List<DepartmentDto> dtoList = mapper.readValue(inputStream, typeReference);

			List<DepartmentEntity> entityList = new ArrayList<>();
			dtoList.forEach(dto -> {
				if (!isDuplicateDepartment(true, dto)) {
					entityList.add(convertToEntity(dto));
				}
			});
			departmentRepository.saveAll(entityList);

			LOGGER.info("Inserted departments dummy data successfully");
			status = createResponseStatus(HttpStatus.OK, "Dummy departments inserted successfully");
		} catch (Exception e) {
			LOGGER.info("Failed to insert deparments dummy data");
			LOGGER.error(e.getMessage());
			status = createResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to insert dummy departments");
		}

		LOGGER.info("Dummy Departments insertion ended");
		return status;
	}

	private DepartmentEntity convertToEntity(DepartmentDto dto) {
		DepartmentEntity entity = new DepartmentEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	private DepartmentDto convertToDto(DepartmentEntity entity) {
		DepartmentDto dto = new DepartmentDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	private ResponseStatus createResponseStatus(HttpStatus httpStatus, String message) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(String.valueOf(httpStatus.value()));
		responseStatus.setMessage(message);
		return responseStatus;
	}

	private boolean isDuplicateDepartment(boolean newFlag, DepartmentDto departmentDto) {

		Optional<DepartmentEntity> optional = departmentRepository
				.findByDepartmentName(departmentDto.getDepartmentName());
		if (!optional.isPresent()) {
			return false;
		} else {
			DepartmentEntity departmentEntity = optional.get();
			if (newFlag || departmentEntity.getDepartmentId() != departmentDto.getDepartmentId()) {
				return true;
			}
		}
		return false;
	}

	private boolean isDepartmentExist(Long id) {
		return departmentRepository.existsById(id);
	}

	private boolean isDepartmentExistByName(String departmentName) {
		Optional<DepartmentEntity> optional = departmentRepository.findByDepartmentName(departmentName);
		return optional.isPresent();
	}

}
