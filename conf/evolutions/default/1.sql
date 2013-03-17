
# --- !Ups

create table presentation (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  presenter                 varchar(255),
  slot_id                   bigint,
  track_id                  bigint,
  rank                      bigint,
  span_all_tracks           boolean,
  constraint pk_presentation primary key (id))
;

create table slot (
  id                        bigint not null,
  start_time                timestamp,
  end_time                  timestamp,
  constraint pk_slot primary key (id))
;

create table track (
  id                        bigint not null,
  name                      varchar(255),
  rank                      bigint,
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

alter table presentation add constraint fk_presentation_slot_1 foreign key (slot_id) references slot (id);
create index ix_presentation_slot_1 on presentation (slot_id);
alter table presentation add constraint fk_presentation_track_2 foreign key (track_id) references track (id);
create index ix_presentation_track_2 on presentation (track_id);



# --- !Downs

drop table if exists presentation cascade;

drop table if exists slot cascade;

drop table if exists track cascade;

drop table if exists account cascade;

drop sequence if exists presentation_seq;

drop sequence if exists slot_seq;

drop sequence if exists track_seq;

drop sequence if exists account_seq;

