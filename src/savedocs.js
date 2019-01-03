const express = require('express');
const bodyParser = require('body-parser');

const mongo = require('mongodb')

const HTTP_PORT = 8000
const MONGODB_URL = 'mongodb://localhost:27017';
const MONGODB_DATABASE = 'savedocs'
const MONGODB_COLLECTION = 'documents'

// ------------------------------------------------------

const app = express();

app.get('/', function (req, res) {
    res.send(`How to use: 
        To save a document, POST a JSON to /docs.
        To retrieve a document, request /docs/[id] using GET
`);
});

app.use(bodyParser.json());

app.post('/docs', function (req, res) {
    if (req.get('Content-Type') !== "application/json") {
        res.status(400)
        res.send('Body must have content type application/json\n')
        return
    }

    doc = req.body

    if (doc._id) {
        res.status(400)
        res.send('Dont include an _id field, it will be generated\n')
        return
    }

    /*
    * To make the overall structure simpler in this sample we are repeating some code in different places
    * Do not do this on production code :-)
    */
    const client = new mongo.MongoClient(MONGODB_URL)
    client.connect(function (err) {
        if (err) {
            res.status(500)
            res.send(`Error connecting to database: ${err.message}\n`)
            console.error(err)
            return
        }

        const db = client.db(MONGODB_DATABASE);
        const collection = db.collection(MONGODB_COLLECTION);

        collection.insertOne(doc, function (err, result) {
            if (err) {
                res.status(500)
                res.send(`Error saving document: ${err.message}\n`)

                console.error(err)
                client.close();

                return
            }
        
            if (!result.result.ok || result.ops.length != 1) {
                res.status(500)
                res.send(`Document not saved: ${result.result} ${result.ops}\n`)

                console.error('Document not saved', result.result, result.ops)
                client.close();

                return
            }

            console.log("Document saved", result.ops)

            savedId = result.ops[0]._id

            res.send(`Document saved! ID = ${savedId}\n`)

            client.close();
        })
    });

});

app.get('/docs/:id', function (req, res) {
    const client = new mongo.MongoClient(MONGODB_URL)

    client.connect(function (err) {
        if (err) {
            res.status(500)
            res.send(`Error connecting to database: ${err.message}\n`)
            console.error(err)
            return
        }

        const db = client.db(MONGODB_DATABASE);
        const collection = db.collection(MONGODB_COLLECTION);

        collection.findOne({ _id: new mongo.ObjectID(req.params.id) }, function (err, result) {
            if (err) {
                res.status(500)
                res.send(`Error getting document: ${err.message}\n`)

                console.error(err)
                client.close();

                return
            }

            if (result) {
                res.contentType = "application/json"
                res.send(result)

            } else {
                res.status(404)
                res.send(`Could not find object with ID ${req.params.id}\n`)
            }

            client.close();
        })
    });

});


app.listen(HTTP_PORT, function () {
    console.log(`Save Documents service running on port ${HTTP_PORT}!`);
});

