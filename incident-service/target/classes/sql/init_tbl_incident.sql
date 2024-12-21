DROP TABLE IF EXISTS `tbl_incident`;

CREATE TABLE `tbl_incident`
(
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'incident id',
    status INT NOT NULL COMMENT 'incident status',
    incident_level INT NOT NULL COMMENT 'incident level',
    create_time DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'incident create time',
    update_time DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'incident update time',
    create_by VARCHAR(256) NULL DEFAULT NULL COMMENT 'incident creator',
    info VARCHAR(256) NULL DEFAULT NULL COMMENT 'incident info',
    PRIMARY KEY (id)
);

CREATE INDEX idx_incident_info ON tbl_incident (info);
CREATE INDEX idx_create_by ON tbl_incident (create_by);