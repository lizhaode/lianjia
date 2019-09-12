create table `for_sale_house`
(

    `id`               int          not null auto_increment,
    `house_name`       varchar(20)  not null comment '小区名字',
    `area_scope`       varchar(20)  not null comment '小区面积范围',
    `room_scope`       varchar(20)  not null comment '居室数量',
    `district`         varchar(100) not null comment '行政区',
    `street`           varchar(100) not null comment '行政街道',
    `address`          varchar(100) not null comment '比较详细的地址',
    `price`            int                   default 0 comment '均价',
    `total_price`      int                   default 0 comment '总价',
    `total_price_unit` varchar(20) comment '总价格单位',
    `url`              text                  default null comment '楼盘链接',
    `create_timestamp` timestamp    not null default current_timestamp,
    `update_timestamp` timestamp    not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    key `house_name` (`house_name`),
    key `district` (`district`),
    key `street` (`street`)
) engine = InnoDB
  default charset = utf8mb4;
