create table if not exists `xxw-order-1`.undo_log
(
    branch_id     bigint       not null comment 'branch transaction id',
    xid           varchar(128) not null comment 'global transaction id',
    context       varchar(128) not null comment 'undo_log context,such as serialization',
    rollback_info longblob     not null comment 'rollback info',
    log_status    int          not null comment '0:normal status,1:defense status',
    log_created   datetime(6)  not null comment 'create datetime',
    log_modified  datetime(6)  not null comment 'modify datetime',
    constraint ux_undo_log
    unique (xid, branch_id)
    )
    comment 'AT transaction mode undo table' charset = utf8mb4;

create table if not exists `xxw-order-1`.xxw_order
(
    id              mediumint unsigned auto_increment
    primary key,
    order_sn        varchar(32)            default ''     null,
    user_id         mediumint unsigned     default '0'    null,
    order_status    int unsigned           default '0'    null,
    shipping_status tinyint unsigned       default '0'    null,
    pay_status      tinyint unsigned       default '0'    null,
    consignee       varchar(60)            default ''     null,
    country         varchar(50)                           null,
    province        varchar(50)                           null,
    city            varchar(50)                           null,
    district        varchar(50)                           null,
    address         varchar(255)           default ''     null,
    mobile          varchar(60)            default ''     null,
    postscript      varchar(255)           default ''     null,
    shipping_id     tinyint                default 0      null,
    shipping_name   varchar(120)           default ''     null,
    pay_id          varchar(64)            default '0'    null,
    pay_name        varchar(120)           default ''     null,
    shipping_fee    decimal(10, 2)         default 0.00   null,
    actual_price    decimal(10, 2)         default 0.00   null comment '实际需要支付的金额',
    integral        int unsigned           default '0'    null,
    integral_money  decimal(10, 2)         default 0.00   null,
    order_price     decimal(10, 2)         default 0.00   null comment '订单总价',
    goods_price     decimal(10, 2)         default 0.00   null comment '商品总价',
    add_time        datetime                              null,
    confirm_time    datetime                              null,
    pay_time        datetime                              null,
    freight_price   int unsigned           default '0'    null comment '配送费用',
    coupon_id       mediumint unsigned     default '0'    null comment '使用的优惠券id',
    parent_id       mediumint unsigned     default '0'    null,
    coupon_price    decimal(10, 2)                        null,
    callback_status enum ('true', 'false') default 'true' null,
    shipping_no     varchar(32)                           null,
    full_cut_price  decimal(10, 2)                        null,
    order_type      varchar(1)                            null,
    constraint order_sn
    unique (order_sn)
    )
    charset = utf8;

create index order_status
    on `xxw-order-1`.xxw_order (order_status);

create index pay_id
    on `xxw-order-1`.xxw_order (pay_id);

create index pay_status
    on `xxw-order-1`.xxw_order (pay_status);

create index shipping_id
    on `xxw-order-1`.xxw_order (shipping_id);

create index shipping_status
    on `xxw-order-1`.xxw_order (shipping_status);

create index user_id
    on `xxw-order-1`.xxw_order (user_id);

create table if not exists `xxw-order-1`.xxw_order_0
(
    id              mediumint unsigned auto_increment
    primary key,
    order_sn        varchar(32)            default ''     null,
    user_id         mediumint unsigned     default '0'    null,
    order_status    int unsigned           default '0'    null,
    shipping_status tinyint unsigned       default '0'    null,
    pay_status      tinyint unsigned       default '0'    null,
    consignee       varchar(60)            default ''     null,
    country         varchar(50)                           null,
    province        varchar(50)                           null,
    city            varchar(50)                           null,
    district        varchar(50)                           null,
    address         varchar(255)           default ''     null,
    mobile          varchar(60)            default ''     null,
    postscript      varchar(255)           default ''     null,
    shipping_id     tinyint                default 0      null,
    shipping_name   varchar(120)           default ''     null,
    pay_id          varchar(64)            default '0'    null,
    pay_name        varchar(120)           default ''     null,
    shipping_fee    decimal(10, 2)         default 0.00   null,
    actual_price    decimal(10, 2)         default 0.00   null comment '实际需要支付的金额',
    integral        int unsigned           default '0'    null,
    integral_money  decimal(10, 2)         default 0.00   null,
    order_price     decimal(10, 2)         default 0.00   null comment '订单总价',
    goods_price     decimal(10, 2)         default 0.00   null comment '商品总价',
    add_time        datetime                              null,
    confirm_time    datetime                              null,
    pay_time        datetime                              null,
    freight_price   int unsigned           default '0'    null comment '配送费用',
    coupon_id       mediumint unsigned     default '0'    null comment '使用的优惠券id',
    parent_id       mediumint unsigned     default '0'    null,
    coupon_price    decimal(10, 2)                        null,
    callback_status enum ('true', 'false') default 'true' null,
    shipping_no     varchar(32)                           null,
    full_cut_price  decimal(10, 2)                        null,
    order_type      varchar(1)                            null,
    constraint order_sn
    unique (order_sn)
    )
    charset = utf8;

