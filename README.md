# word-search
Rest API - Spring Boot Application with basic authentication.
API's to search words and words count from a text paragraph.

1.Get api to get complete paragraph hosted.
Input :
curl http://localhost:8080/counter-api/paragraph -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw=="

Output : 
Returns the complete paragraph
"xxxxx.... "

2.Post api to search the given word count.
Input :
curl http://localhost:8080/counter-api/search -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -d"{\"searchText\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}" -H"Content-Type: application/json" -X POST

Output :
{"count":[{"123":0},{"sed":16},{"duis":11},{"donec":8},{"pellentesque":6},{"augue":7}]}

3.Get top 10 repeated words
Input :
curl http://localhost:8080/counter-api/top/10 -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H"Accept: text/csv" 

Output :
eget|17
vel|17
sed|16
in|15
et|14
eu|13
ut|13
nulla|12
amet|12
sit|12
