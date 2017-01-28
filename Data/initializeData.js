/*
    initializeData.js : Initialize mongodb database for project tests

    TO LAUNCH IT : mongo --port 28042 initializeData.js
*/


// -- Constants
var host = "localhost";
var port = "28042";
var database = "apiApertureBdd";


// -- Assigned variables
//var connectionString = host.concat(port);

// -- Used variables
var db;

print("##### Connecting to the database ####");

db = db.getSiblingDB('apiApertureBdd');


print("##### Wiping previous data ####");
db.dropDatabase();


// ---------- 
print("##### Adding testing supervisors ####");

db.TestSupervisor.insert({"login" : "glados@aperture.fr", "pass" : "caroline"});
db.TestSupervisor.insert({"login" : "wheatley@aperture.fr", "pass" : "iminspace"});
db.TestSupervisor.insert({"login" : "fd@aperture.fr", "pass" : "fd"});
db.TestSupervisor.insert({"login" : "saubouaben@aperture.fr", "pass" : "sb"});
db.TestSupervisor.insert({"login" : "test", "pass" : "test"});
db.TestSupervisor.insert({"login" : "t", "pass" : "t"});

// ---------- 
print("##### Adding Rooms ####");

db.Rooms.insert({"numero" : 42, "name" : "Rooms du sens de la vie"});
db.Rooms.insert({"numero" : 4, "name" : "Chemins"});
db.Rooms.insert({"numero" : 36, "name" : "6x6"});
db.Rooms.insert({"numero" : 1, "name" : "Rooms d'initiation"});
db.Rooms.insert({"numero" : 21, "name" : "Rooms de blackjack"});
db.Rooms.insert({"numero" : 1000, "name" : "Rooms du gateau"});
db.Rooms.insert({"numero" : 404, "name" : "Room Not found"});
db.Rooms.insert({"numero" : 666, "name" : "Rooms des tourelles"});
db.Rooms.insert({"numero" : 123, "name" : "Rooms du soleil"});

// ---------- 
print("##### Adding test TestSubjects ####");

db.TestSubjects.insert({"name" : "Chell", "Rooms": [{"numero" : 1},{"numero" : 4},{"numero" : 36},{"numero" : 42},{"numero" : 21},{"numero" : 123},{"numero" : 404},{"numero" : 666},{"numero" : 1000}]});
db.TestSubjects.insert({"name" : "Doug Rattman", "Rooms": [{"numero" : 1},{"numero" : 42},{"numero" : 21}]});
db.TestSubjects.insert({"name" : "Companion Cube", "Rooms": [{"numero" : 1},{"numero" : 666}]});
db.TestSubjects.insert({"name" : "Cave Johnson", "Rooms": [{"numero" : 1},{"numero" : 1000}]});
db.TestSubjects.insert({"name" : "Caroline", "Rooms": [{"numero" : 1},{"numero" : 36}]});
db.TestSubjects.insert({"name" : "Atlas", "Rooms": [{"numero" : 1},{"numero" : 404}]});
db.TestSubjects.insert({"name" : "P-Body", "Rooms": [{"numero" : 1},{"numero" : 404}]});