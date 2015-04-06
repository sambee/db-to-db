SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM ${tableName}) A WHERE ROWNUM <= #{pageSize}) WHERE RN >= #{pageStart}