create index order_status
    on `xxw-order-1`.xxw_order_0 (order_status);

create index pay_id
    on `xxw-order-1`.xxw_order_0 (pay_id);

create index pay_status
    on `xxw-order-1`.xxw_order_0 (pay_status);

create index shipping_id
    on `xxw-order-1`.xxw_order_0 (shipping_id);

create index shipping_status
    on `xxw-order-1`.xxw_order_0 (shipping_status);

create index user_id
    on `xxw-order-1`.xxw_order_0 (user_id);

create table if not exists `xxw-order-1`.xxw_order_1
(
    id              mediumint unsigned auto_increment
    primary key,
    order_sn        varchar(32)            default ''     null,
    user_id         mediumint unsigned     default '0'    null,
    order_status    int unsigned           default '0'    null,
    shipping_status tinyint unsigned       default '0'    null,
    pay_status      tinyint unsigned       default '0'    null,
    consignee       varchar(60)            default ''     null,
    country         varchar(50)                           null,
    province        varchar(50)                           null,
    city            varchar(50)                           null,
    district        varchar(50)                           null,
    address         varchar(255)           default ''     null,
    mobile          varchar(60)            default ''     null,
    postscript      varchar(255)           default ''     null,
    shipping_id     tinyint                default 0      null,
    shipping_name   varchar(120)           default ''     null,
    pay_id          varchar(64)            default '0'    null,
    pay_name        varchar(120)           default ''     null,
    shipping_fee    decimal(10, 2)         default 0.00   null,
    actual_price    decimal(10, 2)         default 0.00   null comment '实际需要支付的金额',
    integral        int unsigned           default '0'    null,
    integral_money  decimal(10, 2)         default 0.00   null,
    order_price     decimal(10, 2)         default 0.00   null comment '订单总价',
    goods_price     decimal(10, 2)         default 0.00   null comment '商品总价',
    add_time        datetime                              null,
    confirm_time    datetime                              null,
    pay_time        datetime                              null,
    freight_price   int unsigned           default '0'    null comment '配送费用',
    coupon_id       mediumint unsigned     default '0'    null comment '使用的优惠券id',
    parent_id       mediumint unsigned     default '0'    null,
    coupon_price    decimal(10, 2)                        null,
    callback_status enum ('true', 'false') default 'true' null,
    shipping_no     varchar(32)                           null,
    full_cut_price  decimal(10, 2)                        null,
    order_type      varchar(1)                            null,
    constraint order_sn
    unique (order_sn)
    )
    charset = utf8;

create index order_status
    on `xxw-order-1`.xxw_order_1 (order_status);

create index pay_id
    on `xxw-order-1`.xxw_order_1 (pay_id);

create index pay_status
    on `xxw-order-1`.xxw_order_1 (pay_status);

create index shipping_id
    on `xxw-order-1`.xxw_order_1 (shipping_id);

create index shipping_status
    on `xxw-order-1`.xxw_order_1 (shipping_status);

create index user_id
    on `xxw-order-1`.xxw_order_1 (user_id);

