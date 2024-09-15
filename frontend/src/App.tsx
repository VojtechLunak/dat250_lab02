import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CreatePoll from './components/CreatePoll';
import VotePoll from './components/VotePoll';
import MainPage from './components/MainPage';
import { UserProvider } from './context/UserContext';

function App() {
  return (
      <UserProvider>
        <Router>
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/create-poll" element={<CreatePoll />} />
            <Route path="/vote-poll" element={<VotePoll />} />
          </Routes>
        </Router>
      </UserProvider>
  );
}

export default App;