create table comment
(
    id          int auto_increment,
    parent      int not null,
    type        int not null,
    commentator int not null,
    gmt_create  bigint,
    gmt_modify  bigint,
    like_count  int default 0,
    constraint COMMENT_PK
        primary key (id)
);