-- create table for EventDefinition
CREATE TABLE IF NOT EXISTS machine_event.error_definition (
	code integer NOT NULL,
	detail varchar NULL,
	CONSTRAINT error_definition_pk PRIMARY KEY (code)
);

-- create table for MachineType
CREATE TABLE IF NOT EXISTS machine_event.machine_type (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	type_name VARCHAR(100) NOT NULL,
	CONSTRAINT machine_type_pk PRIMARY KEY (id)
);

-- create table for Machine
CREATE TABLE IF NOT EXISTS machine_event.machine (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
	machine_id varchar(100) NOT NULL,
	machine_type_id integer NULL,
	CONSTRAINT machine_pk PRIMARY KEY (id),
	CONSTRAINT machine_un UNIQUE (machine_id),
	CONSTRAINT machine_fk FOREIGN KEY (machine_type_id) REFERENCES machine_event.machine_type(id)
);

-- create table for Error
CREATE TABLE IF NOT EXISTS machine_event.error (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
	error_code integer NOT NULL,
	time_stamp timestamptz(0) NOT NULL,
	machine_id integer NOT NULL,
	CONSTRAINT error_pk PRIMARY KEY (id),
	CONSTRAINT error_fk FOREIGN KEY (error_code) REFERENCES machine_event.error_definition(code),
	CONSTRAINT error_fk_1 FOREIGN KEY (machine_id) REFERENCES machine_event.machine(id)
);
