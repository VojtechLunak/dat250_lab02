import React, {useContext, useState} from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import "../App.css";
import { UserContext } from '../context/UserContext';


const CreatePoll: React.FC = () => {
  const [question, setQuestion] = useState('');
  const [options, setOptions] = useState(['', '']);
  const navigate = useNavigate();
  const userContext = useContext(UserContext);
  const { user } = userContext || {};

  const handleOptionChange = (index: number, value: string) => {
    const newOptions = [...options];
    newOptions[index] = value;
    setOptions(newOptions);
  };

  const addOption = () => {
    setOptions([...options, '']);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    const poll = { question, creatorUsername: user?.username };

    fetch('http://localhost:8080/polls', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(poll),
    })
        .then(response => {
          if (response.ok) {
            return response.text().then(Number);
          } else {
            throw new Error(`Failed to create poll: ${response.status} ${response.statusText}`);
          }
        })
        .then(pollId => {
          options.forEach(option => {
            fetch('http://localhost:8080/voteoptions', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ pollId, caption: option }),
            })
                .then(options_resp => {
                  if (options_resp.ok) {
                    console.log('Option added successfully');
                  } else {
                    console.error(`Failed to add option: ${options_resp.status} ${options_resp.statusText}`);
                  }
                });
          });
          alert('Poll created successfully!');
          navigate("/");
        })
        .catch(error => {
          console.error(error);
          alert('Failed to create poll.');
        });
  };

  return (
      <Container className="create-poll">
        <h2 className="my-4 text-center">Create Poll</h2>
        <Button variant="link" onClick={() => navigate('/')}>
          &larr; Back to Main Page
        </Button>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formQuestion" className="mb-3">
            <Form.Label>Question</Form.Label>
            <Form.Control
                type="text"
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
                required
            />
          </Form.Group>
          {options.map((option, index) => (
              <Form.Group controlId={`formOption${index}`} key={index} className="mb-3">
                <Form.Label>Option {index + 1}</Form.Label>
                <Form.Control
                    type="text"
                    value={option}
                    onChange={(e) => handleOptionChange(index, e.target.value)}
                    required
                />
              </Form.Group>
          ))}
          <Row className="mb-3">
            <Col>
              <Button variant="secondary" onClick={addOption}>
                Add Option
              </Button>
            </Col>
            <Col>
              <Button variant="primary" type="submit">
                Create Poll
              </Button>
            </Col>
          </Row>
        </Form>
      </Container>
  );
};

export default CreatePoll;