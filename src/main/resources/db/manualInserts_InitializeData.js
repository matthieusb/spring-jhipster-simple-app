/*
    initializeData.js : Initialize mongodb database for project tests

    TO LAUNCH IT : mongo --port 27017 initializeData.js
*/


// -- Used variables
var db;

print('##### Connecting to the apiApertureBdd database ####');
db = db.getSiblingDB('apiApertureDB');


print('##### Wiping previous data ####');
db.dropDatabase();


// ---------- Testing supervisors
print('##### Adding testing supervisors ####');

db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b004"), "login" : "glados@aperture.fr", "pass" : "caroline"});
db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b005"), "login" : "wheatley@aperture.fr", "pass" : "iminspace"});
db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b006"), "login" : "fd@aperture.fr", "pass" : "fd"});
db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b007"), "login" : "saubouaben@aperture.fr", "pass" : "sb"});
db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b008"), "login" : "test", "pass" : "test"});
db.TestSupervisors.insert({"_id" : ObjectId("5063114bd386d8fadbd6b009"), "login" : "t", "pass" : "t"});

// ---------- Rooms
print("##### Adding Rooms ####");

db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "number" : 42, "name" : "Answer to life room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00b"), "number" : 4, "name" : "Paths"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "number" : 36, "name" : "6x6"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "number" : 21, "name" : "Blackjack room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "number" : 1000, "name" : "Cake room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "number" : 404, "name" : "Room Not found"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "number" : 666, "name" : "Turret room"});
db.Rooms.insert({"_id" : ObjectId("5063114bd386d8fadbd6b03a"), "number" : 123, "name" : "Sun room"});

// ---------- TestSubjects
print("##### Adding test TestSubjects ####");

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00a"),
		"name" : "Chell",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00b"), "number" : 4, "name" : "Paths"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "number" : 36, "name" : "6x6"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "number" : 42, "name" : "Answer to life room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "number" : 21, "name" : "Blackjack room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b03a"), "number" : 123, "name" : "Sun room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "number" : 404, "name" : "Room Not found"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "number" : 666, "name" : "Turret room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "number" : 1000, "name" : "Cake room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00b"),
		"name" : "Doug Rattman",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00a"), "number" : 42, "name" : "Answer to life room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00e"), "number" : 21, "name" : "Blackjack room"}
		]
	}
);


db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00c"),
		"name" : "Companion Cube",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b02a"), "number" : 666, "name" : "Turret room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00d"),
		"name" : "Cave Johnson",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00f"), "number" : 1000, "name" : "Cake room"}
		]
	}
);

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00e"),
		"name" : "Caroline",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b00c"), "number" : 36, "name" : "6x6"}
		]
	}
);

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b00f"),
		"name" : "Atlas",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "number" : 404, "name" : "Room Not found"}
		]
	}
);

db.TestSubjects.insert(
	{
		"_id" : ObjectId("5063114bd386d8fadbd6b01a"),
		"name" : "P-Body",
		"rooms": [
			{"_id" : ObjectId("5063114bd386d8fadbd6b00d"), "number" : 1, "name" : "Initiation room"},
			{"_id" : ObjectId("5063114bd386d8fadbd6b01a"), "number" : 404, "name" : "Room Not found"}
		]
	}
);
