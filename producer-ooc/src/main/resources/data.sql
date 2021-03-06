DROP TABLE IF EXISTS APPLICATION_PROCESS;
DROP TABLE IF EXISTS APPLICATION_LOG;
DROP TABLE IF EXISTS APPLICATION_STATUS;

CREATE TABLE APPLICATION_PROCESS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  APPLICATION_NUMBER VARCHAR2(255 BYTE),
  APPLICATION_ACTION VARCHAR2(100 BYTE),
  APPLICATION_DESC VARCHAR2(500 BYTE),
  BUREAU_STATUS VARCHAR2(50 BYTE),
  TSYS_STATUS VARCHAR2(50 BYTE),
  AJDC_STATUS VARCHAR2(50 BYTE),
  LOG VARCHAR2(500 BYTE),
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE APPLICATION_LOG (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  APPLICATION_NUMBER VARCHAR2(255 BYTE),
  LOG VARCHAR2(500 BYTE),
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE APPLICATION_STATUS (
  APPLICATION_NUMBER VARCHAR2(255 BYTE) PRIMARY KEY,
  BUREAU_STATUS VARCHAR2(50 BYTE),
  AJDC_STATUS VARCHAR2(50 BYTE),
  TSYS_STATUS VARCHAR2(50 BYTE),
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  LAST_UPDATED_DATE TIMESTAMP 
);