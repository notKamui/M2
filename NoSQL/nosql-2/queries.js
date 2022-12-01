use dbms1;
db.films.drop();

db.films.insertOne({
    title: "A History of Violence",
    year: 2005,
    genre: "Thriller",
    summary: "Tom Stall, a humble family man and owner of a popular neighborhood restaurant, lives a quiet but fulfilling existence in the Midwest. One night Tom foils a crime at his place of business and, to his chagrin, is plastered all over the news for his heroics. Following this, mysterious people follow the Stalls' every move, concerning Tom more than anyone else. As this situation is confronted, more lurks out over where all these occurrences have stemmed from compromising his marriage, family relationship and the main characters' former relations in the process.",
    director: {
        name: "David Cronenberg",
        birth: 1943
    },
    actors: [
        {
            name: "Viggo Mortensen",
            birth: 1958,
            role: "Tom Stall",
        },
        {
            name: "Ed Harris",
            birth: 1950,
            role: "Carl Fogarty",
        },
        {
            name: "Maria Bello",
            birth: 1967,
            role: "Eddie Stall",
        },
        {
            name: "William Hurt",
            birth: 1950,
            role: "Richie Cusack",
        },
    ]
});

use dbms2;
db.actors.drop();
db.films.drop();

db.actors.insertMany([
    {
        name: "Viggo Mortensen",
        birth: 1958,
        films: [
            "A History of Violence",
        ]
    },
    {
        name: "Ed Harris",
        birth: 1950,
        films: [
            "A History of Violence",
        ]
    },
    {
        name: "Maria Bello",
        birth: 1967,
        films: [
            "A History of Violence",
        ]
    },
    {
        name: "William Hurt",
        birth: 1950,
        films: [
            "A History of Violence",
        ]
    },
])

db.films.insertOne({
    title: "A History of Violence",
    year: 2005,
    genre: "Thriller",
    summary: "Tom Stall, a humble family man and owner of a popular neighborhood restaurant, lives a quiet but fulfilling existence in the Midwest. One night Tom foils a crime at his place of business and, to his chagrin, is plastered all over the news for his heroics. Following this, mysterious people follow the Stalls' every move, concerning Tom more than anyone else. As this situation is confronted, more lurks out over where all these occurrences have stemmed from compromising his marriage, family relationship and the main characters' former relations in the process.",
    director: {
        name: "David Cronenberg",
        birth: 1943
    },
    actors: [
        "Viggo Mortensen",
        "Ed Harris",
        "Maria Bello",
        "William Hurt",
    ]
});

db.actors.createIndex({name: 1}, {unique: true});

const filmsByActor = (actor) => db.films.find({actors: actor});
const actorsByFilm = (film) => db.actors.find({films: film});

filmsByActor("Viggo Mortensen");
actorsByFilm("A History of Violence");

use dbms1;

db.films.insertOne({
    title: "The Social Network",
    year: 2010,
    genre: "Drama",
    summary: "On a fall night in 2003, Harvard undergrad and computer programming genius Mark Zuckerberg sits down at his computer and heatedly begins working on a new idea. In a fury of blogging and programming, what begins in his dorm room soon becomes a global social network and a revolution in communication. A mere six years and 500 million friends later, Mark Zuckerberg is the youngest billionaire in history... but for this entrepreneur, success leads to both personal and legal complications.",
    director: {
        name: "David Fincher",
        birth: 1962
    },
    actors: [
        {
            name: "Jesse Eisenberg",
            birth: 1983,
            role: "Mark Zuckerberg",
        },
        {
            name: "Rooney Mara",
            birth: 1985,
            role: "Erica Albright",
        }
    ]
});

db.films.find({year: 2005});
db.films.find({year: 2005}, { _id: 0, title: 1});
db.films.find({title: "The Social Network"}, { _id: 0, summary: 1});
db.films.find({title: "A History of Violence"}, { _id: 0, director: 1});
db.films.find({title: "The Social Network"}, { _id: 0, actors: 1});
db.films.find({"director.name": /Cronenberg$/}, { _id: 0, title: 1});
db.films.find({"director.name": /^David/}, { _id: 0, "director.name": 1});