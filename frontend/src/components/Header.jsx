import React from 'react'
import { Link } from 'react-router-dom';
import { PageTitle } from './PageTitle.jsx'

export const Header = () => {
  return (
    <header>
      <div className="container-fluid bg-white">
        <div className="container d-flex justify-content-between align-items-center py-3">
          <PageTitle />
        <a
          className="nav-link"
          href="https://github.com/mariaSomephikhay/TrabajoPracticoSisDistri"
          target="_blank"
          rel="noopener noreferrer">
          <img
            src="/img/github.png"
            height="50"
            alt="Github Logo"
            loading="lazy"
          />
        </a>
        </div>
      </div>
    </header>
  );
};
