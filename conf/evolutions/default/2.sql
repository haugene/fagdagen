# --- !Ups

create table vote (
  id                        bigint not null,
  session_uuid              varchar(255),
  presentation_id           bigint,
  rating                    integer,
  constraint pk_vote primary key (id))
;

create sequence vote_seq;

alter table vote add constraint fk_vote_presentation_1 foreign key (presentation_id) references presentation (id) on delete restrict on update restrict;
create index ix_vote_presentation_1 on vote (presentation_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists presentation;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists presentation_seq;