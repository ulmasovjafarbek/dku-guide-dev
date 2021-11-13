alter table professor_tags add column tag_count integer;
alter table professor_tags alter column tag_count set default 0;