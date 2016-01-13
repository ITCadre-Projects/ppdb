-- Change these locations to match your available locations
CREATE TABLESPACE all_projects LOCATION '/tablespaces/all_projects';
CREATE TABLESPACE bd_projects LOCATION '/tablespaces/bd_projects';

CREATE database project_db tablespace all_projects owner all_user;
CREATE database project_bd_db tablespace bd_projects owner bd_user;

GRANT ALL PRIVILEGES ON DATABASE project_db to bd_user;
GRANT ALL PRIVILEGES ON DATABASE project_bd_db to bd_user;

\c project_db

CREATE table project (
	project_name varchar(50) constraint project_pk PRIMARY KEY,
	project_start_date date,
	project_end_date date,
	generic_version varchar(50),
	project_summary_name varchar(50)
) TABLESPACE all_projects;

CREATE table client_info (
	project_name varchar(50),
	client_name varchar(50),
	title varchar(50),
	industry varchar(50),
	organization varchar(50),
	client_email varchar(50),
	client_phone_number varchar(50),
	CONSTRAINT client_project_name_fk FOREIGN KEY (project_name)
      REFERENCES project (project_name)
      ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE all_projects;

CREATE table project_lead (
	project_name varchar(50),
	project_lead_name varchar(50),	
	CONSTRAINT lead_project_name_fk FOREIGN KEY (project_name)
      REFERENCES project (project_name)
      ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE all_projects;

CREATE table project_problem (
	project_name varchar(50),
	project_problem_description text,
	CONSTRAINT problem_project_name_fk FOREIGN KEY (project_name)
      REFERENCES project (project_name)
      ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE all_projects;

CREATE table project_results (
	project_name varchar(50),
	text_field text,
	exemplar varchar(50),
	version integer,
	CONSTRAINT results_project_name_fk FOREIGN KEY (project_name)
      REFERENCES project (project_name)
      ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE all_projects;

CREATE table project_summary (
	project_summary_name varchar(50) constraint project_summary_pk PRIMARY KEY,
	project_name varchar(50),
	task	varchar(50),
	goals	varchar(50),
	CONSTRAINT results_project_name_fk FOREIGN KEY (project_name)
      REFERENCES project (project_name)
      ON UPDATE CASCADE ON DELETE CASCADE
) TABLESPACE all_projects;

alter table project_summary owner to all_user;
alter table project_results owner to all_user;
alter table project_problem owner to all_user;
alter table project_lead owner to all_user;
alter table client_info owner to all_user;
alter table project owner to all_user;

\c project_bd_db

CREATE table contract (
	project_summary_name varchar(50),
	prime varchar(50),
	contract_value varchar(50),
	contract_start_date date,
	contract_end_date date,
	text_field text
) TABLESPACE bd_projects;

CREATE SEQUENCE quote_id_seq;

CREATE table project_quote (
	quote_id integer NOT NULL DEFAULT nextval('quote_id_seq'),
	project_summary_name varchar(50),
	quote_client_name varchar(50),
	quote_date varchar(50),
	quote_organization varchar(50),
	quote_text text
) TABLESPACE bd_projects;

ALTER SEQUENCE quote_id_seq OWNED BY project_quote.quote_id;

CREATE table historical_proposals (
	project_summary_name varchar(50),
	proposal_name varchar(50),
	date_submitted date,
	organization_submitted varchar(50),
	version integer,
	text_field text
) TABLESPACE bd_projects;

alter table historical_proposals owner to bd_user;
alter table project_quote owner to bd_user;
alter table contract owner to bd_user;




