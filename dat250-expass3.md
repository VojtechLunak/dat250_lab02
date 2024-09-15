# Lab 02: Technical Report

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
    - Writing tests for the backend logic, especially for creating users, polls, vote options, and casting/changing votes, required setting up the test environment and ensuring the tests covered all edge cases.

## Link to Code

You can find the code for experiments 1-2 [here](https://github.com/VojtechLunak/lab02).

## Pending Issues

There are a few pending issues with this assignment that were not fully resolved:

1. **Vote Change Logic**:
    - The logic for changing a vote sometimes does not update the frontend state immediately, causing a delay in reflecting the new vote status.

2. **Error Handling**:
    - More robust error handling is needed for API calls to provide better feedback to the user in case of failures.

3. **UI/UX Improvements**:
    - The user interface could be improved to provide a more intuitive voting experience, especially when changing votes.