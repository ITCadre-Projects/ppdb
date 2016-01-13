\c project_bd_db

drop table historical_proposals;

drop table project_quote;

drop table contract;

\c project_db

drop table project_results;

drop table project_problem;

drop table project_lead;

drop table client_info;

drop table project_summary;

drop table project;

\c postgres

drop database project_bd_db;
drop database project_db;

DROP TABLESPACE all_projects;
DROP TABLESPACE bd_projects;
