create table if not exists seata.branch_table
(
    branch_id         bigint        not null
    primary key,
    xid               varchar(128)  not null,
    transaction_id    bigint        null,
    resource_group_id varchar(32)   null,
    resource_id       varchar(256)  null,
    branch_type       varchar(8)    null,
    status            tinyint       null,
    client_id         varchar(64)   null,
    application_data  varchar(2000) null,
    gmt_create        datetime(6)   null,
    gmt_modified      datetime(6)   null
    )
    charset = utf8mb4;

create index idx_xid
    on seata.branch_table (xid);

create table if not exists seata.distributed_lock
(
    lock_key   char(20)    not null
    primary key,
    lock_value varchar(20) not null,
    expire     bigint      null
    )
    charset = utf8mb4;

create table if not exists seata.global_table
(
    xid                       varchar(128)  not null
    primary key,
    transaction_id            bigint        null,
    status                    tinyint       not null,
    application_id            varchar(32)   null,
    transaction_service_group varchar(32)   null,
    transaction_name          varchar(128)  null,
    timeout                   int           null,
    begin_time                bigint        null,
    application_data          varchar(2000) null,
    gmt_create                datetime      null,
    gmt_modified              datetime      null
    )
    charset = utf8mb4;

create index idx_status_gmt_modified
    on seata.global_table (status, gmt_modified);

create index idx_transaction_id
    on seata.global_table (transaction_id);

create table if not exists seata.lock_table
(
    row_key        varchar(128)      not null
    primary key,
    xid            varchar(128)      null,
    transaction_id bigint            null,
    branch_id      bigint            not null,
    resource_id    varchar(256)      null,
    table_name     varchar(32)       null,
    pk             varchar(36)       null,
    status         tinyint default 0 not null comment '0:locked ,1:rollbacking',
    gmt_create     datetime          null,
    gmt_modified   datetime          null
    )
    charset = utf8mb4;

create index idx_branch_id
    on seata.lock_table (branch_id);

create index idx_status
    on seata.lock_table (status);

create index idx_xid_and_branch_id
    on seata.lock_table (xid, branch_id);


