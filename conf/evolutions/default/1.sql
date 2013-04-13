# --- !Ups

create table presentation (
  id                        bigint not null,
  name                      varchar(255),
  description               text,
  presenter                 varchar(255),
  business_unit             varchar(255),
  slot_id                   bigint,
  track_id                  bigint,
  rank                      integer,
  constraint pk_presentation primary key (id))
;

create table slot (
  id                        bigint not null,
  start_time                timestamp,
  end_time                  timestamp,
  slot_type                 integer,
  constraint ck_slot_slot_type check (slot_type in (0,1,2)),
  constraint pk_slot primary key (id))
;

create table track (
  id                        bigint not null,
  name                      varchar(255),
  rank                      integer,
  constraint pk_track primary key (id))
;

create table account (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (id))
;

create sequence presentation_seq;

create sequence slot_seq;

create sequence track_seq;

create sequence account_seq;

alter table presentation add constraint fk_presentation_slot_1 foreign key (slot_id) references slot (id) on delete restrict on update restrict;
create index ix_presentation_slot_1 on presentation (slot_id);
alter table presentation add constraint fk_presentation_track_2 foreign key (track_id) references track (id) on delete restrict on update restrict;
create index ix_presentation_track_2 on presentation (track_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists presentation;

drop table if exists slot;

drop table if exists track;

drop table if exists account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists presentation_seq;

drop sequence if exists slot_seq;

drop sequence if exists track_seq;

drop sequence if exists account_seq;

