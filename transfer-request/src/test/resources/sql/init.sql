create schema if not exists transfer_request_service;
create table if not exists transfer_request_service.transfer_requests
(
    request_id   varchar(64)    not null
        primary key,
    from_account varchar(64)    not null,
    to_account   varchar(64)    not null,
    amount       numeric(19, 2) not null,
    currency     varchar(10)    not null,
    status       varchar(32)    not null,
    created_at   timestamp      not null,
    updated_at   timestamp      not null
);

create table if not exists transfer_request_service.outbox_events
(
    request_id    varchar(64)       not null,
    event_id      varchar(64)       not null
        primary key,
    event_type    varchar(64)       not null,
    topic_name    varchar(128)      not null,
    partition_key varchar(128),
    payload       text              not null,
    status        varchar(32)       not null,
    attempt_count integer default 0 not null,
    created_at    timestamp         not null,
    updated_at    timestamp         not null,
    published_at  timestamp
);