create schema if not exists transfer_processing_service;
create table if not exists transfer_processing_service.processed_events
(
    event_id      varchar(64) not null
        primary key,
    consumer_name varchar(64) not null,
    processed_at  timestamp   not null
);

create table if not exists transfer_processing_service.transfer_processing_results
(
    request_id        varchar(64) not null,
    event_id          varchar(64) not null,
    processing_status varchar(32) not null,
    message           varchar(255),
    processed_at      timestamp   not null
);