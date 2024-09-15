import React, { createContext, useState, useEffect, ReactNode } from 'react';

interface User {
  username: string;
  email: string;
}

interface UserContextType {
  user: User | null;
  setUser: (user: User) => void;
}

export const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await fetch('http://localhost:8080/users');
      const data = await response.json();
      if (data.length > 0) {
        setUser(data[0]);
      }
    };
    fetchUsers();
  }, []);

  return (
      <UserContext.Provider value={{ user, setUser }}>
        {children}
      </UserContext.Provider>
  );
};