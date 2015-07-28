#!/bin/sh

dbname="summarytask4"
username="faffi-ubuntu"
export PGPASSWORD="t9dnb2mq"
psql $dbname $username << EOF
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/CREATE_Tables_Functions.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/CREATE_Function_Sum_Marks.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Roles.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Users.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Entrants.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Faculties.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Subjects.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Faculties_Subjects.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Faculties_Entrants.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Marks.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Extra_Marks.sql
	\i /home/faffi-ubuntu/IdeaProjects/SummaryTask4/sql/INSERT_Entrants_final_sheets.sql
EOF
echo "OK!"