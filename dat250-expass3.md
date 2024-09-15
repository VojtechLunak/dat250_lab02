# Lab03

## Technical Problems Encountered

During the completion of the tutorial, several technical problems were encountered:

1. **State Management in React**:
    - Refactoring the `userVotes` state to an array of objects with `pollId` and `voteCaption` required careful updates to the state management logic.
    - Ensuring that the "You voted for this option" text and the "Vote" button were displayed correctly based on the user's voting status was challenging.

2. **Modal Handling**:
    - Implementing the logic to show a confirmation modal when changing a vote required managing multiple states (`showModal`, `currentVote`) and ensuring they were updated correctly.

3. **API Integration**:
    - Integrating with the backend API to fetch polls and user votes, and to submit or change votes, involved handling asynchronous operations and ensuring the frontend state was updated accordingly.

4. **Testing**:
    - As user creating was not part of the assignment, testing the voting functionality required adding testing user programatically in the constructor of `PollManager` to ensure that votes were being recorded correctly. The `@BeforeEach` method in the test class was refactored to clear users after initializing `PollManager` to make the testing environment clear of any side effects.

## Link to Code

You can find the code for experiments 1-2 [here](https://github.com/VojtechLunak/dat250_lab02/tree/master).

## Pending Issues

There are a few pending issues with this assignment that were not fully resolved:

1. **Vote Change Logic**:
    - The logic for changing a vote sometimes does not update the frontend state immediately, causing a delay in reflecting the new vote status. It is not dramatic, but can be seen by human eyes.

2. **Error Handling**:
    - More robust error handling is needed for API calls to provide better feedback to the user in case of failures.

3. **UI/UX Improvements**:
    - The user interface could be improved to provide a more intuitive voting experience, especially when changing votes. Also, separate component could be added to only see one poll at a time with more detailed information.