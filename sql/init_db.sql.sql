CREATE TABLE worker (
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR NOT NULL CHECK(LENGTH(name) >=2 AND LENGTH(name) <= 1000),
	birthday DATE CHECK (YEAR(birthday) > 1900),
	level ENUM('Trainee', 'Junior', 'Middle', 'Senior'),
	salary INT CHECK (salary >= 100 AND salary <= 100000)
);

CREATE TABLE client (
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR NOT NULL CHECK(LENGTH(name) >=2 AND LENGTH(name) <= 1000)
);

CREATE TABLE project (
	id INT NOT NULL PRIMARY KEY,	
	client_id INT,
	start_date DATE,
	finish_date DATE
);

CREATE TABLE project_worker (
	project_id INT NOT NULL,
	worker_id INT NOT NULL,
	CONSTRAINT pk_worker_project PRIMARY KEY (project_id, worker_id)
);

ALTER TABLE project_worker
ADD CONSTRAINT fk_project_id
FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE project_worker
ADD CONSTRAINT fk_worker_id
FOREIGN KEY (worker_id) REFERENCES worker(id);