# Simple search engine

A simple search engine implemented as an inverted index (http://en.wikipedia.org/wiki/Inverted_index) that runs in memory and can return a result list that is sorted by TF-IDF (http://en.wikipedia.org/wiki/Tf*idf).
The search engine is:
- be able to take in a list of documents
- support searches for single terms in the document set (http://en.wikipedia.org/wiki/Tokenization)
- return a list of matching documents sorted by TF-IDF

## Installation

```
mvn spring-boot:run
```

## Usage

The search engine exposes REST API for usage. Possible operations are:

### Add documents

`POST /documents`

`Example request body`
```
[
	"the brown fox jumped over the brown dog",
	"the lazy brown dog sat in the corner",
	"the red fox bit the lazy dog"
]
```

### Search single term

`POST /search?term=[string]`

`Example response`
```
[
    {
        "documentId": "1",
        "document": "the brown fox jumped over the brown dog",
        "score": 0.1013662770270411
    },
    {
        "documentId": "2",
        "document": "the lazy brown dog sat in the corner",
        "score": 0.05068313851352055
    }
]
```

### Get documents

`GET /documents`

`Example response`

```
[
    {
        "documentId": "1",
        "document": "the brown fox jumped over the brown dog"
    },
    {
        "documentId": "2",
        "document": "the lazy brown dog sat in the corner"
    },
    {
        "documentId": "3",
        "document": "the red fox bit the lazy dog"
    }
]
```
### Get inverted indexes

`GET /indexes`

`Example response`

```
[
    {
        "term": "bit",
        "occurrences": [
            {
                "documentId": "3",
                "termFrequency": 0.1428571428571429
            }
        ]
    },
    {
        "term": "brown",
        "occurrences": [
            {
                "documentId": "1",
                "termFrequency": 0.25
            },
            {
                "documentId": "2",
                "termFrequency": 0.125
            }
        ]
    },
    {
        "term": "corner",
        "occurrences": [
            {
                "documentId": "2",
                "termFrequency": 0.125
            }
        ]
    },
    {
        "term": "dog",
        "occurrences": [
            {
                "documentId": "1",
                "termFrequency": 0.125
            },
            {
                "documentId": "2",
                "termFrequency": 0.125
            },
            {
                "documentId": "3",
                "termFrequency": 0.1428571428571429
            }
        ]
    },
    {
        "term": "fox",
        "occurrences": [
            {
                "documentId": "1",
                "termFrequency": 0.125
            },
            {
                "documentId": "3",
                "termFrequency": 0.1428571428571429
            }
        ]
    }
]
```