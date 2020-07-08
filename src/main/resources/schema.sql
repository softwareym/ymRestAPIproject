
DROP TABLE IF EXISTS tblSprinkle;
DROP TABLE IF EXISTS tblReceivers;

CREATE TABLE tblSprinkle(
   seq LONG PRIMARY KEY AUTO_INCREMENT
  , token VARCHAR(3) NOT NULL
  , roomId VARCHAR NOT NULL
  , sprinklerId INT NOT NULL
  , sprinklerPrice INT NOT NULL
  , receiverCnt INT NOT NULL
  , regDate timestamp NOT NULL
  , validTime timestamp NOT NULL
  , deleteYn VARCHAR NOT NULL
);

CREATE TABLE tblReceivers(
   dtlSeq LONG PRIMARY KEY AUTO_INCREMENT
  , token VARCHAR(3) NOT NULL
  , receiverId INT NOT NULL
  , receiverPrice INT NOT NULL
  , receiveDate timestamp NOT NULL
);

ALTER TABLE tblReceivers
ADD FOREIGN KEY (token)
REFERENCES tblSprinkle(token);

