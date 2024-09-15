import React, { useState, useEffect, useContext } from 'react';
import { Container, ListGroup, Button, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import "../App.css";
import { UserContext } from "../context/UserContext.tsx";

interface Vote {
  pollId: number;
  id: number;
  voteOptionCaption: string;
}

const VotePoll: React.FC = () => {
  const [polls, setPolls] = useState<any[]>([]);
  const [userVotes, setUserVotes] = useState<Vote[]>([]);
  const [currentVote, setCurrentVote] = useState<Vote | null>(null);
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  const userContext = useContext(UserContext);
  const { user } = userContext || {};

  useEffect(() => {
    const fetchPolls = async () => {
      const response = await fetch('http://localhost:8080/polls');
      const data = await response.json();
      setPolls(data);
    };
    fetchPolls();
    const fetchUserVotes = async () => {
      if (user) {
        const response = await fetch(`http://localhost:8080/votes/${user.username}`);
        const data = await response.json();
        setUserVotes(data);
      }
    };
    fetchUserVotes();
  }, []);

  useEffect(() => {
    const fetchUserVotes = async () => {
      if (user) {
        const response = await fetch(`http://localhost:8080/votes/${user.username}`);
        const data = await response.json();
        setUserVotes(data);
      }
    };
    fetchUserVotes();
  }, [user]);

  const handleVote = async (pollId: number, optionCaption: string) => {
    const existingVote = userVotes.find(vote => vote.pollId === pollId);
    if (existingVote) {
      setCurrentVote({ ...existingVote, voteOptionCaption: optionCaption });
      setShowModal(true);
    } else {
      await submitFirstVote(pollId, optionCaption);
    }
  };

  const submitFirstVote = async (pollId: number, optionCaption: string) => {
    const response = await fetch(`http://localhost:8080/users/${user?.username}/polls/${pollId}/voteoptions/${optionCaption}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: "{}",
    });
    if (response.ok) {
      await fetchUserVotes();
    }
  };

  const submitChangeVote = async (pollId: number, optionCaption: string) => {
    const existingVote = userVotes.find(vote => vote.pollId === pollId);
    const response = await fetch(`http://localhost:8080/users/${user?.username}/polls/${pollId}/votes/${existingVote?.id}/voteoptions/${optionCaption}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ pollId, voteOptionCaption: optionCaption }),
    });
    if (response.ok) {
      await fetchUserVotes();
    }
    setShowModal(false);
  };

  const fetchUserVotes = async () => {
    const response = await fetch(`http://localhost:8080/votes/${user?.username}`);
    const data = await response.json();
    setUserVotes(data);
  };

  return (
      <Container className="vote-poll">
        <h2 className="my-4 text-center">Vote on Poll</h2>
        <Button variant="link" onClick={() => navigate('/')}>
          &larr; Back to Main Page
        </Button>
        {polls.map((poll) => (
            <div key={poll.id} className="poll-item">
              <h3>{poll.question}</h3>
              <ListGroup>
                {poll.voteOptions.map((option: any) => (
                    <ListGroup.Item key={option.caption} className="d-flex justify-content-between align-items-center">
                      {option.caption}
                      {userVotes.find(vote => vote.pollId === poll.id)?.voteOptionCaption === option.caption ? (
                          <span className="text-success">You voted for this option</span>
                      ) : (
                          <Button
                              variant="primary"
                              onClick={() => handleVote(poll.id, option.caption)}
                          >
                            Vote
                          </Button>
                      )}
                    </ListGroup.Item>
                ))}
              </ListGroup>
            </div>
        ))}
        <Modal show={showModal} onHide={() => setShowModal(false)}>
          <Modal.Header closeButton>
            <Modal.Title>Change Vote</Modal.Title>
          </Modal.Header>
          <Modal.Body>Are you sure you want to change your vote?</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>
              Cancel
            </Button>
            <Button variant="primary" onClick={() => currentVote && submitChangeVote(currentVote.pollId, currentVote.voteOptionCaption)}>
              Confirm
            </Button>
          </Modal.Footer>
        </Modal>
      </Container>
  );
};

export default VotePoll;