CREATE TABLE "users" (
  "id" bigint NOT NULL,
  "role_id" bigint NOT NULL,
  "student_book_id" bigint NOT NULL,
  "department_id" bigint NOT NULL,
  "username" character varying NOT NULL,
  "first_name" character varying NOT NULL,
  "second_name" character varying NOT NULL,
  "rating" bigint NOT NULL,
  "password" character varying NOT NULL,
  "description" character varying,
  "birthday" timestamptz,
  "picture" character varying,
  "social_vk" character varying,
  "social_inst" character varying,
  "social_twi" character varying,
  "social_fb" character varying,
  CONSTRAINT users_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "department" (
  "id" bigint NOT NULL,
  "name" character varying NOT NULL,
  "description" character varying,
  "picture" character varying,
  "social_vk" character varying,
  "social_inst" character varying,
  "social_twi" character varying,
  "social_fb" character varying,
  CONSTRAINT department_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "news" (
  "id" bigint NOT NULL,
  "owner" bigint NOT NULL,
  "title" character varying NOT NULL,
  "description" character varying NOT NULL,
  "date_created" timestamptz,
  CONSTRAINT news_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "req_tournament" (
  "id" bigint NOT NULL,
  "department_id" bigint NOT NULL,
  "sponsor_id" bigint NOT NULL,
  "start_date" timestamptz NOT NULL,
  "end_date" timestamptz NOT NULL,
  "gift_picture" character varying NOT NULL,
  "gift_name" character varying NOT NULL,
  "gift_description" character varying NOT NULL,
  CONSTRAINT req_tournament_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "req_question" (
  "id" bigint NOT NULL,
  "owner_id" bigint NOT NULL,
  "start_date" timestamptz NOT NULL,
  "description" character varying NOT NULL,
  CONSTRAINT req_question_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "tournament" (
  "id" bigint NOT NULL,
  "sponsor_id" bigint NOT NULL,
  "department_id" bigint NOT NULL,
  "winner_id" bigint,
  "start_date" timestamptz NOT NULL,
  "end_date" timestamptz NOT NULL,
  "gift_picture" character varying NOT NULL,
  "gift_name" character varying NOT NULL,
  "gift_description" character varying NOT NULL,
  "status" bigint NOT NULL,
  "label" bigint,
  CONSTRAINT tournament_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "partisipants" (
  "tournament_id" bigint NOT NULL,
  "user_id" bigint NOT NULL
) WITH (
OIDS=FALSE
);


-- TABLE users
ALTER TABLE "users" ADD CONSTRAINT "USERS_fk0" FOREIGN KEY ("department_id") REFERENCES "department"("id");

-- TABLE tournament
ALTER TABLE "tournament" ADD CONSTRAINT "TOURNAMENTS_fk0" FOREIGN KEY ("sponsor_id") REFERENCES "users"("id");
ALTER TABLE "tournament" ADD CONSTRAINT "TOURNAMENTS_fk1" FOREIGN KEY ("winner_id") REFERENCES "users"("id");
ALTER TABLE "tournament" ADD CONSTRAINT "TOURNAMENTS_fk2" FOREIGN KEY ("department_id") REFERENCES "department"("id");

-- TABLE req_tournament
ALTER TABLE "req_tournament" ADD CONSTRAINT "REQ_TOURNAMENT_fk0" FOREIGN KEY ("sponsor_id") REFERENCES "users"("id");
ALTER TABLE "req_tournament" ADD CONSTRAINT "REQ_TOURNAMENT_fk1" FOREIGN KEY ("department_id") REFERENCES "department"("id");

-- TABLE req_question
ALTER TABLE "req_question" ADD CONSTRAINT "REQ_QUESTION_fk0" FOREIGN KEY ("owner_id") REFERENCES "users"("id");

-- TABLE partisipants
ALTER TABLE "partisipants" ADD CONSTRAINT "PARTISIPANTS_fk0" FOREIGN KEY ("tournament_id") REFERENCES "department"("id");
ALTER TABLE "partisipants" ADD CONSTRAINT "PARTISIPANTS_fk1" FOREIGN KEY ("user_id") REFERENCES "users"("id");
