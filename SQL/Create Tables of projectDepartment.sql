DROP TABLE IF EXISTS employee,department,address;

DROP TYPE IF EXISTS sex;

CREATE TYPE sex					AS ENUM ('FEMALE','MALE','UNDECIDED');	--crea un enum

CREATE TABLE address
(
	id_address				BIGINT NOT NULL,
	street					VARCHAR(50) NOT NULL,
	house_number			INT NOT NULL,
	city					VARCHAR(50) NOT NULL,
	country					VARCHAR(50) NOT NULL,
	CONSTRAINT PK_address PRIMARY KEY(id_address)
);

CREATE SEQUENCE address_sequence
  start 1
  increment 1
  OWNED BY address.id_address;

CREATE TABLE department
(
	id_department			BIGINT NOT NULL,
	name					VARCHAR(32) NOT NULL,
	id_address				BIGINT NOT NULL,
	max_capacity			INT NOT NULL,
	CONSTRAINT PK_department PRIMARY KEY(id_department),
	CONSTRAINT FK_department_address FOREIGN KEY(id_address)
		REFERENCES address(id_address)
);

CREATE SEQUENCE department_sequence
  start 1
  increment 1
  OWNED BY department.id_department;

CREATE TABLE employee
(
  	id_employee			BIGINT NOT NULL,
  	name				VARCHAR(32) NOT NULL,
  	surname				VARCHAR(32) NOT NULL,
	hire_date  			DATE NOT NULL,
  	sex					sex NOT NULL,
  	id_department      	BIGINT NOT NULL,
  	CONSTRAINT PK_employee PRIMARY KEY(id_employee),
	CONSTRAINT FK_employee_department FOREIGN KEY(id_department)
		REFERENCES department(id_department)
);

CREATE SEQUENCE employee_sequence
  start 1
  increment 1
  OWNED BY employee.id_employee;
