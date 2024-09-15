#!/bin/bash

# Build the application
./gradlew build

# Start the application in the background
nohup java -jar build/libs/lab02-0.0.1-SNAPSHOT.jar &
APP_PID=$!
sleep 5 # Wait for the application to start

# Function to check HTTP response
check_response() {
  local response="$1"
  local expected_status="$2"
  local expected_body="$3"

  local status_code=$(echo "$response" | tail -n 1)
  local body=$(echo "$response" | sed '$d')

  echo "Response: $response"
  echo "Status Code: $status_code"
  echo "Body: $body"

  if [ "$status_code" -ne "$expected_status" ]; then
    echo "Expected status $expected_status but got $status_code"
    kill $APP_PID
    exit 1
  fi

  if [ -n "$expected_body" ] && [ "$body" != "$expected_body" ]; then
    echo "Expected body '$expected_body' but got '$body'"
    kill $APP_PID
    exit 1
  fi
}

# GET request
response=$(curl -s -w "\n%{http_code}" -X GET "http://localhost:8080/users")
check_response "$response" 200

# POST request
response=$(curl -s -w "\n%{http_code}" -X POST "http://localhost:8080/users" -d '{"username": "test1","email": "test1"}' -H "Content-Type: application/json")
check_response "$response" 200

# POST request
response=$(curl -s -w "\n%{http_code}" -X POST "http://localhost:8080/polls" -d '{"question": "test1","creatorUsername": "test1"}' -H "Content-Type: application/json")
check_response "$response" 200

# POST request
response=$(curl -s -w "\%{http_code}" -X POST "http://localhost:8080/voteoptions" -d '{"pollId": 1,"caption": "option1"}' -H "Content-Type: application/json")
check_response "$response" 200

# POST request
response=$(curl -s -w "\n%{http_code}" -X POST "http://localhost:8080/voteoptions" -d '{"pollId": 1,"caption": "option2"}' -H "Content-Type: application/json")
check_response "$response" 200

# GET request
response=$(curl -s -w "\n%{http_code}" -X GET "http://localhost:8080/polls")
check_response "$response" 200 '[{"id":1,"creatorUsername":"test1","question":"test1","publishedAt":null,"validUntil":null,"voteOptions":[{"pollId":1,"id":1,"caption":"option2","presentationOrder":0},{"pollId":1,"id":1,"caption":"option1","presentationOrder":0}]}]'

# POST request
response=$(curl -s -w "\n%{http_code}" -X POST "http://localhost:8080/users/test1/polls/1/voteoptions/option1" -H "Content-Type: application/json" -d '{}')
check_response "$response" 200

# GET request
response=$(curl -s -w "\n%{http_code}" -X GET "http://localhost:8080/votes")
check_response "$response" 200

# PUT request
response=$(curl -s -w "\n%{http_code}" -X PUT "http://localhost:8080/users/test1/polls/1/votes/1/voteoptions/option2" -H "Content-Type: application/json")
check_response "$response" 200

# Stop the application
kill $APP_PID