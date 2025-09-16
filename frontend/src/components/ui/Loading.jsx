import React from 'react'
import { Title } from "./Title.jsx"

export const Loading = () => {
  return (
    <div className="container p-4">
      <div className="row">
        <div className="col-md-4 mx-auto">
          <div className="card text-center">
            <div className="card-body">
              <Title level='h4' text='Cargando...' />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
