--<ScriptOptions statementTerminator=";"/>

ALTER TABLE "PROJECTUSER"."ACCOUNT" DROP CONSTRAINT "ACCOUNT_FK2";

ALTER TABLE "PROJECTUSER"."ACCOUNT" DROP CONSTRAINT "ACCOUNT_FK1";

ALTER TABLE "PROJECTUSER"."ACCOUNT" DROP CONSTRAINT "ACCOUNT_PK";

DROP INDEX "PROJECTUSER"."ACCOUNT_PK";

DROP TABLE "PROJECTUSER"."ACCOUNT";

CREATE TABLE "PROJECTUSER"."ACCOUNT" (
		"ACCID" NUMBER(22 , 0) NOT NULL,
		"ACCOUNTMASTERID" NUMBER(22 , 0),
		"DATASETMASTERID" NUMBER(22 , 0) NOT NULL
	);

CREATE UNIQUE INDEX "PROJECTUSER"."ACCOUNT_PK" ON "PROJECTUSER"."ACCOUNT" ("ACCID" ASC);

ALTER TABLE "PROJECTUSER"."ACCOUNT" ADD CONSTRAINT "ACCOUNT_PK" PRIMARY KEY ("ACCID");

ALTER TABLE "PROJECTUSER"."ACCOUNT" ADD CONSTRAINT "ACCOUNT_FK2" FOREIGN KEY ("DATASETMASTERID")
	REFERENCES "PROJECTUSER"."DATASETMASTER" ("DATASETID")
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE "PROJECTUSER"."ACCOUNT" ADD CONSTRAINT "ACCOUNT_FK1" FOREIGN KEY ("ACCOUNTMASTERID")
	REFERENCES "PROJECTUSER"."ACCOUNTMASTER" ("ACCOUNTID")
	ON DELETE CASCADE
	ON UPDATE CASCADE;

