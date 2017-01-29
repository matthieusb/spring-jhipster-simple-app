/*
    initializeData.js : Initialize mongodb database for project tests

    TO LAUNCH IT : mongo --port 28042 initializeData.js
*/


// -- Constants
var host = "localhost";
var port = "28042";
var database = "apiApertureBdd";
//var connectionString = host.concat(port).concat(datbase);

// -- Used variables
var db;

print("##### Connecting to the database ####");
db = db.getSiblingDB('apiApertureBdd');


print("##### Wiping previous data ####");
db.dropDatabase();


// ---------- 
print("##### Adding testing supervisors ####");

db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b004"), "login" : "glados@aperture.fr", "pass" : "caroline"});
db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b005"), "login" : "wheatley@aperture.fr", "pass" : "iminspace"});
db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b006"), "login" : "fd@aperture.fr", "pass" : "fd"});
db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b007"), "login" : "saubouaben@aperture.fr", "pass" : "sb"});
db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b008"), "login" : "test", "pass" : "test"});
db.TestSupervisor.insert({"_id" : ObjectId("5063114bd386d8fadbd6b009"), "login" : "t", "pass" : "t"});

// ---------- 
print("##### Adding Rooms ####");

db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "numero" : 42, "name" : "Answer to life room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00b"), "numero" : 4, "name" : "Paths"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "numero" : 36, "name" : "6x6"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "numero" : 21, "name" : "Blackjack room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "numero" : 1000, "name" : "Cake room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "numero" : 404, "name" : "Room Not found"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "numero" : 666, "name" : "Turret room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b03a"), "numero" : 123, "name" : "Sun room"});

// ---------- 
print("##### Adding test TestSubjects ####");

db.TestSubjects.insert(
	{
		"name" : "Chell", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00b"), "numero" : 4, "name" : "Paths"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "numero" : 36, "name" : "6x6"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "numero" : 42, "name" : "Answer to life room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "numero" : 21, "name" : "Blackjack room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b03a"), "numero" : 123, "name" : "Sun room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "numero" : 404, "name" : "Room Not found"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "numero" : 666, "name" : "Turret room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "numero" : 1000, "name" : "Cake room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"name" : "Doug Rattman", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "numero" : 42, "name" : "Answer to life room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "numero" : 21, "name" : "Blackjack room"}
		]
	}
);


db.TestSubjects.insert(
	{
		"name" : "Companion Cube", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "numero" : 666, "name" : "Turret room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"name" : "Cave Johnson", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "numero" : 1000, "name" : "Cake room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"name" : "Caroline", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "numero" : 36, "name" : "6x6"}
		]
	}
);

db.TestSubjects.insert(
	{
		"name" : "Atlas", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "numero" : 404, "name" : "Room Not found"}
		]
	}
);

db.TestSubjects.insert(
	{
		"name" : "P-Body", 
		"Rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "numero" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "numero" : 404, "name" : "Room Not found"}
		]
	}
);