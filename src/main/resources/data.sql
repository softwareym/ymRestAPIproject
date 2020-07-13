INSERT INTO tblSprinkle (token, roomId, sprinklerId, sprinklerPrice, receiverCnt, regDate, validTime, deleteYn) VALUES ('ABC','aaa', 111,10000, 2, now(), DATEADD(DAY, 1, now()), 'N');
INSERT INTO tblReceivers(token, receiverId, receiverPrice, receiveDate ) VALUES ('ABC', 115, 2, now());
INSERT INTO tblReceivers(token, receiverId, receiverPrice, receiveDate ) VALUES ('ABC', 200, 200, now());