create table if not exists `xxw-order-1`.xxw_order_2
(
    id              mediumint unsigned auto_increment
    primary key,
    order_sn        varchar(32)            default ''     null,
    user_id         mediumint unsigned     default '0'    null,
    order_status    int unsigned           default '0'    null,
    shipping_status tinyint unsigned       default '0'    null,
    pay_status      tinyint unsigned       default '0'    null,
    consignee       varchar(60)            default ''     null,
    country         varchar(50)                           null,
    province        varchar(50)                           null,
    city            varchar(50)                           null,
    district        varchar(50)                           null,
    address         varchar(255)           default ''     null,
    mobile          varchar(60)            default ''     null,
    postscript      varchar(255)           default ''     null,
    shipping_id     tinyint                default 0      null,
    shipping_name   varchar(120)           default ''     null,
    pay_id          varchar(64)            default '0'    null,
    pay_name        varchar(120)           default ''     null,
    shipping_fee    decimal(10, 2)         default 0.00   null,
    actual_price    decimal(10, 2)         default 0.00   null comment '实际需要支付的金额',
    integral        int unsigned           default '0'    null,
    integral_money  decimal(10, 2)         default 0.00   null,
    order_price     decimal(10, 2)         default 0.00   null comment '订单总价',
    goods_price     decimal(10, 2)         default 0.00   null comment '商品总价',
    add_time        datetime                              null,
    confirm_time    datetime                              null,
    pay_time        datetime                              null,
    freight_price   int unsigned           default '0'    null comment '配送费用',
    coupon_id       mediumint unsigned     default '0'    null comment '使用的优惠券id',
    parent_id       mediumint unsigned     default '0'    null,
    coupon_price    decimal(10, 2)                        null,
    callback_status enum ('true', 'false') default 'true' null,
    shipping_no     varchar(32)                           null,
    full_cut_price  decimal(10, 2)                        null,
    order_type      varchar(1)                            null,
    constraint order_sn
    unique (order_sn)
    )
    charset = utf8;

create index order_status
    on `xxw-order-1`.xxw_order_2 (order_status);

create index pay_id
    on `xxw-order-1`.xxw_order_2 (pay_id);

create index pay_status
    on `xxw-order-1`.xxw_order_2 (pay_status);

create index shipping_id
    on `xxw-order-1`.xxw_order_2 (shipping_id);

create index shipping_status
    on `xxw-order-1`.xxw_order_2 (shipping_status);

create index user_id
    on `xxw-order-1`.xxw_order_2 (user_id);

create table if not exists `xxw-order-1`.xxw_order_3
(
    id              mediumint unsigned auto_increment
    primary key,
    order_sn        varchar(32)            default ''     null,
    user_id         mediumint unsigned     default '0'    null,
    order_status    int unsigned           default '0'    null,
    shipping_status tinyint unsigned       default '0'    null,
    pay_status      tinyint unsigned       default '0'    null,
    consignee       varchar(60)            default ''     null,
    country         varchar(50)                           null,
    province        varchar(50)                           null,
    city            varchar(50)                           null,
    district        varchar(50)                           null,
    address         varchar(255)           default ''     null,
    mobile          varchar(60)            default ''     null,
    postscript      varchar(255)           default ''     null,
    shipping_id     tinyint                default 0      null,
    shipping_name   varchar(120)           default ''     null,
    pay_id          varchar(64)            default '0'    null,
    pay_name        varchar(120)           default ''     null,
    shipping_fee    decimal(10, 2)         default 0.00   null,
    actual_price    decimal(10, 2)         default 0.00   null comment '实际需要支付的金额',
    integral        int unsigned           default '0'    null,
    integral_money  decimal(10, 2)         default 0.00   null,
    order_price     decimal(10, 2)         default 0.00   null comment '订单总价',
    goods_price     decimal(10, 2)         default 0.00   null comment '商品总价',
    add_time        datetime                              null,
    confirm_time    datetime                              null,
    pay_time        datetime                              null,
    freight_price   int unsigned           default '0'    null comment '配送费用',
    coupon_id       mediumint unsigned     default '0'    null comment '使用的优惠券id',
    parent_id       mediumint unsigned     default '0'    null,
    coupon_price    decimal(10, 2)                        null,
    callback_status enum ('true', 'false') default 'true' null,
    shipping_no     varchar(32)                           null,
    full_cut_price  decimal(10, 2)                        null,
    order_type      varchar(1)                            null,
    constraint order_sn
    unique (order_sn)
    )
    charset = utf8;

create index order_status
    on `xxw-order-1`.xxw_order_3 (order_status);

create index pay_id
    on `xxw-order-1`.xxw_order_3 (pay_id);

create index pay_status
    on `xxw-order-1`.xxw_order_3 (pay_status);

create index shipping_id
    on `xxw-order-1`.xxw_order_3 (shipping_id);

create index shipping_status
    on `xxw-order-1`.xxw_order_3 (shipping_status);

create index user_id
    on `xxw-order-1`.xxw_order_3 (user_id);

