package com.melexis.test.eventconsumer;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class EventImportService {

	private static final Logger logger = Logger.getLogger(EventImportService.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void importEvent2Db(Exchange exchange) {
		MachineEvent evt = exchange.getIn().getBody(MachineEvent.class);

		Integer machineTypeId = importMachineType(evt.getMachineTypeEnum().getId());
		Integer machineId = importMachine(evt.getMachineID(), machineTypeId);
		Integer code = importErrorDefinition(evt.getErrorType());
		logger.info("Machine ID = " + evt.getMachineID() + ", ID = " + machineId);
		logger.info("Error code = " + code);
		importError(machineId, code, Timestamp.valueOf(evt.getDateTime()));
	}

	protected Integer importMachineType(String machineType) {
		Integer result = null;
		try {
			result = jdbcTemplate.queryForObject("select id from machine_event.machine_type where type_name = ?",
					(rs, rowNum) -> Integer.valueOf(rs.getInt("id")), machineType);
			return result;
		} catch (EmptyResultDataAccessException e) {
			logger.info("MachineType not found! Value = " + machineType);
		}
		jdbcTemplate.update("insert into machine_event.machine_type (type_name) values (?)", machineType);
		result = jdbcTemplate.queryForObject("select id from machine_event.machine_type where type_name = ?",
				(rs, rowNum) -> Integer.valueOf(rs.getInt("id")), machineType);
		return result;
	}

	protected Integer importMachine(String machineId, Integer machineTypeId) {
		Integer result = null;
		try {
			result = jdbcTemplate.queryForObject("select id from machine_event.machine where machine_id = ?",
					(rs, rowNum) -> Integer.valueOf(rs.getInt("id")), machineId);
			return result;
		} catch (EmptyResultDataAccessException e) {
			logger.info("Machine not found! ID = " + machineId);
		}
		jdbcTemplate.update("insert into machine_event.machine (machine_id, machine_type_id) values (?, ?)", machineId,
				machineTypeId);
		result = jdbcTemplate.queryForObject("select id from machine_event.machine where machine_id = ?",
				(rs, rowNum) -> Integer.valueOf(rs.getInt("id")), machineId);
		return result;
	}

	protected Integer importErrorDefinition(String detail) {
		Integer result = null;
		try {
			result = jdbcTemplate.queryForObject("select code from machine_event.error_definition where detail = ?",
					(rs, rowNum) -> Integer.valueOf(rs.getInt("code")), detail);
			return result;
		} catch (EmptyResultDataAccessException e) {
			logger.info("Error Code not found! Detail = " + detail);
		}
		jdbcTemplate.update("insert into machine_event.error_definition (detail) values (?)", detail);
		result = jdbcTemplate.queryForObject("select code from machine_event.error_definition where detail = ?",
				(rs, rowNum) -> Integer.valueOf(rs.getInt("code")), detail);
		return result;
	}

	protected Integer importError(Integer machineId, Integer error_code, Timestamp time_stamp) {
		return jdbcTemplate.update("insert into machine_event.error (machine_id, error_code, time_stamp) values (?, ?, ?)", machineId, error_code, time_stamp);
	}
}
