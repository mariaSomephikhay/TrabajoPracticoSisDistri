import { useState } from 'react'
import { OperatorRoutes } from '../routes/Routes.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <OperatorRoutes />
    </>
  )
}

export default App
