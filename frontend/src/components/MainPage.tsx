import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const MainPage: React.FC = () => {
  const [polls, setPolls] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPolls = async () => {
      const response = await fetch('http://localhost:8080/polls');
      const data = await response.json();
      setPolls(data);
    };
    fetchPolls();
  }, []);

  const handleCardClick = () => {
    navigate(`/vote-poll/`);
  };

  return (
      <Container className="main-page">
        <h2 className="my-4 text-center">Poll Overview</h2>
        <Row className="mb-4 justify-content-center">
          <Col xs="auto">
            <Button variant="primary" onClick={() => navigate('/create-poll')}>
              Create Poll
            </Button>
          </Col>
          <Col xs="auto">
            <Button variant="secondary" onClick={() => navigate('/vote-poll')}>
              Vote on Poll
            </Button>
          </Col>
        </Row>
        <Row>
          {polls.map((poll) => (
              <Col md={6} lg={4} className="mb-4" key={poll.id}>
                <Card className="poll-card" onClick={() => handleCardClick()}>
                  <Card.Body>
                    <Card.Title>{poll.question}</Card.Title>
                    <Card.Text>
                      Number of options: {poll.voteOptions.length} (
                      {poll.voteOptions.map((option: { caption: string }) => option.caption).join(', ')}
                      )
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
          ))}
        </Row>
      </Container>
  );
};

export default MainPage;