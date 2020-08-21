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
Constraints user_pk Primary Key (userID),
Constraints user_uq UNIQUE (userName)
);
/*
Ad Post
*/
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
/*
Post Image
*/
Create Table postImages
(
picID int NOT NULL,
postID int NOT NULL,
postPic blob,
Constraints postIamges_pk Primary Key (picID),
Constraints postImages_fk Foreign Key (postID) References addPost(postID)
);
/*
Category
*/
Create Table category
(
cate_ID int NOT NULL,
cate_name varchar(40),
cate_subcategory varchar(90),
Constraints category_pk Primary Key (cate_ID)
);

/*
* Procedure for Registered User
*/
Create Or Replace Procedure insertUser (u_name varchar,u_lastNmae varchar,u_gender varchar,u_mobile varchar,u_address varchar,u_username varchar,u_password varchar,u_pic blob,u_registerDate date)
IS
BEGIN
Insert into registeredUser (name,lastName,gender,mobileno,address,username,password,registereddate) values(u_name,u_lastName,u_gender,u_mobile,u_address,u_username,u_password,u_registerDate);
COMMIT;
END;
/
--Procedure Call
BEGIN 
insertUser (1,'Imran','Aslam','Male','03477645245','Islamabad','imran@240','12345',utl_raw.cast_to_raw('D:\imran\picImran.jpg'),'16-july-2020');
insertUser (2,'Wahab','Nawaz','Male','03477645245','PWD','wahab@23','1234',utl_raw.cast_to_raw('D:\imran\picImran.jpg'),'17-july-2020');
END;
/

/*
*Add Post and Images Procedure
*/
Create Or Replace Procedure insertPost(p_ID int,u_ID int,p_name varchar,p_des varchar,p_category varchar,p_subCategory VARCHAR2,p_price int,
p_city varchar,p_address varchar,p_status varchar,p_date date,pi_ID int,pi_pic blob)
IS
BEGIN
insert into addPost values(p_ID,u_ID,p_name,p_des,p_category,p_subCategory,p_price,p_city,p_address,p_status,p_date);
insert into PostImages values (pi_ID,p_ID,pi_pic);
COMMIT;
END;
/
--Procedure Call
BEGIN
insertPost(4,1,'Car','Civic Honda Model 2016','Vehicles','Car',3033000,'Islamabad','I9','Used','16-july-2020',11,utl_raw.cast_to_raw('D:\imran\download(1).jpg'));
END;
/
/*
* Procedure for Update the user data
*/
Create Or Replace Procedure updateUser(u_ID int,u_name varchar,u_lastNmae varchar,u_gender varchar,u_mobile varchar,u_address varchar,u_password varchar)
IS
BEGIN
UPDATE registeredUser set name = u_name,lastName=u_lastName,gender=u_gender,mobileNo=u_mobile,address=u_address,password=u_password
WHERE userID=u_ID;
COMMIT;
END;
/
/*
* Procedure for Delete the post and post Image
*/
Create Or Replace Procedure deletePost(p_ID int)
IS
BEGIN
DELETE FROM addPost WHERE postID=p_ID;
DELETE FROM postImages WHERE postID=p_ID;
COMMIT;
END;
/
/*
* Procedure for Getting category Post
*/
Create Or Replace Procedure getPosts(category varchar)
IS
BEGIN
Select addPost.postID,addPost.postPrice,postImages.postPic From addPost INNER JOIN postImages ON addPost.postID=postImages.postID where postSubcategory=category;
COMMIT;
END;
/
--Procedure Call
BEGIN
getPosts('Car');
END;
/
/*
* Procedure For Getting one post info
*/
Create Or Replace Procedure getPostInfo(p_ID int)
IS
BEGIN
Select addPost.userID,addPost.postName,addPost.postDescription,addPost.postPrice,addPost.postCity,addPost.postAddress, postImages.postPic From addPost
INNER JOIN postImages 
ON addPost.postID=postImages.postID Where addPost.postID=p_ID;
COMMIT;
END;
--Procedure Call
BEGIN
getPostInfo(11);
END;
