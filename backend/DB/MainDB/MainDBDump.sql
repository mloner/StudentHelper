CREATE TABLE "Facultates" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL UNIQUE,
	CONSTRAINT "Facultates_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Teachers" (
	"id" serial NOT NULL,
	"FIO" varchar(255) NOT NULL,
	"cafedra_id" int NOT NULL,
	"position_id" int NOT NULL,
	"password" varchar(255) NOT NULL,
	CONSTRAINT "Teachers_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Students" (
	"id" serial NOT NULL,
	"FIO" varchar(255) NOT NULL,
	"group_id" int NOT NULL,
	"passport" varchar(255) NOT NULL,
	"record_book" varchar(255) NOT NULL,
	CONSTRAINT "Students_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Groups" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	"facultate_id" int NOT NULL,
	"course" int NOT NULL,
	CONSTRAINT "Groups_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "TeacherPositions" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT "TeacherPositions_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "SubjectNames" (
	"id" serial NOT NULL,
	"cathedra_id" int NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT "SubjectNames_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Lessons" (
	"id" serial NOT NULL,
	"subject_name_id" int NOT NULL,
	"teacher_id" int NOT NULL,
	"class_id" int NOT NULL,
	"lesson_type" int NOT NULL,
	"start_lesson_number" int NOT NULL,
	"lesson_duration" int NOT NULL,
	"group_id" int NOT NULL,
	"date" DATE NOT NULL,
	"remote" BOOLEAN NOT NULL,
	"subgroup" int NOT NULL,
	CONSTRAINT "Lessons_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "LessonTimes" (
	"id" serial NOT NULL,
	"number" int NOT NULL,
	"start_time" TIME NOT NULL,
	"end_time" TIME NOT NULL,
	CONSTRAINT "LessonTimes_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "LessonTypes" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT "LessonTypes_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Classes" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT "Classes_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Journal" (
	"id" serial NOT NULL,
	"lesson_id" int NOT NULL,
	"student_id" int NOT NULL,
	"precence" BOOLEAN NOT NULL,
	"mark" int NOT NULL,
	CONSTRAINT "Journal_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "ExamMarks" (
	"id" serial NOT NULL,
	"subject_name_id" int NOT NULL,
	"student_id" int NOT NULL,
	"exam_mark" int NOT NULL,
	CONSTRAINT "ExamMarks_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Cathedras" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	"facultate_id" int NOT NULL,
	CONSTRAINT "Cathedras_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "Teachers" ADD CONSTRAINT "Teachers_fk0" FOREIGN KEY ("cafedra_id") REFERENCES "Cathedras"("id");
ALTER TABLE "Teachers" ADD CONSTRAINT "Teachers_fk1" FOREIGN KEY ("position_id") REFERENCES "TeacherPositions"("id");

ALTER TABLE "Students" ADD CONSTRAINT "Students_fk0" FOREIGN KEY ("group_id") REFERENCES "Groups"("id");

ALTER TABLE "Groups" ADD CONSTRAINT "Groups_fk0" FOREIGN KEY ("facultate_id") REFERENCES "Facultates"("id");


ALTER TABLE "SubjectNames" ADD CONSTRAINT "SubjectNames_fk0" FOREIGN KEY ("cathedra_id") REFERENCES "Cathedras"("id");

ALTER TABLE "Lessons" ADD CONSTRAINT "Lessons_fk0" FOREIGN KEY ("subject_name_id") REFERENCES "SubjectNames"("id");
ALTER TABLE "Lessons" ADD CONSTRAINT "Lessons_fk1" FOREIGN KEY ("teacher_id") REFERENCES "Teachers"("id");
ALTER TABLE "Lessons" ADD CONSTRAINT "Lessons_fk2" FOREIGN KEY ("class_id") REFERENCES "Classes"("id");
ALTER TABLE "Lessons" ADD CONSTRAINT "Lessons_fk3" FOREIGN KEY ("lesson_type") REFERENCES "LessonTypes"("id");
ALTER TABLE "Lessons" ADD CONSTRAINT "Lessons_fk4" FOREIGN KEY ("group_id") REFERENCES "Groups"("id");




ALTER TABLE "Journal" ADD CONSTRAINT "Journal_fk0" FOREIGN KEY ("lesson_id") REFERENCES "Lessons"("id");
ALTER TABLE "Journal" ADD CONSTRAINT "Journal_fk1" FOREIGN KEY ("student_id") REFERENCES "Students"("id");

ALTER TABLE "ExamMarks" ADD CONSTRAINT "ExamMarks_fk0" FOREIGN KEY ("subject_name_id") REFERENCES "SubjectNames"("id");
ALTER TABLE "ExamMarks" ADD CONSTRAINT "ExamMarks_fk1" FOREIGN KEY ("student_id") REFERENCES "Students"("id");

ALTER TABLE "Cathedras" ADD CONSTRAINT "Cathedras_fk0" FOREIGN KEY ("facultate_id") REFERENCES "Facultates"("id");
