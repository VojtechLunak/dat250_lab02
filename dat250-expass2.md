## Lab02
Repository: https://github.com/VojtechLunak/dat250_lab02

### Model 
I added `id` to `Poll`, `VoteOption` and `Vote` for easier aggregation dependant on the type of request that is being processed. For example change in one object (modification, deletion) can lead to changes elsewhere in the application. With ids I can sort these dependencies quickly and coherently.

### PollManager
Manages the creation, updating, and retrieval of polls and their associated vote options. Not all CRUD methods are supported for all domain classes as of now.

### Controllers
All controlers are set up, but similar to PollManager do not support all CRUD operations yet. One problem I had was deciding where to put the `@PostMapping` for creating a vote. I decided to put it in the `UserController` as it is the main entity in the application. Plus I used quite exhaustive path for the endpoint `/{username}/polls/{pollId}/voteoptions/{voteOptionId}` which contains all needed information. Problem connected to this is that at this moment I send only empty body with the POST request, because all needed information are in the path of the resource.

### Types of Tests
- **Unit Tests**: Test individual components like models and methods in the `PollManager` class to ensure they work as expected. Runs on build.
- **End-to-End Tests**: Tests casting a vote, another user voting, changing vote. Runs on build.
- **HTTP API Tests**: Test the HTTP endpoints using `curl` to ensure they return the expected responses and status codes. These tests are automated by Github Action.

### TODO
Optional features that could be added:
 - OpenAPI documentation

### Issues
- I need to cleanup the code - renames (domainManager -> pollManager), remove unused code, etc. I think I might as well refactor some of the parts of the code.
