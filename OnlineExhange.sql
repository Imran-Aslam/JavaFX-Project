/*
      Online Exchange Project
          4th Semester
*/

Create Table registeredUser
(
userID int NOT NULL,
name varchar(35),
lastName varchar(35),
gender varchar(7),
mobileNo varchar(12),
address varchar(120),
userName varchar(40) NOT NULL,
password varchar(20) NOT NULL, 
pic blob,
registeredDate date,
Constraints user_pk Primary Key (userID)
);
Alter Table registeredUser
ADD (city varchar(25));
Alter Table registeredUser
ADD Constraints user_uq UNIQUE (userName);
Alter Table registeredUser
Rename column fatherName to lastName;


/* Trigger of Registered User */
CREATE OR REPLACE TRIGGER User_trigger
BEFORE INSERT
ON registeredUser
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT seq_user.nextval INTO :NEW.userID FROM dual;
:NEW.registeredDate := SYSDATE;
END;
/


Create Table addPost
(
postID int NOT NULL,
userID int NOT NULL,
postName varchar(50),
postDescription varchar(120) NOT NULL,
postCategory varchar(40),
postSubcategory varchar(40),
postPrice int,
postCity varchar(25),
postAddress varchar(120),
postSatus varchar(5),
postDate date,
Constraints addPost_pk Primary Key (postID),
Constraints addPost_fk Foreign Key (userID) References registeredUser(userID)
);
/* Trigger For Add Post */
CREATE OR REPLACE TRIGGER Post_trigger
BEFORE INSERT
ON addPost
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT seq_post.nextval INTO :NEW.postID FROM dual;
:NEW.postDate := SYSDATE;
END;
/

Create Table postImages
(
picID int NOT NULL,
postID int NOT NULL,
postPic blob,
Constraints postIamges_pk Primary Key (picID),
Constraints postImages_fk Foreign Key (postID) References addPost(postID)
);
/* Trigger For Post Images */
CREATE OR REPLACE TRIGGER Imges_trigger
BEFORE INSERT
ON postImages
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT seq_postPic.nextval INTO :NEW.picID FROM dual;
SELECT seq_post.currval INTO :NEW.postID FROM dual;
END;
/
/*
CREATE OR REPLACE TRIGGER Imges_trigger2
BEFORE INSERT
ON postImges
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT seq_post.currval INTO :NEW.postID FROM dual;
END;
/                                                             */

Create Table category
(
cate_ID int NOT NULL,
cate_name varchar(40),
cate_subcategory varchar(90),
Constraints category_pk Primary Key (cate_ID)
);

CREATE SEQUENCE seq_user
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_post
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_postPic
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10;

--CREATE SEQUENCE seq
--MINVALUE 1
--START WITH 1
--INCREMENT BY 1
--CACHE 10;

Select addPost.postID,addPost.postPrice,postImages.postPic From addPost INNER JOIN postImages ON addPost.postID=postImages.postID where postSubcategory='Bikes' AND (addPost.postPrice between 15000 and 30000);
Select addPost.userID,addPost.postName,addPost.postDescription,addPost.postPrice,addPost.postCity,addPost.postAddress, postImges.postPic From addPost INNER JOIN postImges ON addPost.postID=postImges.postID Where addPost.postID=1;
*/